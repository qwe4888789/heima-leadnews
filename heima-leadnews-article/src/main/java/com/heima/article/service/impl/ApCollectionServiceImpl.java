package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApCollectionMapper;
import com.heima.article.service.ApCollectionService;
import com.heima.model.article.pojos.ApCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * APP收藏信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApCollectionServiceImpl extends ServiceImpl<ApCollectionMapper, ApCollection> implements ApCollectionService {

    @Override
    public ApCollection findCollection(Integer entryId, Long articleId) {
        QueryWrapper<ApCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_id",entryId);
        queryWrapper.eq("article_id",articleId);
        return getOne(queryWrapper);
    }
}
