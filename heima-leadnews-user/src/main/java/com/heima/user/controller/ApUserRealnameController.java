package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.user.dtos.AuthDTO;
import com.heima.model.user.pojos.ApUserRealname;
import com.heima.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP实名认证信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserRealname")
public class ApUserRealnameController extends AbstractController<ApUserRealname>{

    private ApUserRealnameService apUserRealnameService;

    //注入
    @Autowired
    public ApUserRealnameController(ApUserRealnameService apUserRealnameService) {
        super(apUserRealnameService);
        this.apUserRealnameService=apUserRealnameService;
    }

    @PostMapping("/authFail")
    public Result authFail(@RequestBody AuthDTO dto){
        return apUserRealnameService.authFail(dto);
    }

    /**
     * 审核通过
     */
    @PostMapping("/authPass")
    public Result authPass(@RequestBody AuthDTO dto){
        return apUserRealnameService.authPass(dto);
    }
}

