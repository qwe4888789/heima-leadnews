package com.heima.user.feign;

import com.heima.common.dtos.Result;
import com.heima.model.user.pojos.ApUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "leadnews-user",path = "/api/apUser",contextId = "apUserFeign")
public interface ApUserFeign {

    /**
     * 根据id查询
     */
    @GetMapping("/one/{id}")
    public Result<ApUser> findOne(@PathVariable("id")Integer id);
}
