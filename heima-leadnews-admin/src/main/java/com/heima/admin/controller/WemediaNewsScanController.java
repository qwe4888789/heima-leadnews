package com.heima.admin.controller;

import com.heima.admin.service.WemediaNewsScanService;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体文章审核
 */
@RestController
@RequestMapping("/api/news_auth")
public class WemediaNewsScanController {
    @Autowired
    private WemediaNewsScanService wemediaNewsScanService;

    /**
     * 查询待审核文章
     */
    @PostMapping("/findNews")
    public Result<PageInfo<WmNewsVo>> findNews(@RequestBody PageRequestDto<WmNews> dto){
        return wemediaNewsScanService.findNews(dto);
    }

    /**
     * 审核成功
     */
    @PostMapping("/auth_pass")
    public Result authPass(@RequestBody NewsAuthDto dto){
        return wemediaNewsScanService.manualScanNews(dto,WmNews.Status.ADMIN_SUCCESS.getCode());
    }

    /**
     * 审核失败
     */
    @PostMapping("/auth_fail")
    public Result authFail(@RequestBody NewsAuthDto dto){
        return wemediaNewsScanService.manualScanNews(dto,WmNews.Status.FAIL.getCode());
    }
}
