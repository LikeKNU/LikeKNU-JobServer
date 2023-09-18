package ac.knu.likeknujobserver.announcement.repository;

import ac.knu.likeknujobserver.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
