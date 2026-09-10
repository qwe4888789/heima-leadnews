package com.heima.search.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.heima.common.constants.RedisConstants;
import com.heima.common.dtos.Result;
import com.heima.common.exception.AppHttpCodeEnum;
import com.heima.common.exception.LeadNewsException;
import com.heima.common.trie.Trie;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ApAssociateWords;
import com.heima.search.mapper.ApAssociateWordsMapper;
import com.heima.search.service.ApAssociateWordsService;
import com.heima.utils.common.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ApAssociateWordsServiceImpl implements ApAssociateWordsService {
    @Autowired
    private ApAssociateWordsMapper apAssociateWordsMapper;

    //建议使用StringRedisTemplate
    @Autowired
    private StringRedisTemplate redisTemplate;

   /* @Override
    public Result<List<ApAssociateWords>> loadAssociateWords(UserSearchDto dto) {
        if(dto.getPageNum()==0)dto.setPageNum(1);
        if(dto.getPageSize()==0)dto.setPageSize(10);

        try {
            IPage<ApAssociateWords> iPage = new Page<>(dto.getPageNum(),dto.getPageSize());

            QueryWrapper<ApAssociateWords> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("associate_words",dto.getSearchWords());

            queryWrapper.orderByDesc("created_time");

            iPage = apAssociateWordsMapper.selectPage(iPage, queryWrapper);

            return Result.ok(iPage.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            throw new LeadNewsException(AppHttpCodeEnum.SERVER_ERROR);
        }
    }*/


    @Override
    public Result<List<ApAssociateWords>> loadAssociateWords(UserSearchDto dto) {
        //1.从redis取出所有联想词
        String associateWords = redisTemplate.opsForValue().get(RedisConstants.ASSOCIATE_WORD_REDIS);

        //2.如果redis没有，从数据库查询，存入redis
        List<ApAssociateWords> list = null;
        if(StringUtils.isNotEmpty(associateWords)){
            /**
             * toBean(): 转换List集合时，List一定存放Map集合
             * nativeRead(): 可以自由转换任意类型
             */
            list = JsonUtils.nativeRead(associateWords, new TypeReference<List<ApAssociateWords>>() {});
        }else{
            list = apAssociateWordsMapper.selectList(null);
            //存入redis
            redisTemplate.opsForValue().set(RedisConstants.ASSOCIATE_WORD_REDIS,JsonUtils.toString(list));
        }

        //3.把所有联想词构建程Trie树
        Trie trie = new Trie();
        if(CollectionUtils.isNotEmpty(list)){
            list.forEach(apAssociateWords -> {
                trie.insert(apAssociateWords.getAssociateWords());
            });
        }

        //4.使用用户搜索关键词匹配Trie树
        List<String> stringList = trie.startWith(dto.getSearchWords());

        List<ApAssociateWords> wordsList = new ArrayList<>();
        stringList.forEach(str->{
            ApAssociateWords word = new ApAssociateWords();
            word.setAssociateWords(str);
            wordsList.add(word);
        });
        //5.封装结构词返回
        return Result.ok(wordsList);
    }
}
