package com.heima.admin.controller;


import com.heima.admin.service.AdUserEquipmentService;
import com.heima.common.controller.AbstractController;
import com.heima.model.admin.pojos.AdUserEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 管理员设备信息表 控制器</p>
* @author heima
* @since 2021-07-21
*/
@RestController
@RequestMapping("/api/adUserEquipment")
public class AdUserEquipmentController extends AbstractController<AdUserEquipment>{

    private AdUserEquipmentService adUserEquipmentService;

    //注入
    @Autowired
    public AdUserEquipmentController(AdUserEquipmentService adUserEquipmentService) {
        super(adUserEquipmentService);
        this.adUserEquipmentService=adUserEquipmentService;
    }

}

