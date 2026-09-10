package com.heima.article.controller;


import com.heima.article.service.ApEquipmentService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP设备信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apEquipment")
public class ApEquipmentController extends AbstractController<ApEquipment>{

    private ApEquipmentService apEquipmentService;

    //注入
    @Autowired
    public ApEquipmentController(ApEquipmentService apEquipmentService) {
        super(apEquipmentService);
        this.apEquipmentService=apEquipmentService;
    }

}

