package cloud.nino.nino.repository;

import cloud.nino.nino.domain.AlberoVisit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AlberoVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlberoVisitRepository extends JpaRepository<AlberoVisit, Long> {

    @Query("select albero_visit from AlberoVisit albero_visit where albero_visit.user.login = ?#{principal.username}")
    List<AlberoVisit> findByUserIsCurrentUser();

}
