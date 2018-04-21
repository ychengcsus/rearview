package com.predix.rearview.web.rest;

import com.predix.rearview.RearviewFinal1App;

import com.predix.rearview.domain.TrafficMeasurements;
import com.predix.rearview.repository.TrafficMeasurementsRepository;
import com.predix.rearview.service.TrafficMeasurementsService;
import com.predix.rearview.service.dto.TrafficMeasurementsDTO;
import com.predix.rearview.service.mapper.TrafficMeasurementsMapper;
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
 * Test class for the TrafficMeasurementsResource REST controller.
 *
 * @see TrafficMeasurementsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RearviewFinal1App.class)
public class TrafficMeasurementsResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_ASSET_UUID = 1L;
    private static final Long UPDATED_ASSET_UUID = 2L;

    private static final String DEFAULT_ASSET_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_COUNTER_DIRECTION = 1L;
    private static final Long UPDATED_COUNTER_DIRECTION = 2L;

    private static final Long DEFAULT_COUNTER_DIRECTION_SPEED = 1L;
    private static final Long UPDATED_COUNTER_DIRECTION_SPEED = 2L;

    private static final Long DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT = 1L;
    private static final Long UPDATED_COUNTER_DIRECTION_VEHICLE_COUNT = 2L;

    private static final Long DEFAULT_SPEED = 1L;
    private static final Long UPDATED_SPEED = 2L;

    private static final Long DEFAULT_VEHICLE_COUNT = 1L;
    private static final Long UPDATED_VEHICLE_COUNT = 2L;

    private static final ZonedDateTime DEFAULT_TIMESTAMP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIMESTAMP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TrafficMeasurementsRepository trafficMeasurementsRepository;

    @Autowired
    private TrafficMeasurementsMapper trafficMeasurementsMapper;

    @Autowired
    private TrafficMeasurementsService trafficMeasurementsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrafficMeasurementsMockMvc;

    private TrafficMeasurements trafficMeasurements;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrafficMeasurementsResource trafficMeasurementsResource = new TrafficMeasurementsResource(trafficMeasurementsService);
        this.restTrafficMeasurementsMockMvc = MockMvcBuilders.standaloneSetup(trafficMeasurementsResource)
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
    public static TrafficMeasurements createEntity(EntityManager em) {
        TrafficMeasurements trafficMeasurements = new TrafficMeasurements()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .assetUuid(DEFAULT_ASSET_UUID)
            .assetDescription(DEFAULT_ASSET_DESCRIPTION)
            .eventType(DEFAULT_EVENT_TYPE)
            .counterDirection(DEFAULT_COUNTER_DIRECTION)
            .counterDirectionSpeed(DEFAULT_COUNTER_DIRECTION_SPEED)
            .counterDirectionVehicleCount(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT)
            .speed(DEFAULT_SPEED)
            .vehicleCount(DEFAULT_VEHICLE_COUNT)
            .timestamp(DEFAULT_TIMESTAMP);
        return trafficMeasurements;
    }

    @Before
    public void initTest() {
        trafficMeasurements = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrafficMeasurements() throws Exception {
        int databaseSizeBeforeCreate = trafficMeasurementsRepository.findAll().size();

        // Create the TrafficMeasurements
        TrafficMeasurementsDTO trafficMeasurementsDTO = trafficMeasurementsMapper.toDto(trafficMeasurements);
        restTrafficMeasurementsMockMvc.perform(post("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurementsDTO)))
            .andExpect(status().isCreated());

        // Validate the TrafficMeasurements in the database
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeCreate + 1);
        TrafficMeasurements testTrafficMeasurements = trafficMeasurementsList.get(trafficMeasurementsList.size() - 1);
        assertThat(testTrafficMeasurements.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTrafficMeasurements.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTrafficMeasurements.getAssetUuid()).isEqualTo(DEFAULT_ASSET_UUID);
        assertThat(testTrafficMeasurements.getAssetDescription()).isEqualTo(DEFAULT_ASSET_DESCRIPTION);
        assertThat(testTrafficMeasurements.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testTrafficMeasurements.getCounterDirection()).isEqualTo(DEFAULT_COUNTER_DIRECTION);
        assertThat(testTrafficMeasurements.getCounterDirectionSpeed()).isEqualTo(DEFAULT_COUNTER_DIRECTION_SPEED);
        assertThat(testTrafficMeasurements.getCounterDirectionVehicleCount()).isEqualTo(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testTrafficMeasurements.getVehicleCount()).isEqualTo(DEFAULT_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createTrafficMeasurementsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trafficMeasurementsRepository.findAll().size();

        // Create the TrafficMeasurements with an existing ID
        trafficMeasurements.setId(1L);
        TrafficMeasurementsDTO trafficMeasurementsDTO = trafficMeasurementsMapper.toDto(trafficMeasurements);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrafficMeasurementsMockMvc.perform(post("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurementsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrafficMeasurements in the database
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrafficMeasurements() throws Exception {
        // Initialize the database
        trafficMeasurementsRepository.saveAndFlush(trafficMeasurements);

        // Get all the trafficMeasurementsList
        restTrafficMeasurementsMockMvc.perform(get("/api/traffic-measurements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trafficMeasurements.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].assetUuid").value(hasItem(DEFAULT_ASSET_UUID.intValue())))
            .andExpect(jsonPath("$.[*].assetDescription").value(hasItem(DEFAULT_ASSET_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].counterDirection").value(hasItem(DEFAULT_COUNTER_DIRECTION.intValue())))
            .andExpect(jsonPath("$.[*].counterDirectionSpeed").value(hasItem(DEFAULT_COUNTER_DIRECTION_SPEED.intValue())))
            .andExpect(jsonPath("$.[*].counterDirectionVehicleCount").value(hasItem(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.intValue())))
            .andExpect(jsonPath("$.[*].vehicleCount").value(hasItem(DEFAULT_VEHICLE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(sameInstant(DEFAULT_TIMESTAMP))));
    }

    @Test
    @Transactional
    public void getTrafficMeasurements() throws Exception {
        // Initialize the database
        trafficMeasurementsRepository.saveAndFlush(trafficMeasurements);

        // Get the trafficMeasurements
        restTrafficMeasurementsMockMvc.perform(get("/api/traffic-measurements/{id}", trafficMeasurements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trafficMeasurements.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.assetUuid").value(DEFAULT_ASSET_UUID.intValue()))
            .andExpect(jsonPath("$.assetDescription").value(DEFAULT_ASSET_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.counterDirection").value(DEFAULT_COUNTER_DIRECTION.intValue()))
            .andExpect(jsonPath("$.counterDirectionSpeed").value(DEFAULT_COUNTER_DIRECTION_SPEED.intValue()))
            .andExpect(jsonPath("$.counterDirectionVehicleCount").value(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT.intValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.intValue()))
            .andExpect(jsonPath("$.vehicleCount").value(DEFAULT_VEHICLE_COUNT.intValue()))
            .andExpect(jsonPath("$.timestamp").value(sameInstant(DEFAULT_TIMESTAMP)));
    }

    @Test
    @Transactional
    public void getNonExistingTrafficMeasurements() throws Exception {
        // Get the trafficMeasurements
        restTrafficMeasurementsMockMvc.perform(get("/api/traffic-measurements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrafficMeasurements() throws Exception {
        // Initialize the database
        trafficMeasurementsRepository.saveAndFlush(trafficMeasurements);
        int databaseSizeBeforeUpdate = trafficMeasurementsRepository.findAll().size();

        // Update the trafficMeasurements
        TrafficMeasurements updatedTrafficMeasurements = trafficMeasurementsRepository.findOne(trafficMeasurements.getId());
        // Disconnect from session so that the updates on updatedTrafficMeasurements are not directly saved in db
        em.detach(updatedTrafficMeasurements);
        updatedTrafficMeasurements
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .assetUuid(UPDATED_ASSET_UUID)
            .assetDescription(UPDATED_ASSET_DESCRIPTION)
            .eventType(UPDATED_EVENT_TYPE)
            .counterDirection(UPDATED_COUNTER_DIRECTION)
            .counterDirectionSpeed(UPDATED_COUNTER_DIRECTION_SPEED)
            .counterDirectionVehicleCount(UPDATED_COUNTER_DIRECTION_VEHICLE_COUNT)
            .speed(UPDATED_SPEED)
            .vehicleCount(UPDATED_VEHICLE_COUNT)
            .timestamp(UPDATED_TIMESTAMP);
        TrafficMeasurementsDTO trafficMeasurementsDTO = trafficMeasurementsMapper.toDto(updatedTrafficMeasurements);

        restTrafficMeasurementsMockMvc.perform(put("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurementsDTO)))
            .andExpect(status().isOk());

        // Validate the TrafficMeasurements in the database
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeUpdate);
        TrafficMeasurements testTrafficMeasurements = trafficMeasurementsList.get(trafficMeasurementsList.size() - 1);
        assertThat(testTrafficMeasurements.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTrafficMeasurements.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTrafficMeasurements.getAssetUuid()).isEqualTo(UPDATED_ASSET_UUID);
        assertThat(testTrafficMeasurements.getAssetDescription()).isEqualTo(UPDATED_ASSET_DESCRIPTION);
        assertThat(testTrafficMeasurements.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTrafficMeasurements.getCounterDirection()).isEqualTo(UPDATED_COUNTER_DIRECTION);
        assertThat(testTrafficMeasurements.getCounterDirectionSpeed()).isEqualTo(UPDATED_COUNTER_DIRECTION_SPEED);
        assertThat(testTrafficMeasurements.getCounterDirectionVehicleCount()).isEqualTo(UPDATED_COUNTER_DIRECTION_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testTrafficMeasurements.getVehicleCount()).isEqualTo(UPDATED_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingTrafficMeasurements() throws Exception {
        int databaseSizeBeforeUpdate = trafficMeasurementsRepository.findAll().size();

        // Create the TrafficMeasurements
        TrafficMeasurementsDTO trafficMeasurementsDTO = trafficMeasurementsMapper.toDto(trafficMeasurements);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrafficMeasurementsMockMvc.perform(put("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurementsDTO)))
            .andExpect(status().isCreated());

        // Validate the TrafficMeasurements in the database
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrafficMeasurements() throws Exception {
        // Initialize the database
        trafficMeasurementsRepository.saveAndFlush(trafficMeasurements);
        int databaseSizeBeforeDelete = trafficMeasurementsRepository.findAll().size();

        // Get the trafficMeasurements
        restTrafficMeasurementsMockMvc.perform(delete("/api/traffic-measurements/{id}", trafficMeasurements.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrafficMeasurements.class);
        TrafficMeasurements trafficMeasurements1 = new TrafficMeasurements();
        trafficMeasurements1.setId(1L);
        TrafficMeasurements trafficMeasurements2 = new TrafficMeasurements();
        trafficMeasurements2.setId(trafficMeasurements1.getId());
        assertThat(trafficMeasurements1).isEqualTo(trafficMeasurements2);
        trafficMeasurements2.setId(2L);
        assertThat(trafficMeasurements1).isNotEqualTo(trafficMeasurements2);
        trafficMeasurements1.setId(null);
        assertThat(trafficMeasurements1).isNotEqualTo(trafficMeasurements2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrafficMeasurementsDTO.class);
        TrafficMeasurementsDTO trafficMeasurementsDTO1 = new TrafficMeasurementsDTO();
        trafficMeasurementsDTO1.setId(1L);
        TrafficMeasurementsDTO trafficMeasurementsDTO2 = new TrafficMeasurementsDTO();
        assertThat(trafficMeasurementsDTO1).isNotEqualTo(trafficMeasurementsDTO2);
        trafficMeasurementsDTO2.setId(trafficMeasurementsDTO1.getId());
        assertThat(trafficMeasurementsDTO1).isEqualTo(trafficMeasurementsDTO2);
        trafficMeasurementsDTO2.setId(2L);
        assertThat(trafficMeasurementsDTO1).isNotEqualTo(trafficMeasurementsDTO2);
        trafficMeasurementsDTO1.setId(null);
        assertThat(trafficMeasurementsDTO1).isNotEqualTo(trafficMeasurementsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trafficMeasurementsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trafficMeasurementsMapper.fromId(null)).isNull();
    }
}
