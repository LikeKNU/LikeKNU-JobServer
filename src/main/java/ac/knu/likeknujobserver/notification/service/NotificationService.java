package ac.knu.likeknujobserver.notification.service;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.domain.Device;
import ac.knu.likeknujobserver.notification.domain.Notification;
import ac.knu.likeknujobserver.notification.firebase.FirebaseCloudMessage;
import ac.knu.likeknujobserver.notification.repository.DeviceRepository;
import ac.knu.likeknujobserver.notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class NotificationService {

    private static final String OPEN_PAGE_URL = "/notification";

    private final DeviceRepository deviceRepository;
    private final NotificationRepository notificationRepository;
    private final FirebaseCloudMessage firebaseCloudMessage;

    public NotificationService(DeviceRepository deviceRepository, NotificationRepository notificationRepository, FirebaseCloudMessage firebaseCloudMessage) {
        this.deviceRepository = deviceRepository;
        this.notificationRepository = notificationRepository;
        this.firebaseCloudMessage = firebaseCloudMessage;
    }

    @Async
    @Transactional
    public void sendPushNotificationOfAnnouncement(Announcement announcement) {
        Campus campus = announcement.getCampus();
        Tag tag = announcement.getTag();
        List<Device> subscribedDevices = getDevices(campus, tag).stream()
                .filter(Device::isTurnOnNotification)
                .toList();

        List<String> tokens = subscribedDevices.stream()
                .map(Device::getFcmToken)
                .filter(Objects::nonNull)
                .toList();
        if (!tokens.isEmpty()) {
            String title = String.format("[%s] 새로운 공지사항", tag.getTagName());
            String body = announcement.getAnnouncementTitle();
            firebaseCloudMessage.sendMessage(title, body, OPEN_PAGE_URL, tokens);
            saveNotification(announcement, subscribedDevices);
        }
    }

    private List<Device> getDevices(Campus campus, Tag tag) {
        if (campus.equals(Campus.ALL)) {
            return deviceRepository.findBySubscribeTagsContaining(tag);
        }
        return deviceRepository.findByCampusAndSubscribeTagsContaining(campus, tag);
    }

    private void saveNotification(Announcement announcement, List<Device> subscribedDevices) {
        Notification notification = Notification.of(announcement);
        notificationRepository.save(notification);

        subscribedDevices.forEach(device -> device.addNotification(notification));
    }
}
