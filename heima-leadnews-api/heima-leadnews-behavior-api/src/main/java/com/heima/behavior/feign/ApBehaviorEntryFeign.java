package com.heima.behavior.feign;

import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "leadnews-behavior",path = "/api/apBehaviorEntry",contextId = "apBehaviorEntryFeign")
public interface ApBehaviorEntryFeign {

    /**
     * 根据用户ID或设备ID查询行为实体
     */
    @GetMapping("/findByUserIdOrEquipmentId")
    public ApBehaviorEntry findByUserIdOrEquipmentId(
            @RequestParam(value = "userId",required = false) Integer userId,
            @RequestParam(value = "equipmentId",required = false) Integer equipmentId);
}
