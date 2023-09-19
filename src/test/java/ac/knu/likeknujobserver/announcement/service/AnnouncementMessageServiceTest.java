package ac.knu.likeknujobserver.announcement.service;

import ac.knu.likeknujobserver.announcement.dto.AnnouncementMessage;
import ac.knu.likeknujobserver.announcement.value.Category;
import ac.knu.likeknujobserver.common.value.Campus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@DisplayName("공지사항 서비스 테스트")
@ExtendWith(value = MockitoExtension.class)
class AnnouncementMessageServiceTest {

    @Mock
    private AnnouncementService announcementService;

    @DisplayName("새로운 공지사항을 업데이트한다.")
    @Test
    void updateNewAnnouncement() throws Exception {
        // given
        AnnouncementMessage testAnnouncement = AnnouncementMessage.builder()
                .title("Test announcement")
                .announcementUrl("https://test.com")
                .announcementDate(LocalDate.now())
                .campus(Campus.ALL)
                .category(Category.LIBRARY)
                .build();

        // when
        announcementService.init();
        announcementService.updateAnnouncement(testAnnouncement);

        // then
        verify(announcementService, times(1)).init();
        verify(announcementService, times(1)).updateAnnouncement(testAnnouncement);
    }
}