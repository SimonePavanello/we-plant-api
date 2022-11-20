package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.Essenza;
import cloud.nino.nino.domain.EssenzaAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the EssenzaAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EssenzaAuditCustomRepository extends JpaRepository<EssenzaAudit, Long> {
    @Query("select essenza_audit from EssenzaAudit essenza_audit where essenza_audit.modificatoDa.login = ?#{principal.username}")
    List<EssenzaAudit> findByModificatoDaIsCurrentUser();

    Optional<EssenzaAudit> findFirstByEssenzaOrderByDataUltimoAggiornamentoDesc(Essenza essenza);

    Optional<EssenzaAudit> findFirstByEssenza_IdOrderByDataUltimoAggiornamentoDesc(Long essenzaId);

}
