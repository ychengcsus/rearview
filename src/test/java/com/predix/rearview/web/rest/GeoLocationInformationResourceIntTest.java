package com.predix.rearview.web.rest;

import com.predix.rearview.RearviewSandiegoApp;

import com.predix.rearview.domain.GeoLocationInformation;
import com.predix.rearview.repository.GeoLocationInformationRepository;
import com.predix.rearview.service.GeoLocationInformationService;
import com.predix.rearview.service.dto.GeoLocationInformationDTO;
import com.predix.rearview.service.mapper.GeoLocationInformationMapper;
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
import java.util.List;

import static com.predix.rearview.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GeoLocationInformationResource REST controller.
 *
 * @see GeoLocationInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RearviewSandiegoApp.class)
public class GeoLocationInformationResourceIntTest {

    private static final Long DEFAULT_LOCATION_UUID = 1L;
    private static final Long UPDATED_LOCATION_UUID = 2L;

    private static final String DEFAULT_LOCATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_LOCATION_UUID = 1L;
    private static final Long UPDATED_PARENT_LOCATION_UUID = 2L;

    private static final String DEFAULT_COORDINATES_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COORDINATES_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_COORDINATES = 1L;
    private static final Long UPDATED_COORDINATES = 2L;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Long DEFAULT_ZIPCODE = 1L;
    private static final Long UPDATED_ZIPCODE = 2L;

    private static final String DEFAULT_TIMEZONE = "AAAAAAAAAA";
    private static final String UPDATED_TIMEZONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ANALYTIC_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_ANALYTIC_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private GeoLocationInformationRepository geoLocationInformationRepository;

    @Autowired
    private GeoLocationInformationMapper geoLocationInformationMapper;

    @Autowired
    private GeoLocationInformationService geoLocationInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeoLocationInformationMockMvc;

    private GeoLocationInformation geoLocationInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeoLocationInformationResource geoLocationInformationResource = new GeoLocationInformationResource(geoLocationInformationService);
        this.restGeoLocationInformationMockMvc = MockMvcBuilders.standaloneSetup(geoLocationInformationResource)
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
    public static GeoLocationInformation createEntity(EntityManager em) {
        GeoLocationInformation geoLocationInformation = new GeoLocationInformation()
            .locationUuid(DEFAULT_LOCATION_UUID)
            .locationType(DEFAULT_LOCATION_TYPE)
            .parentLocationUuid(DEFAULT_PARENT_LOCATION_UUID)
            .coordinatesType(DEFAULT_COORDINATES_TYPE)
            .coordinates(DEFAULT_COORDINATES)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .zipcode(DEFAULT_ZIPCODE)
            .timezone(DEFAULT_TIMEZONE)
            .address(DEFAULT_ADDRESS)
            .analyticCategory(DEFAULT_ANALYTIC_CATEGORY);
        return geoLocationInformation;
    }

