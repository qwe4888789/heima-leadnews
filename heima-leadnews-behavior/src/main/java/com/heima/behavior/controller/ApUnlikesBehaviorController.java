package com.heima.behavior.controller;


import com.heima.behavior.service.ApUnlikesBehaviorService;
import com.heima.common.controller.AbstractController;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP不喜欢行为表 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apUnlikesBehavior")
public class ApUnlikesBehaviorController extends AbstractController<ApUnlikesBehavior>{

    private ApUnlikesBehaviorService apUnlikesBehaviorService;

    //注入
    @Autowired
    public ApUnlikesBehaviorController(ApUnlikesBehaviorService apUnlikesBehaviorService) {
        super(apUnlikesBehaviorService);
        this.apUnlikesBehaviorService=apUnlikesBehaviorService;
    }


    /**
     * 查询用户不喜欢的信息
     */
    @GetMapping("/findUnlike")
    public ApUnlikesBehavior findUnlike(
            @RequestParam(value = "entryId")Integer entryId,
            @RequestParam(value = "article_id")Long articleId
    ){
        return apUnlikesBehaviorService.findUnlike(entryId,articleId);
    }

}

