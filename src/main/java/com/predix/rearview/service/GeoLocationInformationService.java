package com.predix.rearview.service;

import com.predix.rearview.service.dto.GeoLocationInformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GeoLocationInformation.
 */
public interface GeoLocationInformationService {

    /**
     * Save a geoLocationInformation.
     *
     * @param geoLocationInformationDTO the entity to save
     * @return the persisted entity
     */
    GeoLocationInformationDTO save(GeoLocationInformationDTO geoLocationInformationDTO);

    /**
     * Get all the geoLocationInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GeoLocationInformationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" geoLocationInformation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GeoLocationInformationDTO findOne(Long id);

    /**
     * Delete the "id" geoLocationInformation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
