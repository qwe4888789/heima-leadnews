package com.heima.article.feign;

import com.heima.common.dtos.Result;
import com.heima.model.article.pojos.ApAuthor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 文章作者Feign接口
 */
@FeignClient(name = "leadnews-article", path = "/api/apAuthor",contextId = "apAuthorFeign")
public interface ApAuthorFeign {

    /**
     * 根据userId查询作者
     */
    @GetMapping("/findApAuthor/{apUserId}")
    public Result<ApAuthor> findApAuthor(@PathVariable("apUserId") Integer apUserId);

    /**
     * 添加文章作者
     * @param record
     * @return
     */
    @PostMapping("/save")
    public Result<ApAuthor> save(@RequestBody ApAuthor record);

    /**
     * 根据自媒体ID查询作者
     */
    @GetMapping("/findApAuthorByWmUserId/{wmUserId}")
    public Result<ApAuthor> findApAuthorByWmUserId(@PathVariable("wmUserId") Integer wmUserId);

    /**
     * 根据id查询
     */
    @GetMapping("/one/{id}")
    public Result<ApAuthor> findOne(@PathVariable("id")Integer id);
}
