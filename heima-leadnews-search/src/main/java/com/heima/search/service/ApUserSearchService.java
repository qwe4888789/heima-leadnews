package com.heima.search.service;

import com.heima.common.dtos.Result;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApUserSearch;

import java.util.List;
import java.util.Map;

public interface ApUserSearchService {
    void saveUserSearch(Map<String, Object> msgMap);

    Result<List<ApUserSearch>> load(UserSearchDto dto);

    Result delUserSearch(UserSearchDto dto);
}
