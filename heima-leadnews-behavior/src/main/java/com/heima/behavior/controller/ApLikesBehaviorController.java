package com.heima.behavior.controller;


import com.heima.behavior.service.ApLikesBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.behavior.dtos.LikesBehaviorDto;
import com.heima.model.behavior.pojos.ApLikesBehavior;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
* <p>
* APP点赞行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apLikesBehavior")
public class ApLikesBehaviorController extends AbstractController<ApLikesBehavior>{

    private ApLikesBehaviorService apLikesBehaviorService;

    //注入
    @Autowired
    public ApLikesBehaviorController(ApLikesBehaviorService apLikesBehaviorService) {
        super(apLikesBehaviorService);
        this.apLikesBehaviorService=apLikesBehaviorService;
    }

    /**
     * 点赞/取消点赞
     */
    @PostMapping
    public Result like(@RequestBody LikesBehaviorDto dto){
        return apLikesBehaviorService.like(dto);
    }

    /**
     * 查询用户点赞的信息
     */
    @GetMapping("/findLike")
    public ApLikesBehavior findLike(
            @RequestParam(value = "entryId")Integer entryId,
            @RequestParam(value = "article_id")Long articleId
    ){
        return apLikesBehaviorService.findLike(entryId,articleId);
    }
}

