package com.heima.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heima.behavior.feign.ApBehaviorEntryFeign;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.model.behavior.pojos.ApBehaviorEntry;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApUserSearch;
import com.heima.model.user.pojos.ApUser;
import com.heima.search.mapper.ApUserSearchMapper;
import com.heima.search.service.ApUserSearchService;
import com.heima.utils.common.ThreadLocalUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApUserSearchServiceImpl implements ApUserSearchService {
    @Autowired
    private ApUserSearchMapper apUserSearchMapper;

    @Autowired
    private ApBehaviorEntryFeign apBehaviorEntryFeign;

    @Override
    public void saveUserSearch(Map<String, Object> msgMap) {
        //取出参数值
        Integer userId = (Integer)msgMap.get("userId");
        Integer equipmentId = (Integer)msgMap.get("equipmentId");
        String keyword = (String)msgMap.get("keyword");

        //查询是否存在行为实体
        ApBehaviorEntry behaviorEntry = apBehaviorEntryFeign.findByUserIdOrEquipmentId(userId, equipmentId);
        if(behaviorEntry==null)return;

        //查询搜索记录
        QueryWrapper<ApUserSearch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entry_id",behaviorEntry.getId());
        queryWrapper.eq("keyword",keyword);
        ApUserSearch apUserSearch = apUserSearchMapper.selectOne(queryWrapper);

        if(apUserSearch==null){
            //添加用户搜索记录
            apUserSearch = new ApUserSearch();
            apUserSearch.setEntryId(behaviorEntry.getId());
            apUserSearch.setKeyword(keyword);
            apUserSearch.setStatus(true);
            apUserSearch.setCreatedTime(new Date());
            apUserSearchMapper.insert(apUserSearch);
        }else{
            if(apUserSearch.getStatus()==false){
                //修改status为true
                apUserSearch.setStatus(true);
                apUserSearch.setCreatedTime(new Date());
                apUserSearchMapper.updateById(apUserSearch);
            }
        }
    }

    @Override
    public Result<List<ApUserSearch>> load(UserSearchDto dto) {
        if(dto.getPageNum()==0)dto.setPageNum(1);
        if(dto.getPageSize()==0)dto.setPageSize(5);

        Integer userId = null;
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser!=null){
            userId = apUser.getId();
        }

        //查询行为实体
        ApBehaviorEntry behaviorEntry = apBehaviorEntryFeign.findByUserIdOrEquipmentId(userId, dto.getEquipmentId());
        if(behaviorEntry==null)return Result.ok();

        try {
            IPage<ApUserSearch> iPage = new Page<>(dto.getPageNum(),dto.getPageSize());

            QueryWrapper<ApUserSearch> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("entry_id",behaviorEntry.getId());
            queryWrapper.eq("status",1);

            //按照创建时间倒序
            queryWrapper.orderByDesc("created_time");

            iPage = apUserSearchMapper.selectPage(iPage, queryWrapper);

            return Result.ok(iPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }

    @Override
    public Result delUserSearch(UserSearchDto dto) {
        try {
            ApUserSearch apUserSearch = new ApUserSearch();
            apUserSearch.setId(dto.getId());
            apUserSearch.setStatus(false);
            apUserSearch.setCreatedTime(new Date());
            apUserSearchMapper.updateById(apUserSearch);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }
}
