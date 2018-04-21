package com.predix.rearview.web.rest;

import com.predix.rearview.RearviewFinal1App;

import com.predix.rearview.domain.Assets;
import com.predix.rearview.repository.AssetsRepository;
import com.predix.rearview.service.AssetsService;
import com.predix.rearview.service.dto.AssetsDTO;
import com.predix.rearview.service.mapper.AssetsMapper;
import com.predix.rearview.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.predix.rearview.web.rest.TestUtil.sameInstant;
import static com.predix.rearview.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssetsResource REST controller.
 *
 * @see AssetsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RearviewFinal1App.class)
public class AssetsResourceIntTest {

    private static final Long DEFAULT_ASSET_UUID = 1L;
    private static final Long UPDATED_ASSET_UUID = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTIES = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPES = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPES = "BBBBBBBBBB";

    private static final String DEFAULT_COORDINATES = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATES = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ASSET_UUID = 1L;
    private static final Long UPDATED_PARENT_ASSET_UUID = 2L;

    private static final ZonedDateTime DEFAULT_ASSET_CREATION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ASSET_CREATION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_ASSET_TO_LOCATION_ID = 1L;
    private static final Long UPDATED_ASSET_TO_LOCATION_ID = 2L;

    @Autowired
    private AssetsRepository assetsRepository;

    @Autowired
    private AssetsMapper assetsMapper;

    @Autowired
    private AssetsService assetsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetsMockMvc;

    private Assets assets;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetsResource assetsResource = new AssetsResource(assetsService);
        this.restAssetsMockMvc = MockMvcBuilders.standaloneSetup(assetsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assets createEntity(EntityManager em) {
        Assets assets = new Assets()
            .assetUuid(DEFAULT_ASSET_UUID)
            .description(DEFAULT_DESCRIPTION)
            .properties(DEFAULT_PROPERTIES)
            .status(DEFAULT_STATUS)
            .assetType(DEFAULT_ASSET_TYPE)
            .mediaType(DEFAULT_MEDIA_TYPE)
            .eventTypes(DEFAULT_EVENT_TYPES)
            .coordinates(DEFAULT_COORDINATES)
            .parentAssetUuid(DEFAULT_PARENT_ASSET_UUID)
            .assetCreationDate(DEFAULT_ASSET_CREATION_DATE)
            .assetToLocationId(DEFAULT_ASSET_TO_LOCATION_ID);
        return assets;
    }

    @Before
    public void initTest() {
        assets = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssets() throws Exception {
        int databaseSizeBeforeCreate = assetsRepository.findAll().size();

        // Create the Assets
        AssetsDTO assetsDTO = assetsMapper.toDto(assets);
        restAssetsMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetsDTO)))
            .andExpect(status().isCreated());

