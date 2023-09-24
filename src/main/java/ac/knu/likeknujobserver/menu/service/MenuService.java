package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.dto.MenuMessage;
import ac.knu.likeknujobserver.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private Map<Campus, Queue<MenuMessage>> MENU_CACHE = new ConcurrentHashMap<>();

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
        if(!MENU_CACHE.containsKey(menuMessage.getCampus())) {
            MENU_CACHE.put(menuMessage.getCampus(), new LinkedList<>(List.of(menuMessage)));
            return;
        }

        Queue<MenuMessage> menuMessages = MENU_CACHE.get(menuMessage.getCampus());
        menuMessages.add(menuMessage);
    }

    @Component
    class MenuCacheScheduler {

        @Scheduled(cron = "0 0 12 ? ? THU#2 *")
        public void scheduledMenuCache() {
            for(Campus campus : Campus.values())
                MENU_CACHE.get(campus).clear();
        }
    }

}
