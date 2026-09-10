package com.heima.behavior.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.behavior.mapper.ApUnlikesBehaviorMapper;
import com.heima.behavior.service.ApUnlikesBehaviorService;
import com.heima.model.behavior.pojos.ApUnlikesBehavior;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * APP不喜欢行为表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-08-01
 */
@Service
@Transactional
public class ApUnlikesBehaviorServiceImpl extends ServiceImpl<ApUnlikesBehaviorMapper, ApUnlikesBehavior> implements ApUnlikesBehaviorService {

    @Override
    public ApUnlikesBehavior findUnlike(Integer entryId, Long articleId) {
        QueryWrapper<ApUnlikesBehavior> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_id",entryId);
        queryWrapper.eq("article_id",articleId);
        return getOne(queryWrapper);
    }
}
