package com.predix.rearview.service;

import com.predix.rearview.domain.GeoLocationInformation;
import com.predix.rearview.repository.GeoLocationInformationRepository;
import com.predix.rearview.service.dto.GeoLocationInformationDTO;
import com.predix.rearview.service.mapper.GeoLocationInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing GeoLocationInformation.
 */
@Service
@Transactional
public class GeoLocationInformationService {

    private final Logger log = LoggerFactory.getLogger(GeoLocationInformationService.class);

    private final GeoLocationInformationRepository geoLocationInformationRepository;

    private final GeoLocationInformationMapper geoLocationInformationMapper;

    public GeoLocationInformationService(GeoLocationInformationRepository geoLocationInformationRepository, GeoLocationInformationMapper geoLocationInformationMapper) {
        this.geoLocationInformationRepository = geoLocationInformationRepository;
        this.geoLocationInformationMapper = geoLocationInformationMapper;
    }

    /**
     * Save a geoLocationInformation.
     *
     * @param geoLocationInformationDTO the entity to save
     * @return the persisted entity
     */
    public GeoLocationInformationDTO save(GeoLocationInformationDTO geoLocationInformationDTO) {
        log.debug("Request to save GeoLocationInformation : {}", geoLocationInformationDTO);
        GeoLocationInformation geoLocationInformation = geoLocationInformationMapper.toEntity(geoLocationInformationDTO);
        geoLocationInformation = geoLocationInformationRepository.save(geoLocationInformation);
        return geoLocationInformationMapper.toDto(geoLocationInformation);
    }

    /**
     * Get all the geoLocationInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GeoLocationInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GeoLocationInformations");
        return geoLocationInformationRepository.findAll(pageable)
            .map(geoLocationInformationMapper::toDto);
    }

    /**
     * Get one geoLocationInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GeoLocationInformationDTO findOne(Long id) {
        log.debug("Request to get GeoLocationInformation : {}", id);
        GeoLocationInformation geoLocationInformation = geoLocationInformationRepository.findOne(id);
        return geoLocationInformationMapper.toDto(geoLocationInformation);
    }

    /**
     * Delete the geoLocationInformation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeoLocationInformation : {}", id);
        geoLocationInformationRepository.delete(id);
    }
}
