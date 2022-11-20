package cloud.nino.nino.repository;

import cloud.nino.nino.domain.Essenza;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Essenza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EssenzaRepository extends JpaRepository<Essenza, Long> {

}
