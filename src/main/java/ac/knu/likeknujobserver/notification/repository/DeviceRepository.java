package ac.knu.likeknujobserver.notification.repository;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {

    List<Device> findBySubscribeTagsContaining(Tag tag);

    List<Device> findByCampusAndSubscribeTagsContaining(Campus campus, Tag tag);
}
