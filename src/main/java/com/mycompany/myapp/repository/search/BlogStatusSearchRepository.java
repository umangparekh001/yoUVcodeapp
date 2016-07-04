package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.BlogStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BlogStatus entity.
 */
public interface BlogStatusSearchRepository extends ElasticsearchRepository<BlogStatus, Long> {
}
