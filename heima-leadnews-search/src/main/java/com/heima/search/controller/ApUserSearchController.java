package com.heima.search.controller;

import com.heima.common.dtos.Result;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApUserSearch;
import com.heima.search.service.ApUserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户搜索记录
 */
@RestController
@RequestMapping("/api/article/history")
public class ApUserSearchController {
    @Autowired
    private ApUserSearchService apUserSearchService;

    /**
     * 查询搜索记录
     */
    @PostMapping("/load")
    public Result<List<ApUserSearch>> load(@RequestBody UserSearchDto dto){
        return apUserSearchService.load(dto);
    }

    /**
     * 用户搜索记录删除
     */
    @PostMapping("/del")
    public Result delUserSearch(@RequestBody UserSearchDto dto){
        return apUserSearchService.delUserSearch(dto);
    }
}
