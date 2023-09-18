package ac.knu.likeknujobserver.announcement.service;

import ac.knu.likeknujobserver.announcement.dto.AnnouncementMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementConsumer {

    private final AnnouncementService announcementService;

    public AnnouncementConsumer(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @RabbitListener(queues = "${rabbitmq.announcement-queue-name}")
    public void consumeAnnouncementMessage(AnnouncementMessage announcementMessage) {
        announcementService.updateAnnouncement(announcementMessage);
    }
}
