package com.heima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.user.pojos.ApUserMessage;
import com.heima.user.mapper.ApUserMessageMapper;
import com.heima.user.service.ApUserMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP用户消息通知信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApUserMessageServiceImpl extends ServiceImpl<ApUserMessageMapper, ApUserMessage> implements ApUserMessageService {

}