        // Validate the Assets in the database
        List<Assets> assetsList = assetsRepository.findAll();
        assertThat(assetsList).hasSize(databaseSizeBeforeCreate + 1);
        Assets testAssets = assetsList.get(assetsList.size() - 1);
        assertThat(testAssets.getAssetUuid()).isEqualTo(DEFAULT_ASSET_UUID);
        assertThat(testAssets.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssets.getProperties()).isEqualTo(DEFAULT_PROPERTIES);
        assertThat(testAssets.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssets.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testAssets.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testAssets.getEventTypes()).isEqualTo(DEFAULT_EVENT_TYPES);
        assertThat(testAssets.getCoordinates()).isEqualTo(DEFAULT_COORDINATES);
        assertThat(testAssets.getParentAssetUuid()).isEqualTo(DEFAULT_PARENT_ASSET_UUID);
        assertThat(testAssets.getAssetCreationDate()).isEqualTo(DEFAULT_ASSET_CREATION_DATE);
        assertThat(testAssets.getAssetToLocationId()).isEqualTo(DEFAULT_ASSET_TO_LOCATION_ID);
    }

    @Test
    @Transactional
    public void createAssetsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetsRepository.findAll().size();

        // Create the Assets with an existing ID
        assets.setId(1L);
        AssetsDTO assetsDTO = assetsMapper.toDto(assets);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetsMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assets in the database
        List<Assets> assetsList = assetsRepository.findAll();
        assertThat(assetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssets() throws Exception {
        // Initialize the database
        assetsRepository.saveAndFlush(assets);

        // Get all the assetsList
        restAssetsMockMvc.perform(get("/api/assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetUuid").value(hasItem(DEFAULT_ASSET_UUID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].properties").value(hasItem(DEFAULT_PROPERTIES.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eventTypes").value(hasItem(DEFAULT_EVENT_TYPES.toString())))
            .andExpect(jsonPath("$.[*].coordinates").value(hasItem(DEFAULT_COORDINATES.toString())))
            .andExpect(jsonPath("$.[*].parentAssetUuid").value(hasItem(DEFAULT_PARENT_ASSET_UUID.intValue())))
            .andExpect(jsonPath("$.[*].assetCreationDate").value(hasItem(sameInstant(DEFAULT_ASSET_CREATION_DATE))))
            .andExpect(jsonPath("$.[*].assetToLocationId").value(hasItem(DEFAULT_ASSET_TO_LOCATION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getAssets() throws Exception {
        // Initialize the database
        assetsRepository.saveAndFlush(assets);

        // Get the assets
        restAssetsMockMvc.perform(get("/api/assets/{id}", assets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assets.getId().intValue()))
            .andExpect(jsonPath("$.assetUuid").value(DEFAULT_ASSET_UUID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.properties").value(DEFAULT_PROPERTIES.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE.toString()))
            .andExpect(jsonPath("$.eventTypes").value(DEFAULT_EVENT_TYPES.toString()))
            .andExpect(jsonPath("$.coordinates").value(DEFAULT_COORDINATES.toString()))
            .andExpect(jsonPath("$.parentAssetUuid").value(DEFAULT_PARENT_ASSET_UUID.intValue()))
            .andExpect(jsonPath("$.assetCreationDate").value(sameInstant(DEFAULT_ASSET_CREATION_DATE)))
            .andExpect(jsonPath("$.assetToLocationId").value(DEFAULT_ASSET_TO_LOCATION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssets() throws Exception {
        // Get the assets
        restAssetsMockMvc.perform(get("/api/assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssets() throws Exception {
        // Initialize the database
        assetsRepository.saveAndFlush(assets);
        int databaseSizeBeforeUpdate = assetsRepository.findAll().size();

        // Update the assets
        Assets updatedAssets = assetsRepository.findOne(assets.getId());
        // Disconnect from session so that the updates on updatedAssets are not directly saved in db
        em.detach(updatedAssets);
        updatedAssets
            .assetUuid(UPDATED_ASSET_UUID)
            .description(UPDATED_DESCRIPTION)
            .properties(UPDATED_PROPERTIES)
            .status(UPDATED_STATUS)
            .assetType(UPDATED_ASSET_TYPE)
            .mediaType(UPDATED_MEDIA_TYPE)
            .eventTypes(UPDATED_EVENT_TYPES)
            .coordinates(UPDATED_COORDINATES)
            .parentAssetUuid(UPDATED_PARENT_ASSET_UUID)
            .assetCreationDate(UPDATED_ASSET_CREATION_DATE)
            .assetToLocationId(UPDATED_ASSET_TO_LOCATION_ID);
        AssetsDTO assetsDTO = assetsMapper.toDto(updatedAssets);

        restAssetsMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetsDTO)))
            .andExpect(status().isOk());

        // Validate the Assets in the database
        List<Assets> assetsList = assetsRepository.findAll();
        assertThat(assetsList).hasSize(databaseSizeBeforeUpdate);
        Assets testAssets = assetsList.get(assetsList.size() - 1);
        assertThat(testAssets.getAssetUuid()).isEqualTo(UPDATED_ASSET_UUID);
        assertThat(testAssets.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssets.getProperties()).isEqualTo(UPDATED_PROPERTIES);
        assertThat(testAssets.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testAssets.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testAssets.getEventTypes()).isEqualTo(UPDATED_EVENT_TYPES);
        assertThat(testAssets.getCoordinates()).isEqualTo(UPDATED_COORDINATES);
        assertThat(testAssets.getParentAssetUuid()).isEqualTo(UPDATED_PARENT_ASSET_UUID);
        assertThat(testAssets.getAssetCreationDate()).isEqualTo(UPDATED_ASSET_CREATION_DATE);
        assertThat(testAssets.getAssetToLocationId()).isEqualTo(UPDATED_ASSET_TO_LOCATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingAssets() throws Exception {
        int databaseSizeBeforeUpdate = assetsRepository.findAll().size();

        // Create the Assets
        AssetsDTO assetsDTO = assetsMapper.toDto(assets);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetsMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetsDTO)))
            .andExpect(status().isCreated());

        // Validate the Assets in the database
        List<Assets> assetsList = assetsRepository.findAll();
        assertThat(assetsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssets() throws Exception {
        // Initialize the database
        assetsRepository.saveAndFlush(assets);
        int databaseSizeBeforeDelete = assetsRepository.findAll().size();

        // Get the assets
        restAssetsMockMvc.perform(delete("/api/assets/{id}", assets.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Assets> assetsList = assetsRepository.findAll();
        assertThat(assetsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assets.class);
        Assets assets1 = new Assets();
        assets1.setId(1L);
        Assets assets2 = new Assets();
        assets2.setId(assets1.getId());
        assertThat(assets1).isEqualTo(assets2);
        assets2.setId(2L);
        assertThat(assets1).isNotEqualTo(assets2);
        assets1.setId(null);
        assertThat(assets1).isNotEqualTo(assets2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetsDTO.class);
        AssetsDTO assetsDTO1 = new AssetsDTO();
        assetsDTO1.setId(1L);
        AssetsDTO assetsDTO2 = new AssetsDTO();
        assertThat(assetsDTO1).isNotEqualTo(assetsDTO2);
        assetsDTO2.setId(assetsDTO1.getId());
        assertThat(assetsDTO1).isEqualTo(assetsDTO2);
        assetsDTO2.setId(2L);
        assertThat(assetsDTO1).isNotEqualTo(assetsDTO2);
        assetsDTO1.setId(null);
        assertThat(assetsDTO1).isNotEqualTo(assetsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assetsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assetsMapper.fromId(null)).isNull();
    }
}
