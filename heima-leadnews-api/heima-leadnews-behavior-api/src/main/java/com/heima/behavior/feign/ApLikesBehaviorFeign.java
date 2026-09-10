package com.heima.behavior.feign;

import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "leadnews-behavior",path = "/api/apLikesBehavior",contextId = "apLikesBehaviorFeign")
public interface ApLikesBehaviorFeign {
    /**
     * 查询用户点赞的信息
     */
    @GetMapping("/findLike")
    public ApLikesBehavior findLike(
            @RequestParam(value = "entryId")Integer entryId,
            @RequestParam(value = "article_id")Long articleId
    );
}
