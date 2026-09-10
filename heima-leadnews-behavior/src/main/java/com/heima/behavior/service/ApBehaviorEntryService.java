package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.behavior.pojos.ApBehaviorEntry;

/**
 * <p>
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它 服务类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
public interface ApBehaviorEntryService extends IService<ApBehaviorEntry> {

    /**
     * 根据用户ID或设备ID查询行为实体
     */
    public ApBehaviorEntry findByUserIdOrEquipmentId(Integer userId,Integer equipmentId);
}
