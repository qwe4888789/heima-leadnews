package com.heima.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApAuthorMapper;
import com.heima.article.service.ApAuthorService;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.article.pojos.ApAuthor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * APP文章作者信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
@Transactional
public class ApAuthorServiceImpl extends ServiceImpl<ApAuthorMapper, ApAuthor> implements ApAuthorService {

    @Override
    public Result<ApAuthor> findApAuthor(Integer apUserId) {
        try {
            QueryWrapper<ApAuthor> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",apUserId);
            ApAuthor apAuthor = getOne(queryWrapper);
            return Result.ok(apAuthor);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result<ApAuthor> findApAuthorByWmUserId(Integer wmUserId) {
        try {
            QueryWrapper<ApAuthor> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wm_user_id",wmUserId);
            return Result.ok(getOne(queryWrapper));
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}
