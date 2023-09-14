package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.model.CityBus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityBusRepository extends JpaRepository<CityBus, Long> {
}
