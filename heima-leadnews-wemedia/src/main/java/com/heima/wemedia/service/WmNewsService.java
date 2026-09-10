package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.common.dtos.PageInfo;
import com.heima.common.dtos.PageRequestDto;
import com.heima.common.dtos.Result;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsSaveDto;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自媒体图文内容信息表 服务类
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface WmNewsService extends IService<WmNews> {

    Result<PageInfo<WmNews>> searchNews(PageRequestDto<WmNewsDto> dto);

    Result submitNews(WmNewsSaveDto dto);

    Result delNews(Integer id);

    Result downOrUp(Map<String, Object> map);

    Result<PageInfo<WmNewsVo>> searchNewsByCondition(PageRequestDto<WmNews> dto);

    Result<List<WmNews>> findRelease();
}
