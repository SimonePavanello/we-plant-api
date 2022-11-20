package cloud.nino.nino.repository;

import cloud.nino.nino.domain.EssenzaAudit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EssenzaAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EssenzaAuditRepository extends JpaRepository<EssenzaAudit, Long> {

    @Query("select essenza_audit from EssenzaAudit essenza_audit where essenza_audit.modificatoDa.login = ?#{principal.username}")
    List<EssenzaAudit> findByModificatoDaIsCurrentUser();

}
