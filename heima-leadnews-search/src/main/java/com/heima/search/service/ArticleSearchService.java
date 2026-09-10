package com.heima.search.service;

import com.heima.common.dtos.Result;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ArticleDocument;

import java.util.List;

public interface ArticleSearchService {
    Result<List<ArticleDocument>> articleSearch(UserSearchDto dto);

    void saveToES(String articleId);

    void removeFromES(String articleId);
}
