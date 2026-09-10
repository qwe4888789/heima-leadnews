package com.heima.behavior.controller;


import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.common.controller.AbstractController;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP行为实体表,一个行为实体可能是用户或者设备，或者其它 控制器</p>
* @author heima
* @since 2021-08-01
*/
@RestController
@RequestMapping("/api/apBehaviorEntry")
public class ApBehaviorEntryController extends AbstractController<ApBehaviorEntry>{

    private ApBehaviorEntryService apBehaviorEntryService;

    //注入
    @Autowired
    public ApBehaviorEntryController(ApBehaviorEntryService apBehaviorEntryService) {
        super(apBehaviorEntryService);
        this.apBehaviorEntryService=apBehaviorEntryService;
    }

    /**
     * 根据用户ID或设备ID查询行为实体
     */
    @GetMapping("/findByUserIdOrEquipmentId")
    public ApBehaviorEntry findByUserIdOrEquipmentId(
            @RequestParam(value = "userId",required = false) Integer userId,
            @RequestParam(value = "equipmentId",required = false) Integer equipmentId){
        return apBehaviorEntryService.findByUserIdOrEquipmentId(userId,equipmentId);
    }
}

