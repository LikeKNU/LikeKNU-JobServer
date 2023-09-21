package ac.knu.likeknujobserver.notification.domain;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@RedisHash(value = "device")
public class Device {

    private String id;
    private String fcmToken;
    private Campus campus;
    private Set<Tag> subscribeTags = new HashSet<>();
    private LocalDateTime registeredAt;

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
