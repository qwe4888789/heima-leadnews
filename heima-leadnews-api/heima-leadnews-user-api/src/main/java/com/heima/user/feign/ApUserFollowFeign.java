package com.heima.user.feign;

import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.user.pojos.ApUserFollow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "leadnews-user",path = "/api/apUserFollow",contextId = "apUserFollowFeign")
public interface ApUserFollowFeign {

    /**
     * 查询用户关注作者信息
     */
    @GetMapping("/findUserFollow")
    public ApUserFollow findUserFollow(
            @RequestParam(value = "userId")Integer userId,
            @RequestParam(value = "followId")Integer followId
    );
}
