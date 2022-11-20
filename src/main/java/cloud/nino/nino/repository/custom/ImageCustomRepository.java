package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageCustomRepository extends JpaRepository<Image, Long> {

    List<Image> findByAlbero_Id(Long alberoId);
}
