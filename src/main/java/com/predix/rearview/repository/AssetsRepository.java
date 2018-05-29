package edu.four04.sscapp.repository;

import edu.four04.sscapp.domain.Assets;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Assets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetsRepository extends JpaRepository<Assets, Long> {

}
