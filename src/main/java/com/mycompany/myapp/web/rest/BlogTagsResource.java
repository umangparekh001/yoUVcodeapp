package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BlogTags;
import com.mycompany.myapp.repository.BlogTagsRepository;
import com.mycompany.myapp.repository.search.BlogTagsSearchRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.web.rest.dto.BlogTagsDTO;
import com.mycompany.myapp.web.rest.mapper.BlogTagsMapper;
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
 * REST controller for managing BlogTags.
 */
@RestController
@RequestMapping("/api")
public class BlogTagsResource {

    private final Logger log = LoggerFactory.getLogger(BlogTagsResource.class);
        
    @Inject
    private BlogTagsRepository blogTagsRepository;
    
    @Inject
    private BlogTagsMapper blogTagsMapper;
    
    @Inject
    private BlogTagsSearchRepository blogTagsSearchRepository;
    
    /**
     * POST  /blog-tags : Create a new blogTags.
     *
     * @param blogTagsDTO the blogTagsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new blogTagsDTO, or with status 400 (Bad Request) if the blogTags has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/blog-tags",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BlogTagsDTO> createBlogTags(@Valid @RequestBody BlogTagsDTO blogTagsDTO) throws URISyntaxException {
        log.debug("REST request to save BlogTags : {}", blogTagsDTO);
        if (blogTagsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("blogTags", "idexists", "A new blogTags cannot already have an ID")).body(null);
        }
        BlogTags blogTags = blogTagsMapper.blogTagsDTOToBlogTags(blogTagsDTO);
        blogTags = blogTagsRepository.save(blogTags);
        BlogTagsDTO result = blogTagsMapper.blogTagsToBlogTagsDTO(blogTags);
        blogTagsSearchRepository.save(blogTags);
        return ResponseEntity.created(new URI("/api/blog-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("blogTags", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blog-tags : Updates an existing blogTags.
     *
     * @param blogTagsDTO the blogTagsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated blogTagsDTO,
     * or with status 400 (Bad Request) if the blogTagsDTO is not valid,
     * or with status 500 (Internal Server Error) if the blogTagsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/blog-tags",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BlogTagsDTO> updateBlogTags(@Valid @RequestBody BlogTagsDTO blogTagsDTO) throws URISyntaxException {
        log.debug("REST request to update BlogTags : {}", blogTagsDTO);
        if (blogTagsDTO.getId() == null) {
            return createBlogTags(blogTagsDTO);
        }
        BlogTags blogTags = blogTagsMapper.blogTagsDTOToBlogTags(blogTagsDTO);
        blogTags = blogTagsRepository.save(blogTags);
        BlogTagsDTO result = blogTagsMapper.blogTagsToBlogTagsDTO(blogTags);
        blogTagsSearchRepository.save(blogTags);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("blogTags", blogTagsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blog-tags : get all the blogTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of blogTags in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/blog-tags",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BlogTagsDTO>> getAllBlogTags(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BlogTags");
        Page<BlogTags> page = blogTagsRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blog-tags");
        return new ResponseEntity<>(blogTagsMapper.blogTagsToBlogTagsDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /blog-tags/:id : get the "id" blogTags.
     *
     * @param id the id of the blogTagsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the blogTagsDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/blog-tags/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BlogTagsDTO> getBlogTags(@PathVariable Long id) {
        log.debug("REST request to get BlogTags : {}", id);
        BlogTags blogTags = blogTagsRepository.findOneWithEagerRelationships(id);
        BlogTagsDTO blogTagsDTO = blogTagsMapper.blogTagsToBlogTagsDTO(blogTags);
        return Optional.ofNullable(blogTagsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /blog-tags/:id : delete the "id" blogTags.
     *
     * @param id the id of the blogTagsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/blog-tags/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBlogTags(@PathVariable Long id) {
        log.debug("REST request to delete BlogTags : {}", id);
        blogTagsRepository.delete(id);
        blogTagsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("blogTags", id.toString())).build();
    }

    /**
     * SEARCH  /_search/blog-tags?query=:query : search for the blogTags corresponding
     * to the query.
     *
     * @param query the query of the blogTags search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/blog-tags",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BlogTagsDTO>> searchBlogTags(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of BlogTags for query {}", query);
        Page<BlogTags> page = blogTagsSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/blog-tags");
        return new ResponseEntity<>(blogTagsMapper.blogTagsToBlogTagsDTOs(page.getContent()), headers, HttpStatus.OK);
    }


}
