package com.predix.rearview.repository;

import com.predix.rearview.domain.TrafficMeasurements;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TrafficMeasurements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrafficMeasurementsRepository extends JpaRepository<TrafficMeasurements, Long> {

}
