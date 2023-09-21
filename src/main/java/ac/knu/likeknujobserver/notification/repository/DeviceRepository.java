package ac.knu.likeknujobserver.notification.repository;

import ac.knu.likeknujobserver.notification.domain.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, String> {
}
