package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体用户信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmUser")
public class WmUserController extends AbstractController<WmUser>{

    private WmUserService wmUserService;

    //注入
    @Autowired
    public WmUserController(WmUserService wmUserService) {
        super(wmUserService);
        this.wmUserService=wmUserService;
    }

    /**
     * 根据apUserId查询自媒体账户是否存在
     */
    @GetMapping("/findUser/{apUserId}")
    public Result<WmUser> findUser(@PathVariable("apUserId") Integer apUserId){
        return wmUserService.findUser(apUserId);
    }

}

