package cloud.nino.nino.repository;

import cloud.nino.nino.domain.Poi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Poi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {

}
