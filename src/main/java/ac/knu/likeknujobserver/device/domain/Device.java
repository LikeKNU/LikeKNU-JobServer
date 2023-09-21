package ac.knu.likeknujobserver.device.domain;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@RedisHash(value = "device")
public class Device {

    private String id;
    private String fcmToken;
    private Campus campus;
    private List<Tag> subscribeTags;

    protected Device() {
    }

    @Builder
    public Device(String id, String fcmToken, Campus campus, List<Tag> subscribeTags) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.campus = campus;
        this.subscribeTags = subscribeTags;
    }
}
