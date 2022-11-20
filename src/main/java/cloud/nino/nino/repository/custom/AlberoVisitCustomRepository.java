package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.AlberoVisit;
import cloud.nino.nino.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AlberoVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlberoVisitCustomRepository extends JpaRepository<AlberoVisit, Long> {

    @Query("select albero_visit from AlberoVisit albero_visit where albero_visit.user.login = ?#{principal.username}")
    List<AlberoVisit> findByUserIsCurrentUser();

    List<AlberoVisit> findAllByUser_AuthoritiesIn(List<Authority> authorities);

}
