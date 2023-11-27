package ac.knu.likeknujobserver.announcement.repository;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnnouncementRepository extends JpaRepository<Announcement, String> {

    List<Announcement> findTop30ByCategoryOrderByCollectedAtDesc(Category category);

    Optional<Announcement> findByAnnouncementUrl(String url);
}
