package ac.knu.likeknujobserver.notification.service;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.domain.Device;
import ac.knu.likeknujobserver.notification.domain.Notification;
import ac.knu.likeknujobserver.notification.repository.DeviceRepository;
import ac.knu.likeknujobserver.notification.repository.NotificationRepository;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final DeviceRepository deviceRepository;
    private final NotificationRepository notificationRepository;

    public NotificationService(DeviceRepository deviceRepository, NotificationRepository notificationRepository) {
        this.deviceRepository = deviceRepository;
        this.notificationRepository = notificationRepository;
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
            sendFcmCloudMessage(tokens, announcement.getTag(), announcement);
            saveNotification(announcement, subscribedDevices);
        }
    }

    private List<Device> getDevices(Campus campus, Tag tag) {
        if (campus.equals(Campus.ALL)) {
            return deviceRepository.findBySubscribeTagsContaining(tag);
        }
        return deviceRepository.findByCampusAndSubscribeTagsContaining(campus, tag);
    }

    private void sendFcmCloudMessage(List<String> subscribedDevices, Tag tag, Announcement announcement) {
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder()
                        .setTitle(String.format("[%s] 새로운 공지사항", tag.getTagName()))
                        .setBody(announcement.getAnnouncementTitle())
                        .build())
                .putData("announcement_url", announcement.getAnnouncementUrl())
                .addAllTokens(subscribedDevices)
                .build();

        try {
            BatchResponse batchResponse = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            //TODO FCM error handling
        }
    }

    private void saveNotification(Announcement announcement, List<Device> subscribedDevices) {
        Notification notification = Notification.of(announcement);
        notificationRepository.save(notification);

        subscribedDevices.forEach(device -> device.addNotification(notification));
    }
}
