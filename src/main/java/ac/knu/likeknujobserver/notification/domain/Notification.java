package ac.knu.likeknujobserver.notification.domain;

import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "notification")
@Entity
public class Notification extends BaseEntity {

    @Column(nullable = false)
    private String notificationTitle;

    @Column(nullable = false)
    private String notificationBody;

    @Column(nullable = false)
    private LocalDateTime notificationDate;

    protected Notification() {
        super(Domain.NOTIFICATION);
    }

    @Builder
    public Notification(Domain domain, String notificationTitle, String notificationBody, LocalDateTime notificationDate) {
        this();
        this.notificationTitle = notificationTitle;
        this.notificationBody = notificationBody;
        this.notificationDate = notificationDate;
    }

    public static Notification of(Announcement announcement) {
        return Notification.builder()
                .notificationTitle(String.format("[%s] 새로운 공지사항", announcement.getTag().getTagName()))
                .notificationBody(announcement.getAnnouncementTitle())
                .notificationDate(LocalDateTime.now())
                .build();
    }
}
