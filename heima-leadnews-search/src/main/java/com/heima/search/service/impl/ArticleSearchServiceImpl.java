package com.heima.search.service.impl;

import com.heima.article.feign.ApArticleFeign;
import com.heima.common.constants.MQConstants;
import com.heima.common.dtos.Result;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.search.pojos.ArticleDocument;
import com.heima.model.user.pojos.ApUser;
import com.heima.search.repository.ArticleDocumentRepository;
import com.heima.search.service.ArticleSearchService;
import com.heima.utils.common.BeanHelper;
import com.heima.utils.common.JsonUtils;
import com.heima.utils.common.ThreadLocalUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {
    @Autowired
    private ElasticsearchRestTemplate restTemplate;
    @Autowired
    private ApArticleFeign apArticleFeign;
    @Autowired
    private ArticleDocumentRepository articleDocumentRepository;
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public Result<List<ArticleDocument>> articleSearch(UserSearchDto dto) {
        if(dto.getPageNum()==0)dto.setPageNum(1);
        if(dto.getPageSize()==0)dto.setPageSize(5);

        //1.封装条件
        //1）创建查询构造器(用于封装查询条件)
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //2）往构造器中添加条件（***）
        //查询条件  所有Query条件都使用QueryBuilders进行创建
        queryBuilder.withQuery(QueryBuilders.matchQuery("title",dto.getSearchWords()));
        //分页
        /**
         * 参数一：页码，从0开始
         * 参数二：页面大小
         */
        queryBuilder.withPageable(PageRequest.of(dto.getPageNum()-1,dto.getPageSize()));
        //排序
        queryBuilder.withSort(SortBuilders.fieldSort("publishTime").order(SortOrder.DESC));

        //3）使用构造器获取查询对象
        NativeSearchQuery searchQuery = queryBuilder.build();

        //2.执行查询，获取结果
        SearchHits<ArticleDocument> searchHits = restTemplate.search(searchQuery, ArticleDocument.class);

        //3.处理结果并返回
        List<ArticleDocument> list = new ArrayList<>();
        for(SearchHit<ArticleDocument> hit:searchHits){
            ArticleDocument content = hit.getContent();
            list.add(content);
        }


        //获取登录用户ID
        Integer userId = null;
        ApUser apUser = (ApUser)ThreadLocalUtils.get();
        if(apUser!=null){
            userId = apUser.getId();
        }

        //同步记录用户搜索数据
        Map<String,Object> msgMap = new HashMap<>();
        msgMap.put("userId",userId);
        msgMap.put("equipmentId",dto.getEquipmentId());
        msgMap.put("keyword",dto.getSearchWords());

        kafkaTemplate.send(MQConstants.USER_SEARCH_TOPIC, JsonUtils.toString(msgMap));

        return Result.ok(list);
    }

    @Override
    public void saveToES(String articleId) {
        //1.查询文章数据
        ApArticle apArticle = apArticleFeign.findById(Long.valueOf(articleId));
        //2.保存数据到ES
        if(apArticle!=null){
            //拷贝数据
            ArticleDocument articleDocument = BeanHelper.copyProperties(apArticle, ArticleDocument.class);
            articleDocumentRepository.save(articleDocument);
        }
    }

    @Override
    public void removeFromES(String articleId) {
        articleDocumentRepository.deleteById(Long.valueOf(articleId));
    }
}
