package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.Result;
import com.heima.model.article.pojos.ApAuthor;

/**
 * <p>
 * APP文章作者信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface ApAuthorService extends IService<ApAuthor> {

    Result<ApAuthor> findApAuthor(Integer apUserId);

    Result<ApAuthor> findApAuthorByWmUserId(Integer wmUserId);
}
