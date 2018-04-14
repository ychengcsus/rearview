package com.predix.rearview.service.mapper;

import com.predix.rearview.domain.*;
import com.predix.rearview.service.dto.AssetsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Assets and its DTO AssetsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetsMapper extends EntityMapper<AssetsDTO, Assets> {



    default Assets fromId(Long id) {
        if (id == null) {
            return null;
        }
        Assets assets = new Assets();
        assets.setId(id);
        return assets;
    }
}
