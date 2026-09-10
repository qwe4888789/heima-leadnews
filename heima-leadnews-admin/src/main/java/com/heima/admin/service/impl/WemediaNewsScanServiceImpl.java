package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.heima.admin.mapper.AdChannelMapper;
import com.heima.admin.mapper.AdSensitiveMapper;
import com.heima.admin.service.WemediaNewsScanService;
import com.heima.article.feign.ApArticleFeign;
import com.heima.article.feign.ApAuthorFeign;
import com.heima.common.aliyun.GreenImageScan;
import com.heima.common.aliyun.GreenTextScan;
import com.heima.common.constants.MQConstants;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.common.fastdfs.FastDFSClientUtil;
import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.admin.pojos.AdChannel;
import com.heima.model.admin.pojos.AdSensitive;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleConfig;
import com.heima.model.article.pojos.ApArticleContent;
import com.heima.model.article.pojos.ApAuthor;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.utils.common.JsonUtils;
import com.heima.utils.common.SensitiveWordUtil;
import com.heima.wemedia.feign.WmNewsFeign;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class WemediaNewsScanServiceImpl implements WemediaNewsScanService {
    @Autowired
    private WmNewsFeign wmNewsFeign;
    @Autowired
    private FastDFSClientUtil clientUtil;
    @Value("${fileServerUrl}")
    private String fileServerUrl;
    @Autowired
    private GreenTextScan greenTextScan;
    @Autowired
    private GreenImageScan greenImageScan;
    @Autowired
    private AdSensitiveMapper adSensitiveMapper;
    @Autowired
    private ApAuthorFeign apAuthorFeign;
    @Autowired
    private AdChannelMapper adChannelMapper;
    @Autowired
    private ApArticleFeign apArticleFeign;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    @GlobalTransactional //添加分布式事务
    public void autoScanByMediaNewsId(Integer id) {

        //1.根据ID查询自媒体文章
        WmNews wmNews = wmNewsFeign.findOne(id).getData();
        if(wmNews==null)return;

        //2.判断只有状态为"提交审核"才进入审核流程
        if(!wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode()))return;

        //3.提取文章的文本内容
        List<String> textList = getTextFromContent(wmNews);

        //4.提取文章的图片内容
        List<byte[]> imageList = getImageFromContent(wmNews);

        //5.提交文本内容到阿里云进行检测，根据反馈结果，修改文章的状态
        try {
            Map result = greenTextScan.greeTextScan(textList);
            //根据反馈结果，修改文章的状态
            boolean flag = handlerScanResult(result,wmNews);
            if(!flag)return; //如果审核失败，程序终止
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(500,"调用阿里云API失败");
        }

        //6.提交图片内容到阿里云进行检测，根据反馈结果，修改文章的状态
        try {
            Map result = greenImageScan.imageScan(imageList);
            //根据反馈结果，修改文章的状态
            boolean flag = handlerScanResult(result,wmNews);
            if(!flag)return; //如果审核失败，程序终止
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(500,"调用阿里云API失败");
        }

        //7.判断文本内容是否包含敏感词
        boolean flag = handlerSensitiveScan(textList,wmNews);
        if(!flag)return;//如果审核失败，程序终止

        //8.发布文章
        publishArticle(wmNews);

        //模拟异常
        //int i = 100/0;
    }

    /**
     * 发布文章
     * @param wmNews
     */
    private void publishArticle(WmNews wmNews) {
        //1.判断发表时间是否大于当前时间，如果大于则修改文章状态（8）
        if(wmNews.getPublishTime().after(new Date())){
            wmNews.setStatus(WmNews.Status.SUCCESS.getCode());
            wmNews.setReason("文章需要定时发布");
            wmNewsFeign.update(wmNews);
            return;//程序终止
        }

        //2.保存文章数据到App文章表中，修改文章状态为9
        saveApArticle(wmNews);

    }

    /**
     * 把自媒体文章保存到App文章库中，修改文章状态为9
     * @param wmNews
     */
    private void saveApArticle(WmNews wmNews) {
        //保存ApArticle
        ApArticle apArticle = new ApArticle();
        apArticle.setTitle(wmNews.getTitle());

        //根据自媒体用户ID获取文章作者
        ApAuthor apAuthor = apAuthorFeign.findApAuthorByWmUserId(wmNews.getUserId()).getData();
        //设置文章作者信息
        if(apArticle!=null){
            apArticle.setAuthorId(apAuthor.getId());
            apArticle.setAuthorName(apAuthor.getName());
        }

        //根据频道ID查询频道信息
        AdChannel adChannel = adChannelMapper.selectById(wmNews.getChannelId());
        //设置频道信息
        if(adChannel!=null){
            apArticle.setChannelId(adChannel.getId());
            apArticle.setChannelName(adChannel.getName());
        }
        apArticle.setLayout(wmNews.getType());
        apArticle.setFlag(0);
        apArticle.setImages(wmNews.getImages());
        apArticle.setLabels(wmNews.getLabels());
        apArticle.setLikes(0);
        apArticle.setComment(0);
        apArticle.setCollection(0);
        apArticle.setViews(0);
        apArticle.setCreatedTime(new Date());
        apArticle.setPublishTime(new Date());

        //保存ApArticle信息
        apArticle = apArticleFeign.saveApArticle(apArticle).getData();

        //保存ApArticleConfig
        ApArticleConfig apArticleConfig = new ApArticleConfig();
        apArticleConfig.setArticleId(apArticle.getId());
        apArticleConfig.setIsComment(1);
        apArticleConfig.setIsForward(1);
        apArticleConfig.setIsDown(0);//未下架
        apArticleConfig.setIsDelete(0);//未删除
        apArticleFeign.saveApArticleConfig(apArticleConfig);

        //保存ApArticleContent
        ApArticleContent apArticleContent = new ApArticleContent();
        apArticleContent.setArticleId(apArticle.getId());
        apArticleContent.setContent(wmNews.getContent());
        apArticleFeign.saveApArticleContent(apArticleContent);

        //修改自媒体文章信息（状态为9，更新article_id字段为App文章id）
        wmNews.setStatus(WmNews.Status.PUBLISHED.getCode());
        wmNews.setReason("发布成功");
        wmNews.setArticleId(apArticle.getId());
        wmNewsFeign.update(wmNews);

        //把文章同步到ES库中
        kafkaTemplate.send(MQConstants.WM_NEW_UP_ES_TOPIC,apArticle.getId()+"");
    }

    /**
     * 敏感词检测
     * @param textList
     * @return
     */
    private boolean handlerSensitiveScan(List<String> textList,WmNews wmNews) {
        boolean flag = true;
        //1.查询数据库的所有敏感词
        List<AdSensitive> adSensitives = adSensitiveMapper.selectList(null);
        if (CollectionUtils.isNotEmpty(adSensitives)) {
            //2.初始化敏感词词库Map
            List<String> sensitiveWords = adSensitives.stream().map(AdSensitive::getSensitives).collect(Collectors.toList());
            SensitiveWordUtil.initMap(sensitiveWords);
            //3.判断内容是否匹配敏感词
            String content = textList.stream().collect(Collectors.joining(""));
            Map<String, Integer> result = SensitiveWordUtil.matchWords(content);
            if(result.size()>0){
                flag = false;
                //修改文章状态
                wmNews.setStatus(WmNews.Status.FAIL.getCode());
                wmNews.setReason("该文章包含了敏感词，请更改");
                wmNewsFeign.update(wmNews);
            }
        }
        return flag;
    }

    /**
     * 处理阿里云的反馈结果
     * @param result
     * @param wmNews
     * @return
     */
    private boolean handlerScanResult(Map result, WmNews wmNews) {
        boolean flag = false;
        if(result!=null){
            String suggestion = (String)result.get("suggestion");
            if(suggestion.equals("pass")){
                flag = true;
            }
            if(suggestion.equals("review")){
                //修改文章状态为3,（进入人工审核）
                wmNews.setStatus(WmNews.Status.ADMIN_AUTH.getCode());
                wmNews.setReason("该文章需要人工审核");
                wmNewsFeign.update(wmNews);
            }
            if(suggestion.equals("block")){
                wmNews.setStatus(WmNews.Status.FAIL.getCode());
                String label = (String)result.get("label");
                wmNews.setReason("该文章审核不通过："+label);
                wmNewsFeign.update(wmNews);
            }
        }
        return flag;
    }

    /**
     * 提取图片内容
     * @param wmNews
     * @return
     */
    private List<byte[]> getImageFromContent(WmNews wmNews) {
        List<byte[]> imageList = new ArrayList<>();

        //取出文章的内容图片
        if(StringUtils.isNotEmpty(wmNews.getContent())){
            List<Map<String,String>> list = JsonUtils.toBean(wmNews.getContent(),List.class);
            if(CollectionUtils.isNotEmpty(list)){
                for(Map<String,String> map:list){
                    //判断文本类型
                    if(map.get("type").equals("image")){
                        String url = map.get("value");  // http://192.168.132.133/group1/M00/00/00/wKiEhWDtVImAeNjmAAB5v-vu4bQ824.jpg

                        getImageFromUrl(imageList, url);
                    }
                }
            }
        }

        //取出文章的封面图片
        if(StringUtils.isNotEmpty(wmNews.getImages())){
            String[] array = wmNews.getImages().split(",");
            for(String url:array){
                getImageFromUrl(imageList, url);
            }
        }

        return imageList;
    }

    private void getImageFromUrl(List<byte[]> imageList, String url) {
        //截取路径
        url = url.replaceAll(fileServerUrl,"");  //  group1/M00/00/00/wKiEhWDtVImAeNjmAAB5v-vu4bQ824.jpg

        //截取出组名称
        String groupName = url.substring(0,url.indexOf("/"));

        String imageUrl = url.substring(groupName.length()+1);
        //从FastDFS中下载图片
        /**
         * 参数一：组名  group1
         * 参数二：路径  M00/00/00/wKiEhWDtVImAeNjmAAB5v-vu4bQ824.jpg
         */
        try {
            byte[] imageBytes = clientUtil.download(groupName, imageUrl);

            //存入List集合
            imageList.add(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LeadNewsException(500,"FastDFS图片下载失败");
        }
    }

    /**
     * 提取文本内容
     * @param wmNews
     * @return
     */
    private List<String> getTextFromContent(WmNews wmNews) {
        List<String> textList = new ArrayList<>();
        if(StringUtils.isNotEmpty(wmNews.getContent())){
            List<Map<String,String>> list = JsonUtils.toBean(wmNews.getContent(),List.class);
            if(CollectionUtils.isNotEmpty(list)){
                for(Map<String,String> map:list){
                    //判断文本类型
                    if(map.get("type").equals("text")){
                        textList.add(map.get("value"));
                    }
                }
            }
        }
        //添加标题
        if(StringUtils.isNotEmpty(wmNews.getTitle())){
            textList.add(wmNews.getTitle());
        }
        return textList;
    }


    @Override
    public Result<PageInfo<WmNewsVo>> findNews(PageRequestDto<WmNews> dto) {
        return wmNewsFeign.searchNewsByCondition(dto);
    }

    @Override
    public Result manualScanNews(NewsAuthDto dto, Integer code) {
        //查询文章
        WmNews wmNews = wmNewsFeign.findOne(dto.getId()).getData();
        if(wmNews==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        try {
            //判断审核成功还是失败
            if(code.equals(WmNews.Status.ADMIN_SUCCESS.getCode())){
                //审核成功
                publishArticle(wmNews);
            }else{
                //审核失败
                //修改状态为2，且设置原因
                wmNews.setStatus(WmNews.Status.FAIL.getCode());
                wmNews.setReason(dto.getMsg());
                wmNewsFeign.update(wmNews);
            }

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public void scahedulePublishNews(List<WmNews> wmNews) {
        wmNews.forEach(news -> {
            saveApArticle(news);
        });
    }

}
