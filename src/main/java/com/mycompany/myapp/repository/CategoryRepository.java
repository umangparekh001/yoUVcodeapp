package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Category;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Category entity.
 */
@SuppressWarnings("unused")
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select category from Category category where category.changed_by.login = ?#{principal.username}")
    List<Category> findByChanged_byIsCurrentUser();

    @Query("select category from Category category where category.last_chng_by.login = ?#{principal.username}")
    List<Category> findByLast_chng_byIsCurrentUser();

}
