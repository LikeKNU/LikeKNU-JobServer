package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.model.CityBus;
import ac.knu.likeknujobserver.common.EntityGraphNames;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityBusRepository extends JpaRepository<CityBus, Long> {

    @EntityGraph(value = EntityGraphNames.ROUTE_BUSES)
    List<CityBus> findByRealtimeIsTrue();
}
