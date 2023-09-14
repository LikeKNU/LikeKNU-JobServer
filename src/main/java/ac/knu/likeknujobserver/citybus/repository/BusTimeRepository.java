package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.model.BusTime;
import ac.knu.likeknujobserver.citybus.model.value.BusTimeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusTimeRepository extends JpaRepository<BusTime, BusTimeId> {
}
