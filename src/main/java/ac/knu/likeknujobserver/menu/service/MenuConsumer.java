package ac.knu.likeknujobserver.menu.service;

import ac.knu.likeknujobserver.menu.dto.MenuMessage;
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
    public void consumeAnnouncementMessage(@Valid MenuMessage menuMessage) {

    }
}
