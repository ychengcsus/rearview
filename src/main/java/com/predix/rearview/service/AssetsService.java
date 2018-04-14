package com.predix.rearview.service;

import com.predix.rearview.service.dto.AssetsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Assets.
 */
public interface AssetsService {

    /**
     * Save a assets.
     *
     * @param assetsDTO the entity to save
     * @return the persisted entity
     */
    AssetsDTO save(AssetsDTO assetsDTO);

    /**
     * Get all the assets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AssetsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" assets.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AssetsDTO findOne(Long id);

    /**
     * Delete the "id" assets.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
