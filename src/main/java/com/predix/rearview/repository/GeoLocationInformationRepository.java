package edu.four04.sscapp.repository;

import edu.four04.sscapp.domain.GeoLocationInformation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GeoLocationInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeoLocationInformationRepository extends JpaRepository<GeoLocationInformation, Long> {

}
