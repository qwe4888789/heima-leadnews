package com.heima.user.controller;

import com.heima.common.dtos.Result;
import com.heima.model.user.dtos.LoginDto;
import com.heima.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * App用户登录
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private ApUserService apUserService;


    /**
     * 登录方法
     */
    @PostMapping("/login_auth")
    public Result<Map<String,Object>> login(@RequestBody LoginDto dto){
        return apUserService.login(dto);
    }
}
