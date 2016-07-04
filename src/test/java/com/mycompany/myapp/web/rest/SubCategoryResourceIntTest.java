package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.YoUVcodeApp;
import com.mycompany.myapp.domain.SubCategory;
import com.mycompany.myapp.repository.SubCategoryRepository;
import com.mycompany.myapp.repository.search.SubCategorySearchRepository;

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
 * Test class for the SubCategoryResource REST controller.
 *
 * @see SubCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YoUVcodeApp.class)
@WebAppConfiguration
@IntegrationTest
public class SubCategoryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_SUB_CATEGORY_TITLE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SUB_CATEGORY_TITLE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATED_DATE);

    private static final ZonedDateTime DEFAULT_LAST_CHNG_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_LAST_CHNG_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_LAST_CHNG_DATE_STR = dateTimeFormatter.format(DEFAULT_LAST_CHNG_DATE);

    @Inject
    private SubCategoryRepository subCategoryRepository;

    @Inject
    private SubCategorySearchRepository subCategorySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubCategoryMockMvc;

    private SubCategory subCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubCategoryResource subCategoryResource = new SubCategoryResource();
        ReflectionTestUtils.setField(subCategoryResource, "subCategorySearchRepository", subCategorySearchRepository);
        ReflectionTestUtils.setField(subCategoryResource, "subCategoryRepository", subCategoryRepository);
        this.restSubCategoryMockMvc = MockMvcBuilders.standaloneSetup(subCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subCategorySearchRepository.deleteAll();
        subCategory = new SubCategory();
        subCategory.setSub_category_title(DEFAULT_SUB_CATEGORY_TITLE);
        subCategory.setActive_ind(DEFAULT_ACTIVE_IND);
        subCategory.setCreated_date(DEFAULT_CREATED_DATE);
        subCategory.setLast_chng_date(DEFAULT_LAST_CHNG_DATE);
    }

    @Test
    @Transactional
    public void createSubCategory() throws Exception {
        int databaseSizeBeforeCreate = subCategoryRepository.findAll().size();

        // Create the SubCategory

        restSubCategoryMockMvc.perform(post("/api/sub-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subCategory)))
                .andExpect(status().isCreated());

        // Validate the SubCategory in the database
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeCreate + 1);
        SubCategory testSubCategory = subCategories.get(subCategories.size() - 1);
        assertThat(testSubCategory.getSub_category_title()).isEqualTo(DEFAULT_SUB_CATEGORY_TITLE);
        assertThat(testSubCategory.isActive_ind()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testSubCategory.getCreated_date()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSubCategory.getLast_chng_date()).isEqualTo(DEFAULT_LAST_CHNG_DATE);

        // Validate the SubCategory in ElasticSearch
        SubCategory subCategoryEs = subCategorySearchRepository.findOne(testSubCategory.getId());
        assertThat(subCategoryEs).isEqualToComparingFieldByField(testSubCategory);
    }

    @Test
    @Transactional
    public void checkSub_category_titleIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryRepository.findAll().size();
        // set the field null
        subCategory.setSub_category_title(null);

        // Create the SubCategory, which fails.

        restSubCategoryMockMvc.perform(post("/api/sub-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subCategory)))
                .andExpect(status().isBadRequest());

        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActive_indIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryRepository.findAll().size();
        // set the field null
        subCategory.setActive_ind(null);

        // Create the SubCategory, which fails.

        restSubCategoryMockMvc.perform(post("/api/sub-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subCategory)))
                .andExpect(status().isBadRequest());

        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreated_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryRepository.findAll().size();
        // set the field null
        subCategory.setCreated_date(null);

        // Create the SubCategory, which fails.

        restSubCategoryMockMvc.perform(post("/api/sub-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subCategory)))
                .andExpect(status().isBadRequest());

        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLast_chng_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = subCategoryRepository.findAll().size();
        // set the field null
        subCategory.setLast_chng_date(null);

        // Create the SubCategory, which fails.

        restSubCategoryMockMvc.perform(post("/api/sub-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subCategory)))
                .andExpect(status().isBadRequest());

        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubCategories() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get all the subCategories
        restSubCategoryMockMvc.perform(get("/api/sub-categories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].sub_category_title").value(hasItem(DEFAULT_SUB_CATEGORY_TITLE.toString())))
                .andExpect(jsonPath("$.[*].active_ind").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
                .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE_STR)))
                .andExpect(jsonPath("$.[*].last_chng_date").value(hasItem(DEFAULT_LAST_CHNG_DATE_STR)));
    }

    @Test
    @Transactional
    public void getSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);

        // Get the subCategory
        restSubCategoryMockMvc.perform(get("/api/sub-categories/{id}", subCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subCategory.getId().intValue()))
            .andExpect(jsonPath("$.sub_category_title").value(DEFAULT_SUB_CATEGORY_TITLE.toString()))
            .andExpect(jsonPath("$.active_ind").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.created_date").value(DEFAULT_CREATED_DATE_STR))
            .andExpect(jsonPath("$.last_chng_date").value(DEFAULT_LAST_CHNG_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingSubCategory() throws Exception {
        // Get the subCategory
        restSubCategoryMockMvc.perform(get("/api/sub-categories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);
        subCategorySearchRepository.save(subCategory);
        int databaseSizeBeforeUpdate = subCategoryRepository.findAll().size();

        // Update the subCategory
        SubCategory updatedSubCategory = new SubCategory();
        updatedSubCategory.setId(subCategory.getId());
        updatedSubCategory.setSub_category_title(UPDATED_SUB_CATEGORY_TITLE);
        updatedSubCategory.setActive_ind(UPDATED_ACTIVE_IND);
        updatedSubCategory.setCreated_date(UPDATED_CREATED_DATE);
        updatedSubCategory.setLast_chng_date(UPDATED_LAST_CHNG_DATE);

        restSubCategoryMockMvc.perform(put("/api/sub-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSubCategory)))
                .andExpect(status().isOk());

        // Validate the SubCategory in the database
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeUpdate);
        SubCategory testSubCategory = subCategories.get(subCategories.size() - 1);
        assertThat(testSubCategory.getSub_category_title()).isEqualTo(UPDATED_SUB_CATEGORY_TITLE);
        assertThat(testSubCategory.isActive_ind()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testSubCategory.getCreated_date()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSubCategory.getLast_chng_date()).isEqualTo(UPDATED_LAST_CHNG_DATE);

        // Validate the SubCategory in ElasticSearch
        SubCategory subCategoryEs = subCategorySearchRepository.findOne(testSubCategory.getId());
        assertThat(subCategoryEs).isEqualToComparingFieldByField(testSubCategory);
    }

    @Test
    @Transactional
    public void deleteSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);
        subCategorySearchRepository.save(subCategory);
        int databaseSizeBeforeDelete = subCategoryRepository.findAll().size();

        // Get the subCategory
        restSubCategoryMockMvc.perform(delete("/api/sub-categories/{id}", subCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean subCategoryExistsInEs = subCategorySearchRepository.exists(subCategory.getId());
        assertThat(subCategoryExistsInEs).isFalse();

        // Validate the database is empty
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        assertThat(subCategories).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSubCategory() throws Exception {
        // Initialize the database
        subCategoryRepository.saveAndFlush(subCategory);
        subCategorySearchRepository.save(subCategory);

        // Search the subCategory
        restSubCategoryMockMvc.perform(get("/api/_search/sub-categories?query=id:" + subCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].sub_category_title").value(hasItem(DEFAULT_SUB_CATEGORY_TITLE.toString())))
            .andExpect(jsonPath("$.[*].active_ind").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].created_date").value(hasItem(DEFAULT_CREATED_DATE_STR)))
            .andExpect(jsonPath("$.[*].last_chng_date").value(hasItem(DEFAULT_LAST_CHNG_DATE_STR)));
    }
}
