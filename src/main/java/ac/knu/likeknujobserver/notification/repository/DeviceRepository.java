package ac.knu.likeknujobserver.notification.repository;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.domain.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface DeviceRepository extends CrudRepository<Device, String> {

    List<Device> findBySubscribeTags(Set<Tag> subscribeTags);

    List<Device> findByCampusAndSubscribeTags(Campus campus, Set<Tag> subscribeTags);
}
