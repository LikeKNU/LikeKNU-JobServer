package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.domain.value.CafeteriaInformation;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MenuService {

    private static final Map<CafeteriaInformation, Set<MenuMessage>> MENU_CACHE = new ConcurrentHashMap<>();

    private final MenuRepository menuRepository;
    private final CafeteriaRepository cafeteriaRepository;

    @PostConstruct
    void init() {
        Arrays.stream(CafeteriaInformation.values())
                .forEach(cafeteriaInformation -> {
                    MENU_CACHE.put(cafeteriaInformation, Collections.synchronizedSet(new HashSet<>()));
                    importFromMenuRepositoryAndCache(cafeteriaInformation);
                });
    }

    /**
     * db에서 이번주 일요일부터 menu를 가져와 캐시에 저장
     *
     * @param cafeteriaInformation
     */
    private void importFromMenuRepositoryAndCache(CafeteriaInformation cafeteriaInformation) {
        LocalDate thisSunday = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        cafeteriaRepository.findCafeteriaByCampusAndCafeteriaName(
                cafeteriaInformation.getCampus(), cafeteriaInformation.getCafeteriaName()
        ).ifPresent(cafeteria -> menuRepository.findMenusByMenuDateAfterAndCafeteria(thisSunday, cafeteria)
                .forEach(menu -> {
                    caching(MenuMessage.of(menu, cafeteria));
                }));
    }

    public void updateMenu(MenuMessage menuMessage) {
        if (isAlreadyCollected(menuMessage))
            return;

        caching(menuMessage);
        Cafeteria cafeteria = cafeteriaRepository.findCafeteriaByCampusAndCafeteriaName(menuMessage.getCampus(), menuMessage.getCafeteria())
                .orElseThrow(NullPointerException::new);

        menuRepository.findByCafeteriaAndMenuDateAndMealType(cafeteria, menuMessage.getDate(), menuMessage.getMealType())
                .ifPresentOrElse(
                        menu -> menu.setMenus(menuMessage.getMenus()),
                        () -> menuRepository.save(Menu.of(menuMessage, cafeteria))
                );
    }

    private boolean isAlreadyCollected(MenuMessage menuMessage) {
        return MENU_CACHE.get(CafeteriaInformation.of(menuMessage))
                .contains(menuMessage);
    }

    private void caching(MenuMessage menuMessage) {
        Set<MenuMessage> menuMessages = MENU_CACHE.get(CafeteriaInformation.of(menuMessage));
        menuMessages.add(menuMessage);
    }

    @Component
    static class MenuCacheScheduler {

        /**
         * 매주 금요일 18시 캐시 초기화
         * 크롤러 작동 요일: 일 ~ 목
         * 크롤러 작동 시간: 9 ~ 19
         */
        @Scheduled(cron = "0 0 18 * * FRI")
        public void scheduledMenuCache() {
            Arrays.stream(CafeteriaInformation.values())
                    .forEach(cafeteriaInformation -> MENU_CACHE.get(cafeteriaInformation)
                            .clear());
        }
    }
}
