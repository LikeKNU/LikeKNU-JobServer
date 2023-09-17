package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.model.CityBus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityBusRepository extends JpaRepository<CityBus, Long> {

    List<CityBus> findByRealtimeIsTrue();
}
