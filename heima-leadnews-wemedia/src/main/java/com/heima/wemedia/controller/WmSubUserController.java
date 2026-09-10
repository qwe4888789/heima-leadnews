package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.wemedia.pojos.WmSubUser;
import com.heima.wemedia.service.WmSubUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体子账号信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmSubUser")
public class WmSubUserController extends AbstractController<WmSubUser>{

    private WmSubUserService wmSubUserService;

    //注入
    @Autowired
    public WmSubUserController(WmSubUserService wmSubUserService) {
        super(wmSubUserService);
        this.wmSubUserService=wmSubUserService;
    }

}

