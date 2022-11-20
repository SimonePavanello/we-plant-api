package cloud.nino.nino.repository;

import cloud.nino.nino.domain.Albero;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Albero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlberoRepository extends JpaRepository<Albero, Long> {

    @Query("select albero from Albero albero where albero.modificatoDa.login = ?#{principal.username}")
    List<Albero> findByModificatoDaIsCurrentUser();

}
