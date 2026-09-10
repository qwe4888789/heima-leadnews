package com.heima.article.controller;


import com.heima.article.service.ApEquipmentCodeService;
import com.heima.common.controller.AbstractController;
import com.heima.model.article.pojos.ApEquipmentCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
* APP设备码信息表 控制器</p>
* @author heima
* @since 2021-07-22
*/
@RestController
@RequestMapping("/api/apEquipmentCode")
public class ApEquipmentCodeController extends AbstractController<ApEquipmentCode>{

    private ApEquipmentCodeService apEquipmentCodeService;

    //注入
    @Autowired
    public ApEquipmentCodeController(ApEquipmentCodeService apEquipmentCodeService) {
        super(apEquipmentCodeService);
        this.apEquipmentCodeService=apEquipmentCodeService;
    }

}

