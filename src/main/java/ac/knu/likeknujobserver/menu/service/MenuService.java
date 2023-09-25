package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.dto.MenuMessage;
import ac.knu.likeknujobserver.menu.repository.MenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private static final Map<Campus, Queue<MenuMessage>> MENU_CACHE = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        Stream.of(Campus.values()).forEach((Campus c) -> {
            if(!MENU_CACHE.containsKey(c))
                MENU_CACHE.put(c, new ConcurrentLinkedQueue<>());
            else;
            //findbyid
        });
    }

    public void updateMenu(MenuMessage menuMessage) {
        if(existMenuMessage(menuMessage))
            return;

        cachingMenuMessage(menuMessage);
        menuRepository.save(Menu.of(menuMessage));
    }

    private boolean existMenuMessage(MenuMessage menuMessage) {
        return MENU_CACHE.get(menuMessage.getCampus()).contains(menuMessage);
    }

    private void cachingMenuMessage(MenuMessage menuMessage) {
        Queue<MenuMessage> menuMessages = MENU_CACHE.get(menuMessage.getCampus());
        menuMessages.add(menuMessage);
    }

    @Component
    class MenuCacheScheduler {

        @Scheduled(cron = "0 0 12 ? ? THU *")
        public void scheduledMenuCache() {
            Stream.of(Campus.values()).forEach((Campus c) -> MENU_CACHE.get(c).clear());
        }
    }

}