    @Before
    public void initTest() {
        geoLocationInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeoLocationInformation() throws Exception {
        int databaseSizeBeforeCreate = geoLocationInformationRepository.findAll().size();

        // Create the GeoLocationInformation
        GeoLocationInformationDTO geoLocationInformationDTO = geoLocationInformationMapper.toDto(geoLocationInformation);
        restGeoLocationInformationMockMvc.perform(post("/api/geo-location-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoLocationInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the GeoLocationInformation in the database
        List<GeoLocationInformation> geoLocationInformationList = geoLocationInformationRepository.findAll();
        assertThat(geoLocationInformationList).hasSize(databaseSizeBeforeCreate + 1);
        GeoLocationInformation testGeoLocationInformation = geoLocationInformationList.get(geoLocationInformationList.size() - 1);
        assertThat(testGeoLocationInformation.getLocationUuid()).isEqualTo(DEFAULT_LOCATION_UUID);
        assertThat(testGeoLocationInformation.getLocationType()).isEqualTo(DEFAULT_LOCATION_TYPE);
        assertThat(testGeoLocationInformation.getParentLocationUuid()).isEqualTo(DEFAULT_PARENT_LOCATION_UUID);
        assertThat(testGeoLocationInformation.getCoordinatesType()).isEqualTo(DEFAULT_COORDINATES_TYPE);
        assertThat(testGeoLocationInformation.getCoordinates()).isEqualTo(DEFAULT_COORDINATES);
        assertThat(testGeoLocationInformation.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testGeoLocationInformation.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testGeoLocationInformation.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testGeoLocationInformation.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testGeoLocationInformation.getTimezone()).isEqualTo(DEFAULT_TIMEZONE);
        assertThat(testGeoLocationInformation.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testGeoLocationInformation.getAnalyticCategory()).isEqualTo(DEFAULT_ANALYTIC_CATEGORY);
    }

    @Test
    @Transactional
    public void createGeoLocationInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoLocationInformationRepository.findAll().size();

        // Create the GeoLocationInformation with an existing ID
        geoLocationInformation.setId(1L);
        GeoLocationInformationDTO geoLocationInformationDTO = geoLocationInformationMapper.toDto(geoLocationInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoLocationInformationMockMvc.perform(post("/api/geo-location-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoLocationInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeoLocationInformation in the database
        List<GeoLocationInformation> geoLocationInformationList = geoLocationInformationRepository.findAll();
        assertThat(geoLocationInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGeoLocationInformations() throws Exception {
        // Initialize the database
        geoLocationInformationRepository.saveAndFlush(geoLocationInformation);

        // Get all the geoLocationInformationList
        restGeoLocationInformationMockMvc.perform(get("/api/geo-location-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoLocationInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationUuid").value(hasItem(DEFAULT_LOCATION_UUID.intValue())))
            .andExpect(jsonPath("$.[*].locationType").value(hasItem(DEFAULT_LOCATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].parentLocationUuid").value(hasItem(DEFAULT_PARENT_LOCATION_UUID.intValue())))
            .andExpect(jsonPath("$.[*].coordinatesType").value(hasItem(DEFAULT_COORDINATES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].coordinates").value(hasItem(DEFAULT_COORDINATES.intValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE.intValue())))
            .andExpect(jsonPath("$.[*].timezone").value(hasItem(DEFAULT_TIMEZONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].analyticCategory").value(hasItem(DEFAULT_ANALYTIC_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getGeoLocationInformation() throws Exception {
        // Initialize the database
        geoLocationInformationRepository.saveAndFlush(geoLocationInformation);

        // Get the geoLocationInformation
        restGeoLocationInformationMockMvc.perform(get("/api/geo-location-informations/{id}", geoLocationInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geoLocationInformation.getId().intValue()))
            .andExpect(jsonPath("$.locationUuid").value(DEFAULT_LOCATION_UUID.intValue()))
            .andExpect(jsonPath("$.locationType").value(DEFAULT_LOCATION_TYPE.toString()))
            .andExpect(jsonPath("$.parentLocationUuid").value(DEFAULT_PARENT_LOCATION_UUID.intValue()))
            .andExpect(jsonPath("$.coordinatesType").value(DEFAULT_COORDINATES_TYPE.toString()))
            .andExpect(jsonPath("$.coordinates").value(DEFAULT_COORDINATES.intValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE.intValue()))
            .andExpect(jsonPath("$.timezone").value(DEFAULT_TIMEZONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.analyticCategory").value(DEFAULT_ANALYTIC_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeoLocationInformation() throws Exception {
        // Get the geoLocationInformation
        restGeoLocationInformationMockMvc.perform(get("/api/geo-location-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeoLocationInformation() throws Exception {
        // Initialize the database
        geoLocationInformationRepository.saveAndFlush(geoLocationInformation);
        int databaseSizeBeforeUpdate = geoLocationInformationRepository.findAll().size();

        // Update the geoLocationInformation
        GeoLocationInformation updatedGeoLocationInformation = geoLocationInformationRepository.findOne(geoLocationInformation.getId());
        // Disconnect from session so that the updates on updatedGeoLocationInformation are not directly saved in db
        em.detach(updatedGeoLocationInformation);
        updatedGeoLocationInformation
            .locationUuid(UPDATED_LOCATION_UUID)
            .locationType(UPDATED_LOCATION_TYPE)
            .parentLocationUuid(UPDATED_PARENT_LOCATION_UUID)
            .coordinatesType(UPDATED_COORDINATES_TYPE)
            .coordinates(UPDATED_COORDINATES)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .zipcode(UPDATED_ZIPCODE)
            .timezone(UPDATED_TIMEZONE)
            .address(UPDATED_ADDRESS)
            .analyticCategory(UPDATED_ANALYTIC_CATEGORY);
        GeoLocationInformationDTO geoLocationInformationDTO = geoLocationInformationMapper.toDto(updatedGeoLocationInformation);

        restGeoLocationInformationMockMvc.perform(put("/api/geo-location-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoLocationInformationDTO)))
            .andExpect(status().isOk());

        // Validate the GeoLocationInformation in the database
        List<GeoLocationInformation> geoLocationInformationList = geoLocationInformationRepository.findAll();
        assertThat(geoLocationInformationList).hasSize(databaseSizeBeforeUpdate);
        GeoLocationInformation testGeoLocationInformation = geoLocationInformationList.get(geoLocationInformationList.size() - 1);
        assertThat(testGeoLocationInformation.getLocationUuid()).isEqualTo(UPDATED_LOCATION_UUID);
        assertThat(testGeoLocationInformation.getLocationType()).isEqualTo(UPDATED_LOCATION_TYPE);
        assertThat(testGeoLocationInformation.getParentLocationUuid()).isEqualTo(UPDATED_PARENT_LOCATION_UUID);
        assertThat(testGeoLocationInformation.getCoordinatesType()).isEqualTo(UPDATED_COORDINATES_TYPE);
        assertThat(testGeoLocationInformation.getCoordinates()).isEqualTo(UPDATED_COORDINATES);
        assertThat(testGeoLocationInformation.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testGeoLocationInformation.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testGeoLocationInformation.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testGeoLocationInformation.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testGeoLocationInformation.getTimezone()).isEqualTo(UPDATED_TIMEZONE);
        assertThat(testGeoLocationInformation.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testGeoLocationInformation.getAnalyticCategory()).isEqualTo(UPDATED_ANALYTIC_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingGeoLocationInformation() throws Exception {
        int databaseSizeBeforeUpdate = geoLocationInformationRepository.findAll().size();

        // Create the GeoLocationInformation
        GeoLocationInformationDTO geoLocationInformationDTO = geoLocationInformationMapper.toDto(geoLocationInformation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeoLocationInformationMockMvc.perform(put("/api/geo-location-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoLocationInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the GeoLocationInformation in the database
        List<GeoLocationInformation> geoLocationInformationList = geoLocationInformationRepository.findAll();
        assertThat(geoLocationInformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGeoLocationInformation() throws Exception {
        // Initialize the database
        geoLocationInformationRepository.saveAndFlush(geoLocationInformation);
        int databaseSizeBeforeDelete = geoLocationInformationRepository.findAll().size();

        // Get the geoLocationInformation
        restGeoLocationInformationMockMvc.perform(delete("/api/geo-location-informations/{id}", geoLocationInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeoLocationInformation> geoLocationInformationList = geoLocationInformationRepository.findAll();
        assertThat(geoLocationInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeoLocationInformation.class);
        GeoLocationInformation geoLocationInformation1 = new GeoLocationInformation();
        geoLocationInformation1.setId(1L);
        GeoLocationInformation geoLocationInformation2 = new GeoLocationInformation();
        geoLocationInformation2.setId(geoLocationInformation1.getId());
        assertThat(geoLocationInformation1).isEqualTo(geoLocationInformation2);
        geoLocationInformation2.setId(2L);
        assertThat(geoLocationInformation1).isNotEqualTo(geoLocationInformation2);
        geoLocationInformation1.setId(null);
        assertThat(geoLocationInformation1).isNotEqualTo(geoLocationInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeoLocationInformationDTO.class);
        GeoLocationInformationDTO geoLocationInformationDTO1 = new GeoLocationInformationDTO();
        geoLocationInformationDTO1.setId(1L);
        GeoLocationInformationDTO geoLocationInformationDTO2 = new GeoLocationInformationDTO();
        assertThat(geoLocationInformationDTO1).isNotEqualTo(geoLocationInformationDTO2);
        geoLocationInformationDTO2.setId(geoLocationInformationDTO1.getId());
        assertThat(geoLocationInformationDTO1).isEqualTo(geoLocationInformationDTO2);
        geoLocationInformationDTO2.setId(2L);
        assertThat(geoLocationInformationDTO1).isNotEqualTo(geoLocationInformationDTO2);
        geoLocationInformationDTO1.setId(null);
        assertThat(geoLocationInformationDTO1).isNotEqualTo(geoLocationInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(geoLocationInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(geoLocationInformationMapper.fromId(null)).isNull();
    }
}
