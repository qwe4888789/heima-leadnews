package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.utils.common.ThreadLocalUtils;
import com.heima.wemedia.service.WmMaterialService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
* <p>
* 自媒体图文素材信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmMaterial")
public class WmMaterialController extends AbstractController<WmMaterial>{

    private WmMaterialService wmMaterialService;

    //注入
    @Autowired
    public WmMaterialController(WmMaterialService wmMaterialService) {
        super(wmMaterialService);
        this.wmMaterialService=wmMaterialService;
    }

    /**
     * 添加素材
     */
    @PostMapping("/upload_picture")
    public Result<WmMaterial> uploadPicture(MultipartFile file){
       return wmMaterialService.uploadPicture(file);
    }

    /**
     * 根据自媒体人查询素材列表
     */
    @PostMapping("/list")
    public Result<PageInfo<WmMaterial>> searchByPage(@RequestBody PageRequestDto<WmMaterial> pageRequestDto){
        //1.取出登录自媒体用户
        WmUser wmUser = (WmUser)ThreadLocalUtils.get();
        if(wmUser==null){
            throw new LeadNewsException(AppHttpCodeEnum.NEED_LOGIN);
        }

        //2.把用户ID设置到pageRequestDto的body条件中
        WmMaterial body = pageRequestDto.getBody();
        if(body==null) {
            body = new WmMaterial();
        }
        body.setUserId(wmUser.getId());
        pageRequestDto.setBody(body);

        return super.searchByPage(pageRequestDto);
    }

    /**
     * 删除素材
     */
    @DeleteMapping("/del_picture/{id}")
    public Result delPicture(@PathVariable("id") Integer id) {
        return wmMaterialService.delPicture(id);
    }

    /**
     * 收藏
     */
    @PutMapping("/collect/{id}")
    public Result collect(@PathVariable("id") Integer id){
        return wmMaterialService.updateCollection(id,1);
    }

    /**
     * 取消收藏
     */
    @PutMapping("/cancel_collect/{id}")
    public Result cancelCollect(@PathVariable("id") Integer id){
        return wmMaterialService.updateCollection(id,0);
    }
}

