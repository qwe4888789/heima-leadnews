package com.heima.user.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.user.pojos.ApUserEquipment;
import com.heima.user.service.ApUserEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP用户设备信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apUserEquipment")
public class ApUserEquipmentController extends AbstractController<ApUserEquipment>{

    private ApUserEquipmentService apUserEquipmentService;

    //注入
    @Autowired
    public ApUserEquipmentController(ApUserEquipmentService apUserEquipmentService) {
        super(apUserEquipmentService);
        this.apUserEquipmentService=apUserEquipmentService;
    }

}

