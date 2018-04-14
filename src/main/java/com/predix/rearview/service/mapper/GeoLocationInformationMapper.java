package com.predix.rearview.service.mapper;

import com.predix.rearview.domain.*;
import com.predix.rearview.service.dto.GeoLocationInformationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GeoLocationInformation and its DTO GeoLocationInformationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeoLocationInformationMapper extends EntityMapper<GeoLocationInformationDTO, GeoLocationInformation> {



    default GeoLocationInformation fromId(Long id) {
        if (id == null) {
            return null;
        }
        GeoLocationInformation geoLocationInformation = new GeoLocationInformation();
        geoLocationInformation.setId(id);
        return geoLocationInformation;
    }
}
