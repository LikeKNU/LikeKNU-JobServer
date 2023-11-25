package ac.knu.likeknujobserver.announcement.repository;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, String> {

    List<Announcement> findTop30ByCategoryOrderByCollectedAtDesc(Category category);

    List<Announcement> findByAnnouncementTitle(String title);
}
