package com.predix.rearview.service.mapper;

import com.predix.rearview.domain.*;
import com.predix.rearview.service.dto.TrafficMeasurementsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TrafficMeasurements and its DTO TrafficMeasurementsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TrafficMeasurementsMapper extends EntityMapper<TrafficMeasurementsDTO, TrafficMeasurements> {



    default TrafficMeasurements fromId(Long id) {
        if (id == null) {
            return null;
        }
        TrafficMeasurements trafficMeasurements = new TrafficMeasurements();
        trafficMeasurements.setId(id);
        return trafficMeasurements;
    }
}
