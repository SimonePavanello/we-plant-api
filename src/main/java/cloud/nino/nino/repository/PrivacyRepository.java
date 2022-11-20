package cloud.nino.nino.repository;

import cloud.nino.nino.domain.Privacy;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Privacy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivacyRepository extends JpaRepository<Privacy, Long> {

    @Query("select privacy from Privacy privacy where privacy.user.login = ?#{principal.username}")
    List<Privacy> findByUserIsCurrentUser();

}
