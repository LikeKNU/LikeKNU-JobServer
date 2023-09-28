package ac.knu.likeknujobserver.calendar.service;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import ac.knu.likeknujobserver.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknujobserver.calendar.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AcademicCalendarService {

    private final AcademicCalendarRepository academicCalendarRepository;
    private final Map<Integer, Map<Integer, Queue<AcademicCalendarMessage>>> CALENDAR_CACHE = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        int nowYear = LocalDate.now().getYear();

        if(CALENDAR_CACHE.containsKey(nowYear)) {
            Map<Integer, Queue<AcademicCalendarMessage>> messageQueue = new ConcurrentHashMap<>();
            IntStream.rangeClosed(1, 12)
                    .forEach(i -> messageQueue.put(i, new ConcurrentLinkedQueue<>()));

            CALENDAR_CACHE.put(nowYear, messageQueue);
            CALENDAR_CACHE.put(nowYear + 1, messageQueue);
        }
    }

    public void updateCalendar(AcademicCalendarMessage calendarMessage) {
        calendarMessage.checkYearRight();

        if(existCalendarMessage(calendarMessage))
            return;

        cachingCalendarMessage(calendarMessage);
        academicCalendarRepository.save(AcademicCalendar.of(calendarMessage));
    }

    private void importFromCalendarRepositoryAndCache() {

    }

    private void cachingCalendarMessage(AcademicCalendarMessage calendarMessage) {
        Queue<AcademicCalendarMessage> messageQueue = CALENDAR_CACHE
                .get(calendarMessage.getStartDate().getYear())
                .get(calendarMessage.getStartDate().getMonthValue());

        messageQueue.offer(calendarMessage);
    }

    private boolean existCalendarMessage(AcademicCalendarMessage calendarMessage) {
        return CALENDAR_CACHE
                .get(calendarMessage.getStartDate().getYear())
                .get(calendarMessage.getStartDate().getMonthValue())
                .contains(calendarMessage);
    }
}
