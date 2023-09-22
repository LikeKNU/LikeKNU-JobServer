package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.domain.CityBus;
import ac.knu.likeknujobserver.common.EntityGraphNames;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityBusRepository extends JpaRepository<CityBus, String> {

    @EntityGraph(value = EntityGraphNames.ROUTE_BUSES)
    List<CityBus> findByIsRealtimeIsTrue();

    @EntityGraph(value = EntityGraphNames.ROUTE_BUSES)
    Optional<CityBus> findByBusNumberAndBusStop(String busNumber, String busStop);
}
