package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.user.dtos.UserRelationDto;
import com.heima.model.user.pojos.ApUserFollow;
import com.heima.user.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* <p>
* APP用户关注信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserFollow")
public class ApUserFollowController extends AbstractController<ApUserFollow>{

    private ApUserFollowService apUserFollowService;

    //注入
    @Autowired
    public ApUserFollowController(ApUserFollowService apUserFollowService) {
        super(apUserFollowService);
        this.apUserFollowService=apUserFollowService;
    }

    /**
     * 用户关注作者
     */
    @PostMapping("/user_follow")
    public Result userFollow(@RequestBody UserRelationDto dto){
        return apUserFollowService.userFollow(dto);
    }

    /**
     * 查询用户关注作者信息
     */
    @GetMapping("/findUserFollow")
    public ApUserFollow findUserFollow(
            @RequestParam(value = "userId")Integer userId,
            @RequestParam(value = "followId")Integer followId
    ){
        return apUserFollowService.findUserFollow(userId,followId);
    }
}

