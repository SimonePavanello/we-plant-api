package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityCustomRepository extends JpaRepository<Authority, String> {
    Optional<Authority> findFirstByName(String name);
}
