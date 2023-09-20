package ac.knu.likeknujobserver;

import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.notification.model.Device;
import ac.knu.likeknujobserver.notification.repository.DeviceRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class RedisTest {

    @Autowired
    private DeviceRedisRepository deviceRedisRepository;

    @Test
    void test() {
        Device device = Device.builder()
                .id(UUID.randomUUID().toString())
                .campus(Campus.CHEONAN)
                .fcmToken(UUID.randomUUID().toString())
                .build();
        device.updateSubscribeTags(List.of(Tag.DORMITORY, Tag.ENROLMENT));
        deviceRedisRepository.save(device);
        deviceRedisRepository.findAll().forEach(savedDevice -> log.info("savedDevice = {}", savedDevice));
    }
}
