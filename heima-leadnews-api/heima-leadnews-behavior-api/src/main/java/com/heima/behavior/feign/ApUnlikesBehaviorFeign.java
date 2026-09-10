package com.heima.behavior.feign;

import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "leadnews-behavior",path = "/api/apUnlikesBehavior",contextId = "apUnlikesBehaviorFeign")
public interface ApUnlikesBehaviorFeign {

    /**
     * 查询用户不喜欢的信息
     */
    @GetMapping("/findUnlike")
    public ApUnlikesBehavior findUnlike(
            @RequestParam(value = "entryId")Integer entryId,
            @RequestParam(value = "article_id")Long articleId
    );
}
