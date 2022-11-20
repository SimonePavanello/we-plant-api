package cloud.nino.nino.repository;

import cloud.nino.nino.domain.Image;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select image from Image image where image.cratedBy.login = ?#{principal.username}")
    List<Image> findByCratedByIsCurrentUser();

}
