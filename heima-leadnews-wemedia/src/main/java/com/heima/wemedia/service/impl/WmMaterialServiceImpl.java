package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.common.fastdfs.FastDFSClientUtil;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmNewsMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.mapper.WmNewsMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 自媒体图文素材信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {
    @Autowired
    private FastDFSClientUtil clientUtil;
    @Value("${fileServerUrl}")
    private String fileServerUrl;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    @Override
    public Result<WmMaterial> uploadPicture(MultipartFile file) {
        //1.判断文件是否存在
        if(file==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //2.判断用户是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //3.把文件上传到FastDFS
        try {
            String url = clientUtil.uploadFile(file);

            //4.保存素材信息
            WmMaterial wmMaterial = new WmMaterial();
            wmMaterial.setUserId(wmUser.getId());
            wmMaterial.setType(0);
            wmMaterial.setUrl(fileServerUrl+url);
            wmMaterial.setCreatedTime(new Date());
            wmMaterial.setIsCollection(0);
            save(wmMaterial);

            //5.返回新的素材对象
            return Result.ok(wmMaterial);
        } catch (IOException e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result delPicture(Integer id) {
        //1.判断是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.判断素材是否存在
        WmMaterial wmMaterial = getById(id);
        if(wmMaterial==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //3.判断该素材是否关联了文章
        QueryWrapper<WmNewsMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("material_id",id);
        Integer count = wmNewsMaterialMapper.selectCount(queryWrapper);
        if(count>0){
            throw new LeadNewsException(500,"该素材已被关联，无法删除");
        }

        //4.判断登录用户是否为素材拥有着
        if(!wmUser.getId().equals(wmMaterial.getUserId())){
            throw new LeadNewsException(500,"你无权删除该素材");
        }

        //3.删除FastDFS的文件
        try {
            String url = wmMaterial.getUrl();
            url = url.replaceAll(fileServerUrl,"");
            clientUtil.delFile(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }

        try {
            //4.移除素材记录
            removeById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result updateCollection(Integer id, int isCollection) {
        //1.判断是否登录
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.判断素材是否存在
        WmMaterial wmMaterial = getById(id);
        if(wmMaterial==null){
            throw new LeadNewsException(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        try {
            wmMaterial.setIsCollection(isCollection);
            updateById(wmMaterial);

            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}
