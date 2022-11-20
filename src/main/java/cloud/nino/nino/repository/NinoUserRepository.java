package cloud.nino.nino.repository;

import cloud.nino.nino.domain.NinoUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NinoUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NinoUserRepository extends JpaRepository<NinoUser, Long> {

}
