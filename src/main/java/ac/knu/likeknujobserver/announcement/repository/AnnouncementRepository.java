package ac.knu.likeknujobserver.announcement.repository;

import ac.knu.likeknujobserver.announcement.model.Announcement;
import ac.knu.likeknujobserver.announcement.value.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findTop20ByCategoryOrderByAnnouncementDateDesc(Category category);
}