package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogStatus;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BlogStatus entity.
 */
@SuppressWarnings("unused")
public interface BlogStatusRepository extends JpaRepository<BlogStatus,Long> {

    @Query("select blogStatus from BlogStatus blogStatus where blogStatus.created_by.login = ?#{principal.username}")
    List<BlogStatus> findByCreated_byIsCurrentUser();

    @Query("select blogStatus from BlogStatus blogStatus where blogStatus.last_chng_by.login = ?#{principal.username}")
    List<BlogStatus> findByLast_chng_byIsCurrentUser();

}
