package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * Spring Data  repository for the Stop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StopCustomRepository extends JpaRepository<Stop, Long> {
    Collection<Stop> findByVisit_Id(long visitId);

}
