package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.Albero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Albero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlberoCustomRepository extends JpaRepository<Albero, Long> {
    Page<Albero> findAllByEssenzaIsNull(Pageable pageable);

    Optional<Albero> findFirstByMain_IdOrderByDataUltimoAggiornamentoDesc(Long id);

    @Query("SELECT DISTINCT main.id FROM Albero WHERE idPianta is null")
    List<Long> findDistinctByMain();

    long countByEssenzaIsNull();

    Optional<Albero> findFirstByIdPiantaOrderByDataUltimoAggiornamentoDesc(Integer idPianta);

    Optional<Albero> findFirstByIdPianta(Integer idPianta);

    @Query("SELECT DISTINCT main.id FROM Albero WHERE idPianta is not null and codiceArea =  ?1")
    List<Long> findDistinctByMainAndCodiceArea(Integer codiceArea);

    List<Albero> findAllByCodiceAreaAndIdPiantaNotNull(Integer codiceArea);

    List<Albero> findAllByModificatoDaNotNullAndDataUltimoAggiornamentoIsGreaterThanEqualAndDataUltimoAggiornamentoIsLessThanEqual(ZonedDateTime from, ZonedDateTime to);

    Optional<Albero> findFirstByMainIdOrderByDataUltimoAggiornamentoDesc(Long mainId);

    List<Albero> findByMain_IdOrderByDataUltimoAggiornamento(Long mainId);

    @Query("SELECT DISTINCT main.id FROM Albero where data_ultimo_aggiornamento > '2019-01-01'")
    List<Long> findDistinctByMainModified();

    @Query("SELECT a FROM Albero a where a.main.id = id")
    List<Albero> findAllByMainIdEqualsToId(Pageable pageable);

}
