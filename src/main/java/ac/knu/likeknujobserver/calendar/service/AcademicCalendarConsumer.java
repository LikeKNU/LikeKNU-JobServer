package ac.knu.likeknujobserver.calendar.service;

import ac.knu.likeknujobserver.calendar.dto.AcademicCalendarMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcademicCalendarConsumer {

    private final AcademicCalendarService academicCalendarService;

    @RabbitListener(queues = "${rabbitmq.calendar-queue-name}")
    public void consumeAcademicCalendarMessage(@Valid AcademicCalendarMessage academicCalendarMessage) {
    }
}
