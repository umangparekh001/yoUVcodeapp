package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BlogStatus;
import com.mycompany.myapp.repository.BlogStatusRepository;
import com.mycompany.myapp.repository.search.BlogStatusSearchRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.BlogStatusDTO;
import com.mycompany.myapp.web.rest.mapper.BlogStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing BlogStatus.
 */
@RestController
@RequestMapping("/api")
public class BlogStatusResource {

    private final Logger log = LoggerFactory.getLogger(BlogStatusResource.class);
        
    @Inject
    private BlogStatusRepository blogStatusRepository;
    
    @Inject
    private BlogStatusMapper blogStatusMapper;
    
    @Inject
    private BlogStatusSearchRepository blogStatusSearchRepository;
    
    /**
     * POST  /blog-statuses : Create a new blogStatus.
     *
     * @param blogStatusDTO the blogStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blogStatusDTO, or with status 400 (Bad Request) if the blogStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/blog-statuses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BlogStatusDTO> createBlogStatus(@Valid @RequestBody BlogStatusDTO blogStatusDTO) throws URISyntaxException {
        log.debug("REST request to save BlogStatus : {}", blogStatusDTO);
        if (blogStatusDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("blogStatus", "idexists", "A new blogStatus cannot already have an ID")).body(null);
        }
        BlogStatus blogStatus = blogStatusMapper.blogStatusDTOToBlogStatus(blogStatusDTO);
        blogStatus = blogStatusRepository.save(blogStatus);
        BlogStatusDTO result = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);
        blogStatusSearchRepository.save(blogStatus);
        return ResponseEntity.created(new URI("/api/blog-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("blogStatus", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blog-statuses : Updates an existing blogStatus.
     *
     * @param blogStatusDTO the blogStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blogStatusDTO,
     * or with status 400 (Bad Request) if the blogStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the blogStatusDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/blog-statuses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BlogStatusDTO> updateBlogStatus(@Valid @RequestBody BlogStatusDTO blogStatusDTO) throws URISyntaxException {
        log.debug("REST request to update BlogStatus : {}", blogStatusDTO);
        if (blogStatusDTO.getId() == null) {
            return createBlogStatus(blogStatusDTO);
        }
        BlogStatus blogStatus = blogStatusMapper.blogStatusDTOToBlogStatus(blogStatusDTO);
        blogStatus = blogStatusRepository.save(blogStatus);
        BlogStatusDTO result = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);
        blogStatusSearchRepository.save(blogStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("blogStatus", blogStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blog-statuses : get all the blogStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of blogStatuses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/blog-statuses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BlogStatusDTO>> getAllBlogStatuses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BlogStatuses");
        Page<BlogStatus> page = blogStatusRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blog-statuses");
        return new ResponseEntity<>(blogStatusMapper.blogStatusesToBlogStatusDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /blog-statuses/:id : get the "id" blogStatus.
     *
     * @param id the id of the blogStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blogStatusDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/blog-statuses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BlogStatusDTO> getBlogStatus(@PathVariable Long id) {
        log.debug("REST request to get BlogStatus : {}", id);
        BlogStatus blogStatus = blogStatusRepository.findOne(id);
        BlogStatusDTO blogStatusDTO = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);
        return Optional.ofNullable(blogStatusDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /blog-statuses/:id : delete the "id" blogStatus.
     *
     * @param id the id of the blogStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/blog-statuses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBlogStatus(@PathVariable Long id) {
        log.debug("REST request to delete BlogStatus : {}", id);
        blogStatusRepository.delete(id);
        blogStatusSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("blogStatus", id.toString())).build();
    }

    /**
     * SEARCH  /_search/blog-statuses?query=:query : search for the blogStatus corresponding
     * to the query.
     *
     * @param query the query of the blogStatus search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/blog-statuses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BlogStatusDTO>> searchBlogStatuses(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of BlogStatuses for query {}", query);
        Page<BlogStatus> page = blogStatusSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/blog-statuses");
        return new ResponseEntity<>(blogStatusMapper.blogStatusesToBlogStatusDTOs(page.getContent()), headers, HttpStatus.OK);
    }


}
