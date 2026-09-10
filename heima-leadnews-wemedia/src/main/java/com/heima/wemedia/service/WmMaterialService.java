package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.wemedia.pojos.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 自媒体图文素材信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface WmMaterialService extends IService<WmMaterial> {

    Result<WmMaterial> uploadPicture(MultipartFile file);

    Result delPicture(Integer id);

    Result updateCollection(Integer id, int isCollection);
}
