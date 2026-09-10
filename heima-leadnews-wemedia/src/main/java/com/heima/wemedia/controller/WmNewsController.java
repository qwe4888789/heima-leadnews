package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsSaveDto;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* <p>
* 自媒体图文内容信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmNews")
public class WmNewsController extends AbstractController<WmNews>{

    private WmNewsService wmNewsService;

    //注入
    @Autowired
    public WmNewsController(WmNewsService wmNewsService) {
        super(wmNewsService);
        this.wmNewsService=wmNewsService;
    }

    /**
     * 查询自媒体人的文章列表
     */
    @PostMapping("/searchNews")
    public Result<PageInfo<WmNews>> searchNews(@RequestBody PageRequestDto<WmNewsDto> dto){
        return wmNewsService.searchNews(dto);
    }

    /**
     * 发布文章
     */
    @PostMapping("/submit")
    public Result submitNews(@RequestBody WmNewsSaveDto dto){
        return wmNewsService.submitNews(dto);
    }

    /**
     * 删除文章
     */
    @DeleteMapping("/del_news/1")
    public Result delNews(@PathVariable("id") Integer id){
        return wmNewsService.delNews(id);
    }
    /**
     * 文章上下架
     */
    @PutMapping("/down_or_up")
    public Result downOrUp(@RequestBody Map<String,Object> map){
        return wmNewsService.downOrUp(map);
    }

    /**
     * 查询待审核的文章列表
     */
    @PostMapping("/searchNewsByCondition")
    public Result<PageInfo<WmNewsVo>> searchNewsByCondition(@RequestBody PageRequestDto<WmNews> dto){
        return wmNewsService.searchNewsByCondition(dto);
    }

    /**
     * 查询待定时发布的文章
     */
    @GetMapping("/findRelease")
    public Result<List<WmNews>> findRelease(){
        return wmNewsService.findRelease();
    }
}

