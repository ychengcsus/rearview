package edu.four04.sscapp.web.rest;

import edu.four04.sscapp.SscappApp;

import edu.four04.sscapp.domain.TrafficMeasurements;
import edu.four04.sscapp.repository.TrafficMeasurementsRepository;
import edu.four04.sscapp.web.rest.errors.ExceptionTranslator;

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

import static edu.four04.sscapp.web.rest.TestUtil.sameInstant;
import static edu.four04.sscapp.web.rest.TestUtil.createFormattingConversionService;
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
@SpringBootTest(classes = SscappApp.class)
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

    private static final Integer DEFAULT_COUNTER_DIRECTION = 1;
    private static final Integer UPDATED_COUNTER_DIRECTION = 2;

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
        final TrafficMeasurementsResource trafficMeasurementsResource = new TrafficMeasurementsResource(trafficMeasurementsRepository);
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
            .end_time(DEFAULT_END_TIME)
            .asset_uuid(DEFAULT_ASSET_UUID)
            .asset_description(DEFAULT_ASSET_DESCRIPTION)
            .event_type(DEFAULT_EVENT_TYPE)
            .counter_direction(DEFAULT_COUNTER_DIRECTION)
            .counter_direction_speed(DEFAULT_COUNTER_DIRECTION_SPEED)
            .counter_direction_vehicle_count(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT)
            .speed(DEFAULT_SPEED)
            .vehicle_count(DEFAULT_VEHICLE_COUNT)
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
        restTrafficMeasurementsMockMvc.perform(post("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurements)))
            .andExpect(status().isCreated());

        // Validate the TrafficMeasurements in the database
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeCreate + 1);
        TrafficMeasurements testTrafficMeasurements = trafficMeasurementsList.get(trafficMeasurementsList.size() - 1);
        assertThat(testTrafficMeasurements.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTrafficMeasurements.getEnd_time()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTrafficMeasurements.getAsset_uuid()).isEqualTo(DEFAULT_ASSET_UUID);
        assertThat(testTrafficMeasurements.getAsset_description()).isEqualTo(DEFAULT_ASSET_DESCRIPTION);
        assertThat(testTrafficMeasurements.getEvent_type()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testTrafficMeasurements.getCounter_direction()).isEqualTo(DEFAULT_COUNTER_DIRECTION);
        assertThat(testTrafficMeasurements.getCounter_direction_speed()).isEqualTo(DEFAULT_COUNTER_DIRECTION_SPEED);
        assertThat(testTrafficMeasurements.getCounter_direction_vehicle_count()).isEqualTo(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getSpeed()).isEqualTo(DEFAULT_SPEED);
        assertThat(testTrafficMeasurements.getVehicle_count()).isEqualTo(DEFAULT_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    @Transactional
    public void createTrafficMeasurementsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trafficMeasurementsRepository.findAll().size();

        // Create the TrafficMeasurements with an existing ID
        trafficMeasurements.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrafficMeasurementsMockMvc.perform(post("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurements)))
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
            .andExpect(jsonPath("$.[*].end_time").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].asset_uuid").value(hasItem(DEFAULT_ASSET_UUID.intValue())))
            .andExpect(jsonPath("$.[*].asset_description").value(hasItem(DEFAULT_ASSET_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].event_type").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].counter_direction").value(hasItem(DEFAULT_COUNTER_DIRECTION)))
            .andExpect(jsonPath("$.[*].counter_direction_speed").value(hasItem(DEFAULT_COUNTER_DIRECTION_SPEED.intValue())))
            .andExpect(jsonPath("$.[*].counter_direction_vehicle_count").value(hasItem(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].speed").value(hasItem(DEFAULT_SPEED.intValue())))
            .andExpect(jsonPath("$.[*].vehicle_count").value(hasItem(DEFAULT_VEHICLE_COUNT.intValue())))
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
            .andExpect(jsonPath("$.end_time").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.asset_uuid").value(DEFAULT_ASSET_UUID.intValue()))
            .andExpect(jsonPath("$.asset_description").value(DEFAULT_ASSET_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.event_type").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.counter_direction").value(DEFAULT_COUNTER_DIRECTION))
            .andExpect(jsonPath("$.counter_direction_speed").value(DEFAULT_COUNTER_DIRECTION_SPEED.intValue()))
            .andExpect(jsonPath("$.counter_direction_vehicle_count").value(DEFAULT_COUNTER_DIRECTION_VEHICLE_COUNT.intValue()))
            .andExpect(jsonPath("$.speed").value(DEFAULT_SPEED.intValue()))
            .andExpect(jsonPath("$.vehicle_count").value(DEFAULT_VEHICLE_COUNT.intValue()))
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
            .end_time(UPDATED_END_TIME)
            .asset_uuid(UPDATED_ASSET_UUID)
            .asset_description(UPDATED_ASSET_DESCRIPTION)
            .event_type(UPDATED_EVENT_TYPE)
            .counter_direction(UPDATED_COUNTER_DIRECTION)
            .counter_direction_speed(UPDATED_COUNTER_DIRECTION_SPEED)
            .counter_direction_vehicle_count(UPDATED_COUNTER_DIRECTION_VEHICLE_COUNT)
            .speed(UPDATED_SPEED)
            .vehicle_count(UPDATED_VEHICLE_COUNT)
            .timestamp(UPDATED_TIMESTAMP);

        restTrafficMeasurementsMockMvc.perform(put("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrafficMeasurements)))
            .andExpect(status().isOk());

        // Validate the TrafficMeasurements in the database
        List<TrafficMeasurements> trafficMeasurementsList = trafficMeasurementsRepository.findAll();
        assertThat(trafficMeasurementsList).hasSize(databaseSizeBeforeUpdate);
        TrafficMeasurements testTrafficMeasurements = trafficMeasurementsList.get(trafficMeasurementsList.size() - 1);
        assertThat(testTrafficMeasurements.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTrafficMeasurements.getEnd_time()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTrafficMeasurements.getAsset_uuid()).isEqualTo(UPDATED_ASSET_UUID);
        assertThat(testTrafficMeasurements.getAsset_description()).isEqualTo(UPDATED_ASSET_DESCRIPTION);
        assertThat(testTrafficMeasurements.getEvent_type()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTrafficMeasurements.getCounter_direction()).isEqualTo(UPDATED_COUNTER_DIRECTION);
        assertThat(testTrafficMeasurements.getCounter_direction_speed()).isEqualTo(UPDATED_COUNTER_DIRECTION_SPEED);
        assertThat(testTrafficMeasurements.getCounter_direction_vehicle_count()).isEqualTo(UPDATED_COUNTER_DIRECTION_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getSpeed()).isEqualTo(UPDATED_SPEED);
        assertThat(testTrafficMeasurements.getVehicle_count()).isEqualTo(UPDATED_VEHICLE_COUNT);
        assertThat(testTrafficMeasurements.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void updateNonExistingTrafficMeasurements() throws Exception {
        int databaseSizeBeforeUpdate = trafficMeasurementsRepository.findAll().size();

        // Create the TrafficMeasurements

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrafficMeasurementsMockMvc.perform(put("/api/traffic-measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trafficMeasurements)))
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
}
