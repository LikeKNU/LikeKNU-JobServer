package ac.knu.likeknujobserver.notification.domain;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.EntityGraphNames;
import ac.knu.likeknujobserver.common.value.Campus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = EntityGraphNames.DEVICE_NOTIFICATIONS,
                        attributeNodes = @NamedAttributeNode(value = "notifications")
                )
        }
)
@Table(name = "device")
@Entity
public class Device {

    @Id
    private String id;

    @Column(unique = true)
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @CollectionTable(name = "subscribe", joinColumns = @JoinColumn(name = "device_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "tag")
    @ElementCollection
    private Set<Tag> subscribeTags = new HashSet<>();

    @JoinTable(name = "device_notification",
            joinColumns = @JoinColumn(name = "device_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @ManyToMany
    private List<Notification> notifications = new ArrayList<>();

    protected Device() {
    }

    @Builder
    public Device(String id, String fcmToken, Campus campus, LocalDateTime registeredAt) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.campus = campus;
        this.registeredAt = registeredAt;
    }
}
