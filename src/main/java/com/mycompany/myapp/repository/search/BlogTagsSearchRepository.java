package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.BlogTags;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BlogTags entity.
 */
public interface BlogTagsSearchRepository extends ElasticsearchRepository<BlogTags, Long> {
}
