package com.heima.article.controller;


import com.heima.article.service.ApAuthorService;
import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.Result;
import com.heima.model.article.pojos.ApAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP文章作者信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apAuthor")
public class ApAuthorController extends AbstractController<ApAuthor>{

    private ApAuthorService apAuthorService;

    //注入
    @Autowired
    public ApAuthorController(ApAuthorService apAuthorService) {
        super(apAuthorService);
        this.apAuthorService=apAuthorService;
    }

    /**
     * 根据userId查询作者
     */
    @GetMapping("/findApAuthor/{apUserId}")
    public Result<ApAuthor> findApAuthor(@PathVariable("apUserId") Integer apUserId){
        return apAuthorService.findApAuthor(apUserId);
    }

    /**
     * 根据自媒体ID查询作者
     */
    @GetMapping("/findApAuthorByWmUserId/{wmUserId}")
    public Result<ApAuthor> findApAuthorByWmUserId(@PathVariable("wmUserId") Integer wmUserId){
        return apAuthorService.findApAuthorByWmUserId(wmUserId);
    }
}

