package ac.knu.likeknujobserver.announcement.service;

import ac.knu.likeknujobserver.announcement.api.OpenAIFineTuning;
import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.dto.AnnouncementMessage;
import ac.knu.likeknujobserver.announcement.repository.AnnouncementRepository;
import ac.knu.likeknujobserver.announcement.value.Category;
import ac.knu.likeknujobserver.announcement.value.Tag;
import ac.knu.likeknujobserver.notification.service.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Transactional(readOnly = true)
@Service
public class AnnouncementService {

    private static final Map<Category, Queue<AnnouncementMessage>> ANNOUNCEMENT_CACHE = new ConcurrentHashMap<>();
    private static final int CACHE_SIZE = 30;

    private final AnnouncementRepository announcementRepository;
    private final NotificationService notificationService;
    private final OpenAIFineTuning openAIFineTuning;

    public AnnouncementService(AnnouncementRepository announcementRepository, NotificationService notificationService, OpenAIFineTuning openAIFineTuning) {
        this.announcementRepository = announcementRepository;
        this.notificationService = notificationService;
        this.openAIFineTuning = openAIFineTuning;
    }

    @PostConstruct
    void init() {
        Map<Category, List<Announcement>> recentAnnouncementsByCategory = groupingRecentAnnouncementsByCategory();
        initializeAnnouncementCache(recentAnnouncementsByCategory);
    }

    private Map<Category, List<Announcement>> groupingRecentAnnouncementsByCategory() {
        return Arrays.stream(Category.values())
                .map(announcementRepository::findTop20ByCategoryOrderByAnnouncementDateDesc)
                .flatMap(Collection::stream)
                .collect(groupingBy(Announcement::getCategory));
    }

    private void initializeAnnouncementCache(Map<Category, List<Announcement>> announcementsByCategory) {
        Arrays.stream(Category.values())
                .forEach(category -> ANNOUNCEMENT_CACHE.put(category, new LinkedBlockingQueue<>()));

        announcementsByCategory.keySet().stream()
                .flatMap(category -> announcementsByCategory.get(category).stream()
                        .map(AnnouncementMessage::of))
                .forEach(announcementMessage -> {
                    Queue<AnnouncementMessage> announcementMessages = ANNOUNCEMENT_CACHE.get(announcementMessage.getCategory());
                    announcementMessages.offer(announcementMessage);
                });
    }

    @Transactional
    public void updateAnnouncement(AnnouncementMessage announcementMessage) {
        if (isAlreadyCollectedAnnouncement(announcementMessage)) {
            return;
        }

        cachingAnnouncementMessage(announcementMessage);
        Tag tag = abstractTagOfAnnouncement(announcementMessage);
        Announcement announcement = Announcement.of(announcementMessage, tag);
        announcementRepository.save(announcement);

        if (!announcement.getTag().equals(Tag.ETC)) {
            notificationService.sendPushNotificationOfAnnouncement(announcement);
        }
    }

    private boolean isAlreadyCollectedAnnouncement(AnnouncementMessage announcementMessage) {
        return ANNOUNCEMENT_CACHE.get(announcementMessage.getCategory()).contains(announcementMessage);
    }

    private void cachingAnnouncementMessage(AnnouncementMessage announcementMessage) {
        Queue<AnnouncementMessage> announcementMessages = ANNOUNCEMENT_CACHE.get(announcementMessage.getCategory());
        announcementMessages.offer(announcementMessage);
        if (announcementMessages.size() > CACHE_SIZE) {
            announcementMessages.poll();
        }
    }

    private Tag abstractTagOfAnnouncement(AnnouncementMessage announcementMessage) {
        if (announcementMessage.getCategory().equals(Category.STUDENT_NEWS)) {
            return openAIFineTuning.abstractTagOfAnnouncement(announcementMessage.getTitle());
        }

        return Tag.valueOf(announcementMessage.getCategory().name());
    }
}
