package com.predix.rearview.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.predix.rearview.service.AssetsService;
import com.predix.rearview.web.rest.errors.BadRequestAlertException;
import com.predix.rearview.web.rest.util.HeaderUtil;
import com.predix.rearview.web.rest.util.PaginationUtil;
import com.predix.rearview.service.dto.AssetsDTO;
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
 * REST controller for managing Assets.
 */
@RestController
@RequestMapping("/api")
public class AssetsResource {

    private final Logger log = LoggerFactory.getLogger(AssetsResource.class);

    private static final String ENTITY_NAME = "assets";

    private final AssetsService assetsService;

    public AssetsResource(AssetsService assetsService) {
        this.assetsService = assetsService;
    }

    /**
     * POST  /assets : Create a new assets.
     *
     * @param assetsDTO the assetsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assetsDTO, or with status 400 (Bad Request) if the assets has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assets")
    @Timed
    public ResponseEntity<AssetsDTO> createAssets(@RequestBody AssetsDTO assetsDTO) throws URISyntaxException {
        log.debug("REST request to save Assets : {}", assetsDTO);
        if (assetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new assets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetsDTO result = assetsService.save(assetsDTO);
        return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assets : Updates an existing assets.
     *
     * @param assetsDTO the assetsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assetsDTO,
     * or with status 400 (Bad Request) if the assetsDTO is not valid,
     * or with status 500 (Internal Server Error) if the assetsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assets")
    @Timed
    public ResponseEntity<AssetsDTO> updateAssets(@RequestBody AssetsDTO assetsDTO) throws URISyntaxException {
        log.debug("REST request to update Assets : {}", assetsDTO);
        if (assetsDTO.getId() == null) {
            return createAssets(assetsDTO);
        }
        AssetsDTO result = assetsService.save(assetsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /assets : get all the assets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of assets in body
     */
    @GetMapping("/assets")
    @Timed
    public ResponseEntity<List<AssetsDTO>> getAllAssets(Pageable pageable) {
        log.debug("REST request to get a page of Assets");
        Page<AssetsDTO> page = assetsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assets/:id : get the "id" assets.
     *
     * @param id the id of the assetsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assetsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/assets/{id}")
    @Timed
    public ResponseEntity<AssetsDTO> getAssets(@PathVariable Long id) {
        log.debug("REST request to get Assets : {}", id);
        AssetsDTO assetsDTO = assetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assetsDTO));
    }

    /**
     * DELETE  /assets/:id : delete the "id" assets.
     *
     * @param id the id of the assetsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssets(@PathVariable Long id) {
        log.debug("REST request to delete Assets : {}", id);
        assetsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
