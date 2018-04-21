package com.predix.rearview.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.rearview.service.TrafficMeasurementsService;
import com.predix.rearview.web.rest.errors.BadRequestAlertException;
import com.predix.rearview.web.rest.util.HeaderUtil;
import com.predix.rearview.web.rest.util.PaginationUtil;
import com.predix.rearview.service.dto.TrafficMeasurementsDTO;
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

    private final TrafficMeasurementsService trafficMeasurementsService;

    public TrafficMeasurementsResource(TrafficMeasurementsService trafficMeasurementsService) {
        this.trafficMeasurementsService = trafficMeasurementsService;
    }

    /**
     * POST  /traffic-measurements : Create a new trafficMeasurements.
     *
     * @param trafficMeasurementsDTO the trafficMeasurementsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trafficMeasurementsDTO, or with status 400 (Bad Request) if the trafficMeasurements has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/traffic-measurements")
    @Timed
    public ResponseEntity<TrafficMeasurementsDTO> createTrafficMeasurements(@RequestBody TrafficMeasurementsDTO trafficMeasurementsDTO) throws URISyntaxException {
        log.debug("REST request to save TrafficMeasurements : {}", trafficMeasurementsDTO);
        if (trafficMeasurementsDTO.getId() != null) {
            throw new BadRequestAlertException("A new trafficMeasurements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrafficMeasurementsDTO result = trafficMeasurementsService.save(trafficMeasurementsDTO);
        return ResponseEntity.created(new URI("/api/traffic-measurements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /traffic-measurements : Updates an existing trafficMeasurements.
     *
     * @param trafficMeasurementsDTO the trafficMeasurementsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trafficMeasurementsDTO,
     * or with status 400 (Bad Request) if the trafficMeasurementsDTO is not valid,
     * or with status 500 (Internal Server Error) if the trafficMeasurementsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/traffic-measurements")
    @Timed
    public ResponseEntity<TrafficMeasurementsDTO> updateTrafficMeasurements(@RequestBody TrafficMeasurementsDTO trafficMeasurementsDTO) throws URISyntaxException {
        log.debug("REST request to update TrafficMeasurements : {}", trafficMeasurementsDTO);
        if (trafficMeasurementsDTO.getId() == null) {
            return createTrafficMeasurements(trafficMeasurementsDTO);
        }
        TrafficMeasurementsDTO result = trafficMeasurementsService.save(trafficMeasurementsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trafficMeasurementsDTO.getId().toString()))
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
    public ResponseEntity<List<TrafficMeasurementsDTO>> getAllTrafficMeasurements(Pageable pageable) {
        log.debug("REST request to get a page of TrafficMeasurements");
        Page<TrafficMeasurementsDTO> page = trafficMeasurementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/traffic-measurements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /traffic-measurements/:id : get the "id" trafficMeasurements.
     *
     * @param id the id of the trafficMeasurementsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trafficMeasurementsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/traffic-measurements/{id}")
    @Timed
    public ResponseEntity<TrafficMeasurementsDTO> getTrafficMeasurements(@PathVariable Long id) {
        log.debug("REST request to get TrafficMeasurements : {}", id);
        TrafficMeasurementsDTO trafficMeasurementsDTO = trafficMeasurementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trafficMeasurementsDTO));
    }

    /**
     * DELETE  /traffic-measurements/:id : delete the "id" trafficMeasurements.
     *
     * @param id the id of the trafficMeasurementsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/traffic-measurements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrafficMeasurements(@PathVariable Long id) {
        log.debug("REST request to delete TrafficMeasurements : {}", id);
        trafficMeasurementsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
