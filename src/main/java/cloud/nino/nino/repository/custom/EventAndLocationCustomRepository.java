package cloud.nino.nino.repository.custom;

import cloud.nino.nino.domain.EventAndLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EventAndLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventAndLocationCustomRepository extends JpaRepository<EventAndLocation, Long> {

    @Query(value = "select distinct event_and_location from EventAndLocation event_and_location left join fetch event_and_location.adminUsers left join fetch event_and_location.pois",
        countQuery = "select count(distinct event_and_location) from EventAndLocation event_and_location")
    Page<EventAndLocation> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct event_and_location from EventAndLocation event_and_location left join fetch event_and_location.adminUsers left join fetch event_and_location.pois")
    List<EventAndLocation> findAllWithEagerRelationships();

    @Query(value = "select distinct event_and_location from EventAndLocation event_and_location inner join fetch event_and_location.adminUsers au left join fetch event_and_location.pois " +
        "where au.user.login = ?#{principal.username}")
    List<EventAndLocation> findAllWithEagerRelationshipsByCurrentUser();

    @Query("select event_and_location from EventAndLocation event_and_location left join fetch event_and_location.adminUsers left join fetch event_and_location.pois where event_and_location.id =:id")
    Optional<EventAndLocation> findOneWithEagerRelationships(@Param("id") Long id);
}
