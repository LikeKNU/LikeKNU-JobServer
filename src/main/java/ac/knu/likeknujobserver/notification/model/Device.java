package ac.knu.likeknujobserver.notification.model;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
@Getter
@RedisHash(value = "device")
public class Device {

    private String id;
    private Campus campus;
    private String fcmToken;
    private Set<Tag> subscribeTags = new HashSet<>();

    protected Device() {
    }

    @Builder
    public Device(String id, Campus campus, String fcmToken) {
        this.id = id;
        this.campus = campus;
        this.fcmToken = fcmToken;
    }

    public void updateSubscribeTags(List<Tag> tags) {
        subscribeTags.clear();
        subscribeTags.addAll(tags);
    }
}
