package ac.knu.likeknujobserver.announcement.service;

import ac.knu.likeknujobserver.announcement.dto.AnnouncementMessage;
import ac.knu.likeknujobserver.announcement.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Transactional
    public void updateAnnouncement(AnnouncementMessage announcementMessage) {
    }
}
