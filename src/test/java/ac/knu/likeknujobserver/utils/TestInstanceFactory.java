package ac.knu.likeknujobserver.utils;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.value.Category;
import ac.knu.likeknujobserver.common.value.Campus;

import java.time.LocalDate;

public class TestInstanceFactory {

    public static Announcement testAnnouncement(String title, Category category, Campus campus) {
        return Announcement.builder()
                .announcementTitle(title)
                .announcementUrl("test")
                .announcementDate(LocalDate.now())
                .campus(campus)
                .category(category)
                .build();
    }
}
