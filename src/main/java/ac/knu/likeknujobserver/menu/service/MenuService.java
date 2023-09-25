package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.dto.MenuMessage;
import ac.knu.likeknujobserver.menu.repository.CafeteriaRepository;
import ac.knu.likeknujobserver.menu.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    private static final Map<Campus, Queue<MenuMessage>> MENU_CACHE = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        Stream.of(Campus.values()).forEach((Campus c) -> {
            if(!MENU_CACHE.containsKey(c))
                MENU_CACHE.put(c, new ConcurrentLinkedQueue<>());
            importFromMenuRepositoryAndCache(c);
        });
    }

    /**
     * db에서 이번주 일요일부터 menu를 가져와 캐시에 저장
     * @param c
     */
    private void importFromMenuRepositoryAndCache(Campus c) {
        LocalDate thisSunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        List<Menu> menusByMenuDateAfter = menuRepository.findMenusByMenuDateAfter(thisSunday);
        Queue<MenuMessage> menuMessages = MENU_CACHE.get(c);

        menusByMenuDateAfter.forEach((Menu m) -> menuMessages.add(MenuMessage.of(m)));
    }

    public void updateMenu(MenuMessage menuMessage) {
        if(existMenuMessage(menuMessage))
            return;

        cachingMenuMessage(menuMessage);
        Cafeteria cafeteria = cafeteriaRepository.findCafeteriaByCampusAndCafeteriaName(menuMessage.getCampus(), menuMessage.getCafeteria());
        menuRepository.save(Menu.of(menuMessage, cafeteria));
    }

    private boolean existMenuMessage(MenuMessage menuMessage) {
        return MENU_CACHE.get(menuMessage.getCampus()).contains(menuMessage);
    }

    private void cachingMenuMessage(MenuMessage menuMessage) {
        Queue<MenuMessage> menuMessages = MENU_CACHE.get(menuMessage.getCampus());
        menuMessages.add(menuMessage);
    }

    @Component
    static class MenuCacheScheduler {

        /**
         * 매주 목요일 캐시 초기화
         */
        @Scheduled(cron = "0 0 12 ? ? THU *")
        public void scheduledMenuCache() {
            Stream.of(Campus.values()).forEach((Campus c) -> MENU_CACHE.get(c).clear());
        }
    }

}
