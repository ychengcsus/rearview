package com.predix.rearview.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.rearview.service.GeoLocationInformationService;
import com.predix.rearview.web.rest.errors.BadRequestAlertException;
import com.predix.rearview.web.rest.util.HeaderUtil;
import com.predix.rearview.web.rest.util.PaginationUtil;
import com.predix.rearview.service.dto.GeoLocationInformationDTO;
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
 * REST controller for managing GeoLocationInformation.
 */
@RestController
@RequestMapping("/api")
public class GeoLocationInformationResource {

    private final Logger log = LoggerFactory.getLogger(GeoLocationInformationResource.class);

    private static final String ENTITY_NAME = "geoLocationInformation";

    private final GeoLocationInformationService geoLocationInformationService;

    public GeoLocationInformationResource(GeoLocationInformationService geoLocationInformationService) {
        this.geoLocationInformationService = geoLocationInformationService;
    }

    /**
     * POST  /geo-location-informations : Create a new geoLocationInformation.
     *
     * @param geoLocationInformationDTO the geoLocationInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geoLocationInformationDTO, or with status 400 (Bad Request) if the geoLocationInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/geo-location-informations")
    @Timed
    public ResponseEntity<GeoLocationInformationDTO> createGeoLocationInformation(@RequestBody GeoLocationInformationDTO geoLocationInformationDTO) throws URISyntaxException {
        log.debug("REST request to save GeoLocationInformation : {}", geoLocationInformationDTO);
        if (geoLocationInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new geoLocationInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeoLocationInformationDTO result = geoLocationInformationService.save(geoLocationInformationDTO);
        return ResponseEntity.created(new URI("/api/geo-location-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /geo-location-informations : Updates an existing geoLocationInformation.
     *
     * @param geoLocationInformationDTO the geoLocationInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geoLocationInformationDTO,
     * or with status 400 (Bad Request) if the geoLocationInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the geoLocationInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/geo-location-informations")
    @Timed
    public ResponseEntity<GeoLocationInformationDTO> updateGeoLocationInformation(@RequestBody GeoLocationInformationDTO geoLocationInformationDTO) throws URISyntaxException {
        log.debug("REST request to update GeoLocationInformation : {}", geoLocationInformationDTO);
        if (geoLocationInformationDTO.getId() == null) {
            return createGeoLocationInformation(geoLocationInformationDTO);
        }
        GeoLocationInformationDTO result = geoLocationInformationService.save(geoLocationInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, geoLocationInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /geo-location-informations : get all the geoLocationInformations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of geoLocationInformations in body
     */
    @GetMapping("/geo-location-informations")
    @Timed
    public ResponseEntity<List<GeoLocationInformationDTO>> getAllGeoLocationInformations(Pageable pageable) {
        log.debug("REST request to get a page of GeoLocationInformations");
        Page<GeoLocationInformationDTO> page = geoLocationInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/geo-location-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /geo-location-informations/:id : get the "id" geoLocationInformation.
     *
     * @param id the id of the geoLocationInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geoLocationInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/geo-location-informations/{id}")
    @Timed
    public ResponseEntity<GeoLocationInformationDTO> getGeoLocationInformation(@PathVariable Long id) {
        log.debug("REST request to get GeoLocationInformation : {}", id);
        GeoLocationInformationDTO geoLocationInformationDTO = geoLocationInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(geoLocationInformationDTO));
    }

    /**
     * DELETE  /geo-location-informations/:id : delete the "id" geoLocationInformation.
     *
     * @param id the id of the geoLocationInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/geo-location-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeoLocationInformation(@PathVariable Long id) {
        log.debug("REST request to delete GeoLocationInformation : {}", id);
        geoLocationInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
