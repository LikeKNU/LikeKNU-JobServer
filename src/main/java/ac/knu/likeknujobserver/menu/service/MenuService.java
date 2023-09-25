package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.domain.value.CacheCamCafe;
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
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    private static final Map<CacheCamCafe, Queue<MenuMessage>> MENU_CACHE = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        Stream.of(CacheCamCafe.values()).forEach((CacheCamCafe c) -> {
            if (!MENU_CACHE.containsKey(c))
                MENU_CACHE.put(c, new ConcurrentLinkedQueue<>());
            importFromMenuRepositoryAndCache(c);
        });
    }

    /**
     * db에서 이번주 일요일부터 menu를 가져와 캐시에 저장
     *
     * @param c
     */
    private void importFromMenuRepositoryAndCache(CacheCamCafe c) {
        LocalDate thisSunday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        Cafeteria cafeteria = cafeteriaRepository.findCafeteriaByCampusAndCafeteriaName(c.getCampus(), c.getCafeteriaName())
                .orElseThrow(NullPointerException::new);
        List<Menu> menusByMenuDateAfter = menuRepository.findMenusByMenuDateAfterAndCafeteria(thisSunday, cafeteria);
        Queue<MenuMessage> menuMessages = MENU_CACHE.get(c);

        menusByMenuDateAfter.forEach((Menu m) -> menuMessages.add(MenuMessage.of(m)));
    }

    public void updateMenu(MenuMessage menuMessage) {
        if (existMenuMessage(menuMessage))
            return;

        cachingMenuMessage(menuMessage);
        Cafeteria cafeteria = cafeteriaRepository.findCafeteriaByCampusAndCafeteriaName(menuMessage.getCampus(), menuMessage.getCafeteria())
                .orElseThrow(NullPointerException::new);
        menuRepository.save(Menu.of(menuMessage, cafeteria));
    }

    private boolean existMenuMessage(MenuMessage menuMessage) {
        return MENU_CACHE.get(CacheCamCafe.of(menuMessage)).contains(menuMessage);
    }

    private void cachingMenuMessage(MenuMessage menuMessage) {
        Queue<MenuMessage> menuMessages = MENU_CACHE.get(CacheCamCafe.of(menuMessage));
        menuMessages.add(menuMessage);
    }

    @Component
    static class MenuCacheScheduler {

        /**
         * 매주 금요일 18시 캐시 초기화
         *      크롤러 작동 요일: 일 ~ 목
         *      크롤러 작동 시간: 9 ~ 19
         */
        @Scheduled(cron = "0 0 18 * * FRI")
        public void scheduledMenuCache() {
            Stream.of(CacheCamCafe.values()).forEach((CacheCamCafe c) -> MENU_CACHE.get(c).clear());
        }
    }

}
