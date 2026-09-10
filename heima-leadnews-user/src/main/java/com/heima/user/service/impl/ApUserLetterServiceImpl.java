package com.heima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.user.pojos.ApUserLetter;
import com.heima.user.mapper.ApUserLetterMapper;
import com.heima.user.service.ApUserLetterService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP用户私信信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApUserLetterServiceImpl extends ServiceImpl<ApUserLetterMapper, ApUserLetter> implements ApUserLetterService {

}
