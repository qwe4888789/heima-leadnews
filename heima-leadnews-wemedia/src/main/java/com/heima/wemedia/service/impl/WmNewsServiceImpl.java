package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.constants.MQConstants;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.user.pojos.ApUser;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsSaveDto;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.BeanHelper;
import com.heima.utils.common.JsonUtils;
import com.heima.utils.common.ThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private WmNewsMapper wmNewsMapper;

    @Override
    public Result<PageInfo<WmNews>> searchNews(PageRequestDto<WmNewsDto> dto) {
        //判断是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        try {
            //1.封装条件
            IPage<WmNews> iPage = new Page<>(dto.getPage(),dto.getSize());

            WmNewsDto body = dto.getBody();
            QueryWrapper<WmNews> queryWrapper = new QueryWrapper<>();
            if(body!=null){
                //keyword
                if(StringUtils.isNotEmpty(body.getKeyword())){
                    queryWrapper.like("title",body.getKeyword());
                }
                //status
                if(body.getStatus()!=null){
                    queryWrapper.eq("status",body.getStatus());
                }
                //channelId
                if(body.getChannelId()!=null){
                    queryWrapper.eq("channel_id",body.getChannelId());
                }
                //发布时间范围
                if(body.getBeginPubDate()!=null && body.getEndPubDate()!=null){
                    queryWrapper.between("publish_time",body.getBeginPubDate(),body.getEndPubDate());
                }

            }

            //当前登录自媒体人的文章
            queryWrapper.eq("user_id",wmUser.getId());

            //按照创建时间倒序
            queryWrapper.orderByDesc("created_time");

            //2.查询
            iPage = page(iPage,queryWrapper);

            //3.获取结果并封装返回
            PageInfo<WmNews> pageInfo = new PageInfo<>(dto.getPage(),dto.getSize(),iPage.getTotal(),iPage.getPages(),iPage.getRecords());

            return Result.ok(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result submitNews(WmNewsSaveDto dto) {
        //判断是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //把dto时间拷贝到WmNews中
        WmNews wmNews = BeanHelper.copyProperties(dto,WmNews.class);

        //设置发表自媒体人
        wmNews.setUserId(wmUser.getId());

        //1. 处理封面图片格式问题 images
        List<String> coverImages = dto.getImages();
        String imageStr = coverImages.stream().collect(Collectors.joining(","));
        wmNews.setImages(imageStr);

        //2. 处理自动封面的问题
        // 取出内容的所有图片
        List<String> contentImages = getImagesFromContent(dto.getContent());

        if(dto.getType().equals(-1)){
            //处理自动封面
            //图片数量为0，则为无图(type=0)，修改images值
            //图片数量为1-2，则为单图（type=1)，修改images值
            //图片数量大于等于3，则为多图（type=3)，修改images值

            if(contentImages.size()==0){
                //无图
                wmNews.setType(0);
                wmNews.setImages("");
            }
            if(contentImages.size()>=1 && contentImages.size()<=2){
                //单图
                wmNews.setType(1);
                wmNews.setImages(contentImages.get(0));
            }
            if(contentImages.size()>=3){
                //多图
                wmNews.setType(3);
                //截取List集合内容
                List<String> images = contentImages.subList(0, 3);
                wmNews.setImages(images.stream().collect(Collectors.joining(",")));
            }
        }

        //3）判断该请求为新增还是修改（id是否为空）
        if(dto.getId()==null){
            //如果是新增，新增wm_news表数据
            wmNews.setCreatedTime(new Date());
            save(wmNews);

        }else{
            //如果是修改，修改wm_news表数据，先删除文章和旧素材的关联

            //修改文章表数据
            updateById(wmNews);

            //删除文章和旧素材的关联
            QueryWrapper<WmNewsMaterial> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("news_id",dto.getId());
            wmNewsMaterialMapper.delete(queryWrapper);

        }

        //4）判断草稿还是提交
        //如果是提交请求，才新增文章和素材的关联
        if(dto.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            //1）添加内容图片与文章的关联
            if(CollectionUtils.isNotEmpty(contentImages)){
                //通过素材的URL地址取出素材的Id列表
                List<Integer> materialIds = getMaterialIdsFromUrl(contentImages);
                //批量信息素材id和文章id的关系
                wmNewsMaterialMapper.saveNewsMaterials(materialIds,wmNews.getId(),0);
            }

            //2)添加封面图片与文章的关联
            String images = wmNews.getImages();
            if(StringUtils.isNotEmpty(images)){
                List<Integer> materialIds = getMaterialIdsFromUrl(Arrays.asList(images.split(",")));
                //批量信息素材id和文章id的关系
                wmNewsMaterialMapper.saveNewsMaterials(materialIds,wmNews.getId(),1);
            }
        }

        //如果是提交审核，发送自媒体ID到MQ
        if(dto.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            kafkaTemplate.send(MQConstants.WM_NEWS_AUTO_SCAN_TOPIC,wmNews.getId()+"");
        }

        return Result.ok();
    }

    /**
     * 通过素材的URL地址取出素材的Id列表
     * @param contentImages
     * @return
     */
    private List<Integer> getMaterialIdsFromUrl(List<String> contentImages) {
        QueryWrapper<WmMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("url",contentImages);
        List<WmMaterial> wmMaterials = wmMaterialMapper.selectList(queryWrapper);
        return wmMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());
    }

    /**
     * 提取内容中的所有图片的地址
     * @param content
     * @return
     */
    private List<String> getImagesFromContent(String content) {
        //1.将content字符串转换List<Map>对象
        List<Map<String,String>> list = JsonUtils.toBean(content,List.class);
        //2.准备List集合存储所有路径
        List<String> imageList = new ArrayList<>();;
        if(CollectionUtils.isNotEmpty(list)){
            for(Map<String,String> map:list){
                //找出图片
                if(map.get("type").equals("image")){
                    imageList.add(map.get("value"));
                }
            }
        }
        return imageList;
    }


    @Override
    public Result delNews(Integer id) {
        //判断是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //判断文章是否存在
        WmNews wmNews = getById(id);
        if(wmNews==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        try {
            //删除该文章与素材关联
            QueryWrapper<WmNewsMaterial> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("news_id",id);
            wmNewsMaterialMapper.delete(queryWrapper);

            //删除该文章记录
            removeById(id);

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result downOrUp(Map<String, Object> map) {
        //判断是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        Integer id = (Integer)map.get("id");
        Integer enable = (Integer)map.get("enable");

        //判断文章是否存在
        WmNews wmNews = getById(id);
        if(wmNews==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        try {
            wmNews.setEnable(enable);
            updateById(wmNews);

            //使用MQ同步App文章的上下架状态
            //App文章ID 状态值
            Map<String,Object> msgMap = new HashMap<>();
            msgMap.put("enable",enable);
            msgMap.put("apArticleId",wmNews.getArticleId());

            kafkaTemplate.send(MQConstants.WM_NEWS_UP_OR_DOWN_TOPIC,JsonUtils.toString(msgMap));

            /**
             * 判断上架或下架
             */
            if(enable.equals(1)){
                //上架
                kafkaTemplate.send(MQConstants.WM_NEW_UP_ES_TOPIC,wmNews.getArticleId()+"");
            }else{
                //下架
                kafkaTemplate.send(MQConstants.WM_NEW_DOWN_ES_TOPIC,wmNews.getArticleId()+"");
            }

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result<PageInfo<WmNewsVo>> searchNewsByCondition(PageRequestDto<WmNews> dto) {
        if(dto.getPage()==null)dto.setPage(1L);
        if(dto.getSize()==null)dto.setSize(10L);

        //1.封装查询条件
        //处理起始行
        Long start = (dto.getPage()-1)*dto.getSize();
        //处理关键词
        String keyword = "";
        WmNews body = dto.getBody();
        if(body!=null && StringUtils.isNotEmpty(body.getTitle())){
            keyword = "%"+body.getTitle()+"%";
        }

        //2.执行查询，获取结果
        //1）查询每页列表数据
        List<WmNewsVo> wmNewsVoList = wmNewsMapper.searchNewsByCondition(keyword,start,dto.getSize());
        //2）查询总记录数
        Long totalCount = wmNewsMapper.searchCountNewsByCondition(keyword);

        //3.封装结果返回
        //1）计算总页数
        Long totalPage = 0L;
        if(totalCount%dto.getSize()==0){
            totalPage = totalCount/dto.getSize();
        }else{
            totalPage = totalCount/dto.getSize()+1;
        }
        //2）封装PageInfo对象
        PageInfo<WmNewsVo> pageInfo = new PageInfo<>(
                dto.getPage(),
                dto.getSize(),
                totalCount,
                totalPage,
                wmNewsVoList
        );
        return Result.ok(pageInfo);
    }

    @Override
    public Result<List<WmNews>> findRelease() {
        try {
            QueryWrapper<WmNews> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",WmNews.Status.SUCCESS.getCode());
            //发布时间小于等于当前时间
            queryWrapper.le("publish_time",new Date());
            return Result.ok(list(queryWrapper));
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

}
