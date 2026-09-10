package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.service.ApArticleConfigService;
import com.heima.model.article.pojos.ApArticleConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * APP已发布文章配置表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService {

    @Override
    public void updateIsDown(Long apArticleId, Integer enable) {
        UpdateWrapper<ApArticleConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("article_id",apArticleId);
        updateWrapper.set("is_down",enable==1?0:1);
        update(updateWrapper);
    }
}
