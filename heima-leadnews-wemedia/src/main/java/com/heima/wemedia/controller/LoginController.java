package com.heima.wemedia.controller;

import com.heima.common.dtos.Result;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 自媒体人登录
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private WmUserService wmUserService;

    /**
     * 登录
     */
    @PostMapping("/in")
    public Result<Map<String,Object>> login(@RequestBody WmUser user){
        return wmUserService.login(user);
    }
}
