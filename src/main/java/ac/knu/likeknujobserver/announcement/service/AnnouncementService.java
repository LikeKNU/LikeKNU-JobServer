package ac.knu.likeknujobserver.announcement.service;

import ac.knu.likeknujobserver.announcement.api.OpenAI;
import ac.knu.likeknujobserver.announcement.domain.Announcement;
import ac.knu.likeknujobserver.announcement.dto.AnnouncementMessage;
import ac.knu.likeknujobserver.announcement.repository.AnnouncementRepository;
import ac.knu.likeknujobserver.announcement.value.Category;
import ac.knu.likeknujobserver.announcement.value.Tag;
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

    private final AnnouncementRepository announcementRepository;
    private final OpenAI openAI;

    public AnnouncementService(AnnouncementRepository announcementRepository, OpenAI openAI) {
        this.announcementRepository = announcementRepository;
        this.openAI = openAI;
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
                .peek(announcementMessage -> log.info("announcementMessage = {}", announcementMessage))
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
        // TODO Push notifications service call
    }

    private boolean isAlreadyCollectedAnnouncement(AnnouncementMessage announcementMessage) {
        return ANNOUNCEMENT_CACHE.get(announcementMessage.getCategory()).contains(announcementMessage);
    }

    private void cachingAnnouncementMessage(AnnouncementMessage announcementMessage) {
        Queue<AnnouncementMessage> announcementMessages = ANNOUNCEMENT_CACHE.get(announcementMessage.getCategory());
        announcementMessages.offer(announcementMessage);
        announcementMessages.poll();
    }

    private Tag abstractTagOfAnnouncement(AnnouncementMessage announcementMessage) {
        return announcementMessage.getCategory().equals(Category.SCHOOL_NEWS)
                ? openAI.abstractTagOfAnnouncement(announcementMessage.getTitle())
                : Tag.valueOf(announcementMessage.getCategory().name());
    }
}
