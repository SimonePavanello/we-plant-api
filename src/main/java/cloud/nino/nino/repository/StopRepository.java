package cloud.nino.nino.repository;

import cloud.nino.nino.domain.Stop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Stop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StopRepository extends JpaRepository<Stop, Long> {

}
