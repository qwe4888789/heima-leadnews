package com.heima.search.controller;

import com.heima.common.dtos.Result;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApAssociateWords;
import com.heima.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article/associate")
public class ApAssociateWordsController {
    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    /**
     * 搜索联想词
     */
    @PostMapping("/load")
    public Result<List<ApAssociateWords>> loadAssociateWords(@RequestBody UserSearchDto dto){
        return apAssociateWordsService.loadAssociateWords(dto);
    }
}
