package com.predix.rearview.service;

import com.predix.rearview.domain.TrafficMeasurements;
import com.predix.rearview.repository.TrafficMeasurementsRepository;
import com.predix.rearview.service.dto.TrafficMeasurementsDTO;
import com.predix.rearview.service.mapper.TrafficMeasurementsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TrafficMeasurements.
 */
@Service
@Transactional
public class TrafficMeasurementsService {

    private final Logger log = LoggerFactory.getLogger(TrafficMeasurementsService.class);

    private final TrafficMeasurementsRepository trafficMeasurementsRepository;

    private final TrafficMeasurementsMapper trafficMeasurementsMapper;

    public TrafficMeasurementsService(TrafficMeasurementsRepository trafficMeasurementsRepository, TrafficMeasurementsMapper trafficMeasurementsMapper) {
        this.trafficMeasurementsRepository = trafficMeasurementsRepository;
        this.trafficMeasurementsMapper = trafficMeasurementsMapper;
    }

    /**
     * Save a trafficMeasurements.
     *
     * @param trafficMeasurementsDTO the entity to save
     * @return the persisted entity
     */
    public TrafficMeasurementsDTO save(TrafficMeasurementsDTO trafficMeasurementsDTO) {
        log.debug("Request to save TrafficMeasurements : {}", trafficMeasurementsDTO);
        TrafficMeasurements trafficMeasurements = trafficMeasurementsMapper.toEntity(trafficMeasurementsDTO);
        trafficMeasurements = trafficMeasurementsRepository.save(trafficMeasurements);
        return trafficMeasurementsMapper.toDto(trafficMeasurements);
    }

    /**
     * Get all the trafficMeasurements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrafficMeasurementsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrafficMeasurements");
        return trafficMeasurementsRepository.findAll(pageable)
            .map(trafficMeasurementsMapper::toDto);
    }

    /**
     * Get one trafficMeasurements by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TrafficMeasurementsDTO findOne(Long id) {
        log.debug("Request to get TrafficMeasurements : {}", id);
        TrafficMeasurements trafficMeasurements = trafficMeasurementsRepository.findOne(id);
        return trafficMeasurementsMapper.toDto(trafficMeasurements);
    }

    /**
     * Delete the trafficMeasurements by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TrafficMeasurements : {}", id);
        trafficMeasurementsRepository.delete(id);
    }
}
