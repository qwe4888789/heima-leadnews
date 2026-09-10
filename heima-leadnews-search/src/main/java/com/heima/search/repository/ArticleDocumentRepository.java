package com.heima.search.repository;

import com.heima.model.search.pojos.ArticleDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ElasticsearchRepository 用于增删改
 */
public interface ArticleDocumentRepository extends ElasticsearchRepository<ArticleDocument,Long> {
}
