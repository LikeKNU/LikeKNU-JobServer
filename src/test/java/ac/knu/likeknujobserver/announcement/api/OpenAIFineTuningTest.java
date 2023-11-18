package ac.knu.likeknujobserver.announcement.api;

import ac.knu.likeknujobserver.announcement.value.Tag;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class OpenAIFineTuningTest {

    @Autowired
    private OpenAIFineTuning openAIFineTuning;

    @Test
    void test() {
        Tag tag = openAIFineTuning.abstractTagOfAnnouncement("[재학생 필수교육] 인터넷·스마트폰 과의존 예방교육 실시 안내(마일리지 부여)");
        log.info("tag = {}", tag.getTagName());
    }
}