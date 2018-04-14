package com.predix.rearview.repository;

import com.predix.rearview.domain.GeoLocationInformation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GeoLocationInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeoLocationInformationRepository extends JpaRepository<GeoLocationInformation, Long> {

}
