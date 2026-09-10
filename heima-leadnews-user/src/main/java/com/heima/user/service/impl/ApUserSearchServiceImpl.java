package com.heima.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.user.pojos.ApUserSearch;
import com.heima.user.mapper.ApUserSearchMapper;
import com.heima.user.service.ApUserSearchService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * APP用户搜索信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApUserSearchServiceImpl extends ServiceImpl<ApUserSearchMapper, ApUserSearch> implements ApUserSearchService {

}
