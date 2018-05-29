package edu.four04.sscapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.four04.sscapp.domain.GeoLocationInformation;

import edu.four04.sscapp.repository.GeoLocationInformationRepository;
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
 * REST controller for managing GeoLocationInformation.
 */
@RestController
@RequestMapping("/api")
public class GeoLocationInformationResource {

    private final Logger log = LoggerFactory.getLogger(GeoLocationInformationResource.class);

    private static final String ENTITY_NAME = "geoLocationInformation";

    private final GeoLocationInformationRepository geoLocationInformationRepository;

    public GeoLocationInformationResource(GeoLocationInformationRepository geoLocationInformationRepository) {
        this.geoLocationInformationRepository = geoLocationInformationRepository;
    }

    /**
     * POST  /geo-location-informations : Create a new geoLocationInformation.
     *
     * @param geoLocationInformation the geoLocationInformation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geoLocationInformation, or with status 400 (Bad Request) if the geoLocationInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/geo-location-informations")
    @Timed
    public ResponseEntity<GeoLocationInformation> createGeoLocationInformation(@RequestBody GeoLocationInformation geoLocationInformation) throws URISyntaxException {
        log.debug("REST request to save GeoLocationInformation : {}", geoLocationInformation);
        if (geoLocationInformation.getId() != null) {
            throw new BadRequestAlertException("A new geoLocationInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeoLocationInformation result = geoLocationInformationRepository.save(geoLocationInformation);
        return ResponseEntity.created(new URI("/api/geo-location-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /geo-location-informations : Updates an existing geoLocationInformation.
     *
     * @param geoLocationInformation the geoLocationInformation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geoLocationInformation,
     * or with status 400 (Bad Request) if the geoLocationInformation is not valid,
     * or with status 500 (Internal Server Error) if the geoLocationInformation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/geo-location-informations")
    @Timed
    public ResponseEntity<GeoLocationInformation> updateGeoLocationInformation(@RequestBody GeoLocationInformation geoLocationInformation) throws URISyntaxException {
        log.debug("REST request to update GeoLocationInformation : {}", geoLocationInformation);
        if (geoLocationInformation.getId() == null) {
            return createGeoLocationInformation(geoLocationInformation);
        }
        GeoLocationInformation result = geoLocationInformationRepository.save(geoLocationInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, geoLocationInformation.getId().toString()))
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
    public ResponseEntity<List<GeoLocationInformation>> getAllGeoLocationInformations(Pageable pageable) {
        log.debug("REST request to get a page of GeoLocationInformations");
        Page<GeoLocationInformation> page = geoLocationInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/geo-location-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /geo-location-informations/:id : get the "id" geoLocationInformation.
     *
     * @param id the id of the geoLocationInformation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geoLocationInformation, or with status 404 (Not Found)
     */
    @GetMapping("/geo-location-informations/{id}")
    @Timed
    public ResponseEntity<GeoLocationInformation> getGeoLocationInformation(@PathVariable Long id) {
        log.debug("REST request to get GeoLocationInformation : {}", id);
        GeoLocationInformation geoLocationInformation = geoLocationInformationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(geoLocationInformation));
    }

    /**
     * DELETE  /geo-location-informations/:id : delete the "id" geoLocationInformation.
     *
     * @param id the id of the geoLocationInformation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/geo-location-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeoLocationInformation(@PathVariable Long id) {
        log.debug("REST request to delete GeoLocationInformation : {}", id);
        geoLocationInformationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
