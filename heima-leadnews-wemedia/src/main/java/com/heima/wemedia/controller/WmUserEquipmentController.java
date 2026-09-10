package com.heima.wemedia.controller;


import com.heima.common.controller.AbstractController;
import com.heima.model.wemedia.pojos.WmUserEquipment;
import com.heima.wemedia.service.WmUserEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* 自媒体用户设备信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/wmUserEquipment")
public class WmUserEquipmentController extends AbstractController<WmUserEquipment>{

    private WmUserEquipmentService wmUserEquipmentService;

    //注入
    @Autowired
    public WmUserEquipmentController(WmUserEquipmentService wmUserEquipmentService) {
        super(wmUserEquipmentService);
        this.wmUserEquipmentService=wmUserEquipmentService;
    }

}

