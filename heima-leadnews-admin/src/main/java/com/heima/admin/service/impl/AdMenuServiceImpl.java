package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdMenuMapper;
import com.heima.admin.service.AdMenuService;
import com.heima.model.admin.pojos.AdMenu;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单资源信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-21
 */
@Service
public class AdMenuServiceImpl extends ServiceImpl<AdMenuMapper, AdMenu> implements AdMenuService {

}
