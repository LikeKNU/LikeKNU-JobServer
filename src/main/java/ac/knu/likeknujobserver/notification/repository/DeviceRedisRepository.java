package ac.knu.likeknujobserver.notification.repository;

import ac.knu.likeknujobserver.notification.model.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRedisRepository extends CrudRepository<Device, String> {
}
