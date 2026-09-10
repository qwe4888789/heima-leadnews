package com.heima.test;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.heima.article.feign.ApArticleFeign;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.search.pojos.ArticleDocument;
import com.heima.search.SearchApplication;
import com.heima.search.repository.ArticleDocumentRepository;
import com.heima.utils.common.BeanHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SearchApplication.class)
public class ArticleTest {
    @Autowired
    private ArticleDocumentRepository articleDocumentRepository;
    @Autowired
    private ApArticleFeign apArticleFeign;

    @Test
    public void testImportData(){
        //1.查询所有文章
        List<ApArticle> list = apArticleFeign.findAll().getData();
        if(CollectionUtils.isNotEmpty(list)){
            //把ApArticle数据拷贝到ArticleDocument
            List<ArticleDocument> documents = BeanHelper.copyWithCollection(list,ArticleDocument.class);
            //批量新增
            articleDocumentRepository.saveAll(documents);
        }
    }


}
