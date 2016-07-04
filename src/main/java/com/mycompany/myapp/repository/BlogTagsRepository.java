package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogTags;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BlogTags entity.
 */
@SuppressWarnings("unused")
public interface BlogTagsRepository extends JpaRepository<BlogTags,Long> {

    @Query("select distinct blogTags from BlogTags blogTags left join fetch blogTags.blog_ids")
    List<BlogTags> findAllWithEagerRelationships();

    @Query("select blogTags from BlogTags blogTags left join fetch blogTags.blog_ids where blogTags.id =:id")
    BlogTags findOneWithEagerRelationships(@Param("id") Long id);

}
