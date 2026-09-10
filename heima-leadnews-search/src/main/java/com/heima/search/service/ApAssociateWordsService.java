package com.heima.search.service;

import com.heima.common.dtos.Result;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApAssociateWords;

import java.util.List;

public interface ApAssociateWordsService {
    Result<List<ApAssociateWords>> loadAssociateWords(UserSearchDto dto);
}
