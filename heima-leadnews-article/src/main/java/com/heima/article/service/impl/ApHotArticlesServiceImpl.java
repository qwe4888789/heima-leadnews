package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApHotArticlesMapper;
import com.heima.article.service.ApHotArticlesService;
import com.heima.model.article.pojos.ApHotArticles;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 热文章表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApHotArticlesServiceImpl extends ServiceImpl<ApHotArticlesMapper, ApHotArticles> implements ApHotArticlesService {

}
