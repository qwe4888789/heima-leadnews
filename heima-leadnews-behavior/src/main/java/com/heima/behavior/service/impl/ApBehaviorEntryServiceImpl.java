package com.heima.behavior.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.behavior.mapper.ApBehaviorEntryMapper;
import com.heima.behavior.service.ApBehaviorEntryService;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
@Service
@Transactional
public class ApBehaviorEntryServiceImpl extends ServiceImpl<ApBehaviorEntryMapper, ApBehaviorEntry> implements ApBehaviorEntryService {

    @Override
    public ApBehaviorEntry findByUserIdOrEquipmentId(Integer userId, Integer equipmentId) {
        try {
            QueryWrapper<ApBehaviorEntry> queryWrapper = new QueryWrapper<>();

            if(userId!=null){
                queryWrapper.eq("entry_id",userId);
                queryWrapper.eq("type",1);
            }
            if(equipmentId!=null && userId==null){
                queryWrapper.eq("entry_id",equipmentId);
                queryWrapper.eq("type",0);
            }
            return getOne(queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}
