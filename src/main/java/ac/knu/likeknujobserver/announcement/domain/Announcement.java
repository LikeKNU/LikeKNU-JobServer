package ac.knu.likeknujobserver.announcement.domain;

import ac.knu.likeknujobserver.announcement.dto.AnnouncementMessage;
import ac.knu.likeknujobserver.announcement.value.Category;
import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.common.value.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "announcement")
@Entity
public class Announcement extends BaseEntity {

    @Column(name = "announcement_title", nullable = false)
    private String announcementTitle;

    @Column(name = "announcement_url", nullable = false)
    private String announcementUrl;

    @Column(name = "announcement_date", nullable = false)
    private LocalDate announcementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "campus", nullable = false)
    private Campus campus;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false)
    private Tag tag;

    @CreatedDate
    @Column(name = "collected_at")
    private LocalDateTime collectedAt;

    protected Announcement() {
        super(Domain.ANNOUNCEMENT);
    }

    @Builder
    public Announcement(String announcementTitle, String announcementUrl, LocalDate announcementDate, Campus campus, Category category, Tag tag) {
        this();
        this.announcementTitle = announcementTitle;
        this.announcementUrl = announcementUrl;
        this.announcementDate = announcementDate;
        this.campus = campus;
        this.category = category;
        this.tag = tag;
    }

    public static Announcement of(AnnouncementMessage announcementMessage, Tag tag) {
        return Announcement.builder()
                .announcementTitle(announcementMessage.getTitle())
                .announcementUrl(announcementMessage.getAnnouncementUrl())
                .announcementDate(announcementMessage.getAnnouncementDate())
                .campus(announcementMessage.getCampus())
                .category(announcementMessage.getCategory())
                .tag(tag)
                .build();
    }

    public boolean isSameUrl(String url) {
        return this.announcementUrl.equals(url);
    }

    public void modifyTitle(String title) {
        this.announcementTitle = title;
        this.collectedAt = LocalDateTime.now();
    }
}
