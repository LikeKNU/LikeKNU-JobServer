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

        List<String> tokens = subscribedDevices.stream()
                .map(Device::getFcmToken)
                .filter(Objects::nonNull)
                .toList();
        sendFcmCloudMessage(tokens, announcement.getTag(), announcement.getAnnouncementUrl());
        // TODO Save notifications
    }

    private void sendFcmCloudMessage(List<String> subscribedDevices, Tag tag, String announcementUrl) {
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .setNotification(Notification.builder()
                        .setTitle(String.format("[%s] 신규 공지사항", tag.getTagName()))
                        .setBody("새로운 공지사항이 등록되었어요!")
                        .build())
                .putData("announcement_url", announcementUrl)
                .addAllTokens(subscribedDevices)
                .build();

        try {
            BatchResponse batchResponse = FirebaseMessaging.getInstance().sendEachForMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
