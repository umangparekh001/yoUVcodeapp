package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.YoUVcodeApp;
import com.mycompany.myapp.domain.BlogStatus;
import com.mycompany.myapp.repository.BlogStatusRepository;
import com.mycompany.myapp.repository.search.BlogStatusSearchRepository;
import com.mycompany.myapp.web.rest.dto.BlogStatusDTO;
import com.mycompany.myapp.web.rest.mapper.BlogStatusMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BlogStatusResource REST controller.
 *
 * @see BlogStatusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YoUVcodeApp.class)
@WebAppConfiguration
@IntegrationTest
public class BlogStatusResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_BLOG_STATUS = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BLOG_STATUS = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATED_DATE);

    private static final ZonedDateTime DEFAULT_LAST_CHNG_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_CHNG_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_CHNG_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_CHNG_DATE);

    @Inject
    private BlogStatusRepository blogStatusRepository;

    @Inject
    private BlogStatusMapper blogStatusMapper;

    @Inject
    private BlogStatusSearchRepository blogStatusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBlogStatusMockMvc;

    private BlogStatus blogStatus;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BlogStatusResource blogStatusResource = new BlogStatusResource();
        ReflectionTestUtils.setField(blogStatusResource, "blogStatusSearchRepository", blogStatusSearchRepository);
        ReflectionTestUtils.setField(blogStatusResource, "blogStatusRepository", blogStatusRepository);
        ReflectionTestUtils.setField(blogStatusResource, "blogStatusMapper", blogStatusMapper);
        this.restBlogStatusMockMvc = MockMvcBuilders.standaloneSetup(blogStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        blogStatusSearchRepository.deleteAll();
        blogStatus = new BlogStatus();
        blogStatus.setBlog_status(DEFAULT_BLOG_STATUS);
        blogStatus.setCreated_date(DEFAULT_CREATED_DATE);
        blogStatus.setLast_chng_date(DEFAULT_LAST_CHNG_DATE);
    }

    @Test
    @Transactional
    public void createBlogStatus() throws Exception {
        int databaseSizeBeforeCreate = blogStatusRepository.findAll().size();

        // Create the BlogStatus
        BlogStatusDTO blogStatusDTO = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);

        restBlogStatusMockMvc.perform(post("/api/blog-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogStatusDTO)))
                .andExpect(status().isCreated());

        // Validate the BlogStatus in the database
        List<BlogStatus> blogStatuses = blogStatusRepository.findAll();
        assertThat(blogStatuses).hasSize(databaseSizeBeforeCreate + 1);
        BlogStatus testBlogStatus = blogStatuses.get(blogStatuses.size() - 1);
        assertThat(testBlogStatus.getBlog_status()).isEqualTo(DEFAULT_BLOG_STATUS);
        assertThat(testBlogStatus.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBlogStatus.getLast_chng_date()).isEqualTo(DEFAULT_LAST_CHNG_DATE);

        // Validate the BlogStatus in ElasticSearch
        BlogStatus blogStatusEs = blogStatusSearchRepository.findOne(testBlogStatus.getId());
        assertThat(blogStatusEs).isEqualToComparingFieldByField(testBlogStatus);
    }

    @Test
    @Transactional
    public void checkBlog_statusIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogStatusRepository.findAll().size();
        // set the field null
        blogStatus.setBlog_status(null);

        // Create the BlogStatus, which fails.
        BlogStatusDTO blogStatusDTO = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);

        restBlogStatusMockMvc.perform(post("/api/blog-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogStatusDTO)))
                .andExpect(status().isBadRequest());

        List<BlogStatus> blogStatuses = blogStatusRepository.findAll();
        assertThat(blogStatuses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogStatusRepository.findAll().size();
        // set the field null
        blogStatus.setCreated_date(null);

        // Create the BlogStatus, which fails.
        BlogStatusDTO blogStatusDTO = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);

        restBlogStatusMockMvc.perform(post("/api/blog-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogStatusDTO)))
                .andExpect(status().isBadRequest());

        List<BlogStatus> blogStatuses = blogStatusRepository.findAll();
        assertThat(blogStatuses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_chng_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogStatusRepository.findAll().size();
        // set the field null
        blogStatus.setLast_chng_date(null);

        // Create the BlogStatus, which fails.
        BlogStatusDTO blogStatusDTO = blogStatusMapper.blogStatusToBlogStatusDTO(blogStatus);

        restBlogStatusMockMvc.perform(post("/api/blog-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogStatusDTO)))
                .andExpect(status().isBadRequest());

        List<BlogStatus> blogStatuses = blogStatusRepository.findAll();
        assertThat(blogStatuses).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBlogStatuses() throws Exception {
        // Initialize the database
        blogStatusRepository.saveAndFlush(blogStatus);

        // Get all the blogStatuses
        restBlogStatusMockMvc.perform(get("/api/blog-statuses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(blogStatus.getId().intValue())))
                .andExpect(jsonPath("$.[*].blog_status").value(hasItem(DEFAULT_BLOG_STATUS.toString())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_chng_date").value(hasItem(DEFAULT_LAST_CHNG_DATE_STR)));
    }

    @Test
    @Transactional
    public void getBlogStatus() throws Exception {
        // Initialize the database
        blogStatusRepository.saveAndFlush(blogStatus);

        // Get the blogStatus
        restBlogStatusMockMvc.perform(get("/api/blog-statuses/{id}", blogStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(blogStatus.getId().intValue()))
            .andExpect(jsonPath("$.blog_status").value(DEFAULT_BLOG_STATUS.toString()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE_STR))
            .andExpect(jsonPath("$.last_chng_date").value(DEFAULT_LAST_CHNG_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingBlogStatus() throws Exception {
        // Get the blogStatus
        restBlogStatusMockMvc.perform(get("/api/blog-statuses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogStatus() throws Exception {
        // Initialize the database
        blogStatusRepository.saveAndFlush(blogStatus);
        blogStatusSearchRepository.save(blogStatus);
        int databaseSizeBeforeUpdate = blogStatusRepository.findAll().size();

        // Update the blogStatus
        BlogStatus updatedBlogStatus = new BlogStatus();
        updatedBlogStatus.setId(blogStatus.getId());
        updatedBlogStatus.setBlog_status(UPDATED_BLOG_STATUS);
        updatedBlogStatus.setCreated_date(UPDATED_CREATED_DATE);
        updatedBlogStatus.setLast_chng_date(UPDATED_LAST_CHNG_DATE);
        BlogStatusDTO blogStatusDTO = blogStatusMapper.blogStatusToBlogStatusDTO(updatedBlogStatus);

        restBlogStatusMockMvc.perform(put("/api/blog-statuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(blogStatusDTO)))
                .andExpect(status().isOk());

        // Validate the BlogStatus in the database
        List<BlogStatus> blogStatuses = blogStatusRepository.findAll();
        assertThat(blogStatuses).hasSize(databaseSizeBeforeUpdate);
        BlogStatus testBlogStatus = blogStatuses.get(blogStatuses.size() - 1);
        assertThat(testBlogStatus.getBlog_status()).isEqualTo(UPDATED_BLOG_STATUS);
        assertThat(testBlogStatus.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBlogStatus.getLast_chng_date()).isEqualTo(UPDATED_LAST_CHNG_DATE);

        // Validate the BlogStatus in ElasticSearch
        BlogStatus blogStatusEs = blogStatusSearchRepository.findOne(testBlogStatus.getId());
        assertThat(blogStatusEs).isEqualToComparingFieldByField(testBlogStatus);
    }

    @Test
    @Transactional
    public void deleteBlogStatus() throws Exception {
        // Initialize the database
        blogStatusRepository.saveAndFlush(blogStatus);
        blogStatusSearchRepository.save(blogStatus);
        int databaseSizeBeforeDelete = blogStatusRepository.findAll().size();

        // Get the blogStatus
        restBlogStatusMockMvc.perform(delete("/api/blog-statuses/{id}", blogStatus.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean blogStatusExistsInEs = blogStatusSearchRepository.exists(blogStatus.getId());
        assertThat(blogStatusExistsInEs).isFalse();

        // Validate the database is empty
        List<BlogStatus> blogStatuses = blogStatusRepository.findAll();
        assertThat(blogStatuses).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBlogStatus() throws Exception {
        // Initialize the database
        blogStatusRepository.saveAndFlush(blogStatus);
        blogStatusSearchRepository.save(blogStatus);

        // Search the blogStatus
        restBlogStatusMockMvc.perform(get("/api/_search/blog-statuses?query=id:" + blogStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].blog_status").value(hasItem(DEFAULT_BLOG_STATUS.toString())))
            .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE_STR)))
            .andExpect(jsonPath("$.[*].last_chng_date").value(hasItem(DEFAULT_LAST_CHNG_DATE_STR)));
    }
}
