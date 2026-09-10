package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleLabelMapper;
import com.heima.article.service.ApArticleLabelService;
import com.heima.model.article.pojos.ApArticleLabel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章标签信息表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApArticleLabelServiceImpl extends ServiceImpl<ApArticleLabelMapper, ApArticleLabel> implements ApArticleLabelService {

}
