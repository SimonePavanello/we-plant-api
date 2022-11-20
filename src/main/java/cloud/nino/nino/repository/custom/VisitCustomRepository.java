package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.User;
import cloud.nino.nino.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Visit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitCustomRepository extends JpaRepository<Visit, Long> {

    @Query("select visit from Visit visit where visit.user.login = ?#{principal.username}")
    List<Visit> findByUserIsCurrentUser();

    List<Visit> findByUserAndActiveOrderByModifiedDateDesc(User user, Boolean active);

    List<Visit>  findAllByEventAndlocation_Id(Long eventAndlocationId);
}
