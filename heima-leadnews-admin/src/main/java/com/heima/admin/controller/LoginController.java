package com.heima.admin.controller;

import com.heima.admin.service.AdUserService;
import com.heima.common.dtos.Result;
import com.heima.model.admin.pojos.AdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 管理员登录
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AdUserService adUserService;

    /**
     * 登录
     */
    @PostMapping("/in")
    public Result<Map<String,Object>> login(@RequestBody AdUser user){
        return adUserService.login(user);
    }
}
