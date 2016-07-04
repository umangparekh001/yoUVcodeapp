package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.SubCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SubCategory entity.
 */
public interface SubCategorySearchRepository extends ElasticsearchRepository<SubCategory, Long> {
}
