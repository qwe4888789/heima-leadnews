package com.heima.wemedia.feign;

import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "leadnews-wemedia",path = "/api/wmNews",contextId = "wmNewsFeign" )
public interface WmNewsFeign {

    /**
     * 根据id查询
     */
    @GetMapping("/one/{id}")
    public Result<WmNews> findOne(@PathVariable("id")Integer id);

    /**
     * 更新自媒体文章
     * @param record
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody WmNews record);

    /**
     * 查询待审核的文章列表
     */
    @PostMapping("/searchNewsByCondition")
    public Result<PageInfo<WmNewsVo>> searchNewsByCondition(@RequestBody PageRequestDto<WmNews> dto);

    /**
     * 查询待定时发布的文章
     */
    @GetMapping("/findRelease")
    public Result<List<WmNews>> findRelease();
}
