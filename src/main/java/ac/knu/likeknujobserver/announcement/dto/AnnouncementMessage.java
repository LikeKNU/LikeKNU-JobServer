package ac.knu.likeknujobserver.announcement.dto;

import ac.knu.likeknujobserver.announcement.value.Category;
import ac.knu.likeknujobserver.common.value.Campus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AnnouncementMessage {

    private String title;
    private String announcementUrl;
    private LocalDate announcementDate;
    private Campus campus;
    private Category category;

    protected AnnouncementMessage() {
    }

    @Builder
    public AnnouncementMessage(String title, String announcementUrl, LocalDate announcementDate, Campus campus, Category category) {
        this.title = title;
        this.announcementUrl = announcementUrl;
        this.announcementDate = announcementDate;
        this.campus = campus;
        this.category = category;
    }
}
