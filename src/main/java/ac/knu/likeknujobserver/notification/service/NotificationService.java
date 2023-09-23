package ac.knu.likeknujobserver.notification.service;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.domain.Device;
import ac.knu.likeknujobserver.notification.repository.DeviceRepository;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
        Tag tag = announcement.getTag();
        List<Device> subscribedDevices;
        if (campus.equals(Campus.ALL)) {
            subscribedDevices = deviceRepository.findBySubscribeTagsContaining(tag);
        } else {
            subscribedDevices = deviceRepository.findByCampusAndSubscribeTagsContaining(campus, tag);
        }

        List<String> tokens = subscribedDevices.stream()
                .map(Device::getFcmToken)
                .filter(Objects::nonNull)
                .toList();
        sendFcmCloudMessage(tokens, announcement.getTag(), announcement);
        // TODO Save notifications
    }

    private void sendFcmCloudMessage(List<String> subscribedDevices, Tag tag, Announcement announcement) {
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(String.format("[%s] 새로운 공지사항", tag.getTagName()))
                        .setBody(announcement.getAnnouncementTitle())
                        .build())
                .putData("announcement_url", announcement.getAnnouncementUrl())
                .addAllTokens(subscribedDevices)
                .build();

        try {
            BatchResponse batchResponse = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
