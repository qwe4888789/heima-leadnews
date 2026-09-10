package com.heima.admin.service;

import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.model.admin.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;

import java.util.List;

public interface WemediaNewsScanService {

    /**
     * 文章的自动审核
     */
    public void autoScanByMediaNewsId(Integer id);

    Result<PageInfo<WmNewsVo>> findNews(PageRequestDto<WmNews> dto);

    Result manualScanNews(NewsAuthDto dto, Integer code);

    void scahedulePublishNews(List<WmNews> wmNews);
}
