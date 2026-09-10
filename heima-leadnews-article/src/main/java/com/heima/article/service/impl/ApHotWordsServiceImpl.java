package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApHotWordsMapper;
import com.heima.article.service.ApHotWordsService;
import com.heima.model.article.pojos.ApHotWords;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 搜索热词表 服务实现类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
@Service
public class ApHotWordsServiceImpl extends ServiceImpl<ApHotWordsMapper, ApHotWords> implements ApHotWordsService {

}
