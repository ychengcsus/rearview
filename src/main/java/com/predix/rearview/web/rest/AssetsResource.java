package edu.four04.sscapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import edu.four04.sscapp.domain.Assets;

import edu.four04.sscapp.repository.AssetsRepository;
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
 * REST controller for managing Assets.
 */
@RestController
@RequestMapping("/api")
public class AssetsResource {

    private final Logger log = LoggerFactory.getLogger(AssetsResource.class);

    private static final String ENTITY_NAME = "assets";

    private final AssetsRepository assetsRepository;

    public AssetsResource(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    /**
     * POST  /assets : Create a new assets.
     *
     * @param assets the assets to create
     * @return the ResponseEntity with status 201 (Created) and with body the new assets, or with status 400 (Bad Request) if the assets has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/assets")
    @Timed
    public ResponseEntity<Assets> createAssets(@RequestBody Assets assets) throws URISyntaxException {
        log.debug("REST request to save Assets : {}", assets);
        if (assets.getId() != null) {
            throw new BadRequestAlertException("A new assets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Assets result = assetsRepository.save(assets);
        return ResponseEntity.created(new URI("/api/assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /assets : Updates an existing assets.
     *
     * @param assets the assets to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated assets,
     * or with status 400 (Bad Request) if the assets is not valid,
     * or with status 500 (Internal Server Error) if the assets couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/assets")
    @Timed
    public ResponseEntity<Assets> updateAssets(@RequestBody Assets assets) throws URISyntaxException {
        log.debug("REST request to update Assets : {}", assets);
        if (assets.getId() == null) {
            return createAssets(assets);
        }
        Assets result = assetsRepository.save(assets);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, assets.getId().toString()))
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
    public ResponseEntity<List<Assets>> getAllAssets(Pageable pageable) {
        log.debug("REST request to get a page of Assets");
        Page<Assets> page = assetsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/assets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /assets/:id : get the "id" assets.
     *
     * @param id the id of the assets to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the assets, or with status 404 (Not Found)
     */
    @GetMapping("/assets/{id}")
    @Timed
    public ResponseEntity<Assets> getAssets(@PathVariable Long id) {
        log.debug("REST request to get Assets : {}", id);
        Assets assets = assetsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(assets));
    }

    /**
     * DELETE  /assets/:id : delete the "id" assets.
     *
     * @param id the id of the assets to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/assets/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssets(@PathVariable Long id) {
        log.debug("REST request to delete Assets : {}", id);
        assetsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
