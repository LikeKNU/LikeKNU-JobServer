package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.domain.CityBus;
import ac.knu.likeknujobserver.common.EntityGraphNames;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityBusRepository extends JpaRepository<CityBus, String> {

    @EntityGraph(value = EntityGraphNames.BUS_ARRIVAL_TIMES)
    List<CityBus> findByIsRealtimeIsTrue();
}
