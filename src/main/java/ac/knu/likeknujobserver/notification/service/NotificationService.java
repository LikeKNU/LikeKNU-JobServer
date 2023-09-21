package ac.knu.likeknujobserver.notification.service;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.domain.Device;
import ac.knu.likeknujobserver.notification.repository.DeviceRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final DeviceRepository deviceRepository;

    public NotificationService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Async
    public void pushNotificationOfAnnouncement(Announcement announcement) {
        Campus campus = announcement.getCampus();
        Set<Tag> tags = Set.of(announcement.getTag());
        List<Device> subscribedDevices;
        if (campus.equals(Campus.ALL)) {
            subscribedDevices = deviceRepository.findBySubscribeTags(tags);
        } else {
            subscribedDevices = deviceRepository.findByCampusAndSubscribeTags(campus, tags);
        }

        subscribedDevices.forEach(device -> {
            sendFcmCloudMessage(device, announcement);
            // TODO Save notifications
        });
    }

    private void sendFcmCloudMessage(Device device, Announcement announcement) {
        // TODO Send FCM cloud messages
    }
}
