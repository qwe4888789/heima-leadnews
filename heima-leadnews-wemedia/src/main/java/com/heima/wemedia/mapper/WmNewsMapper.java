package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heima.model.wemedia.dtos.WmNewsVo;
import com.heima.model.wemedia.pojos.WmNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 自媒体图文内容信息表 Mapper 接口
 * </p>
 *
 * @author heima
 * @since 2021-07-22
 */
public interface WmNewsMapper extends BaseMapper<WmNews> {

    List<WmNewsVo> searchNewsByCondition(@Param("keyword") String keyword,@Param("start") Long start,@Param("size") Long size);

    Long searchCountNewsByCondition(@Param("keyword")String keyword);
}
