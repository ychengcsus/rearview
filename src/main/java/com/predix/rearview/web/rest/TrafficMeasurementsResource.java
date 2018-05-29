package edu.four04.sscapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.four04.sscapp.domain.TrafficMeasurements;

import edu.four04.sscapp.repository.TrafficMeasurementsRepository;
import edu.four04.sscapp.web.rest.errors.BadRequestAlertException;
import edu.four04.sscapp.web.rest.util.HeaderUtil;
import edu.four04.sscapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TrafficMeasurements.
 */
@RestController
@RequestMapping("/api")
public class TrafficMeasurementsResource {

    private final Logger log = LoggerFactory.getLogger(TrafficMeasurementsResource.class);

    private static final String ENTITY_NAME = "trafficMeasurements";

    private final TrafficMeasurementsRepository trafficMeasurementsRepository;

    public TrafficMeasurementsResource(TrafficMeasurementsRepository trafficMeasurementsRepository) {
        this.trafficMeasurementsRepository = trafficMeasurementsRepository;
    }

    /**
     * POST  /traffic-measurements : Create a new trafficMeasurements.
     *
     * @param trafficMeasurements the trafficMeasurements to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trafficMeasurements, or with status 400 (Bad Request) if the trafficMeasurements has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/traffic-measurements")
    @Timed
    public ResponseEntity<TrafficMeasurements> createTrafficMeasurements(@RequestBody TrafficMeasurements trafficMeasurements) throws URISyntaxException {
        log.debug("REST request to save TrafficMeasurements : {}", trafficMeasurements);
        if (trafficMeasurements.getId() != null) {
            throw new BadRequestAlertException("A new trafficMeasurements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrafficMeasurements result = trafficMeasurementsRepository.save(trafficMeasurements);
        return ResponseEntity.created(new URI("/api/traffic-measurements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /traffic-measurements : Updates an existing trafficMeasurements.
     *
     * @param trafficMeasurements the trafficMeasurements to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trafficMeasurements,
     * or with status 400 (Bad Request) if the trafficMeasurements is not valid,
     * or with status 500 (Internal Server Error) if the trafficMeasurements couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/traffic-measurements")
    @Timed
    public ResponseEntity<TrafficMeasurements> updateTrafficMeasurements(@RequestBody TrafficMeasurements trafficMeasurements) throws URISyntaxException {
        log.debug("REST request to update TrafficMeasurements : {}", trafficMeasurements);
        if (trafficMeasurements.getId() == null) {
            return createTrafficMeasurements(trafficMeasurements);
        }
        TrafficMeasurements result = trafficMeasurementsRepository.save(trafficMeasurements);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trafficMeasurements.getId().toString()))
            .body(result);
    }

    /**
     * GET  /traffic-measurements : get all the trafficMeasurements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of trafficMeasurements in body
     */
    @GetMapping("/traffic-measurements")
    @Timed
    public ResponseEntity<List<TrafficMeasurements>> getAllTrafficMeasurements(Pageable pageable) {
        log.debug("REST request to get a page of TrafficMeasurements");
        Page<TrafficMeasurements> page = trafficMeasurementsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/traffic-measurements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /traffic-measurements/:id : get the "id" trafficMeasurements.
     *
     * @param id the id of the trafficMeasurements to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trafficMeasurements, or with status 404 (Not Found)
     */
    @GetMapping("/traffic-measurements/{id}")
    @Timed
    public ResponseEntity<TrafficMeasurements> getTrafficMeasurements(@PathVariable Long id) {
        log.debug("REST request to get TrafficMeasurements : {}", id);
        TrafficMeasurements trafficMeasurements = trafficMeasurementsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trafficMeasurements));
    }

    /**
     * DELETE  /traffic-measurements/:id : delete the "id" trafficMeasurements.
     *
     * @param id the id of the trafficMeasurements to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/traffic-measurements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrafficMeasurements(@PathVariable Long id) {
        log.debug("REST request to delete TrafficMeasurements : {}", id);
        trafficMeasurementsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
