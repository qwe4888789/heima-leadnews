package com.heima.behavior.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.behavior.dtos.ReadBehaviorDto;
import com.heima.model.behavior.pojos.ApReadBehavior;

/**
 * <p>
 * APP阅读行为表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
public interface ApReadBehaviorService extends IService<ApReadBehavior> {

    Result read(ReadBehaviorDto dto);
}
