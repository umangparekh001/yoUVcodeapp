package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SubCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubCategory entity.
 */
@SuppressWarnings("unused")
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {

    @Query("select subCategory from SubCategory subCategory where subCategory.created_by.login = ?#{principal.username}")
    List<SubCategory> findByCreated_byIsCurrentUser();

}
