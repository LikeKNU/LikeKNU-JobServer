package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.menu.dto.MealMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuConsumer {

    private final MenuService menuService;

    @RabbitListener(queues = "${rabbitmq.menu-queue-name}")
    public void consumeMenuMessage(@Valid MealMessage mealMessage) {
        menuService.updateMenus(mealMessage);
    }
}
