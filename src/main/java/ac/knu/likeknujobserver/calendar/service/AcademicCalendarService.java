package ac.knu.likeknujobserver.calendar.service;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import ac.knu.likeknujobserver.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknujobserver.calendar.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AcademicCalendarService {

    private final AcademicCalendarRepository academicCalendarRepository;
    private final Map<Integer, Map<Integer, Queue<AcademicCalendarMessage>>> CALENDAR_CACHE = new ConcurrentHashMap<>();

    public void updateCalendar(AcademicCalendarMessage calendarMessage) {
        calendarMessage.checkYearRight();

        if(existCalendarMessage(calendarMessage))
            return;

        cachingCalendarMessage(calendarMessage);
        academicCalendarRepository.save(AcademicCalendar.of(calendarMessage));
    }

    private void cachingCalendarMessage(AcademicCalendarMessage calendarMessage) {
        Queue<AcademicCalendarMessage> messageQueue = CALENDAR_CACHE
                .get(calendarMessage.getStartDate().getYear())
                .get(calendarMessage.getStartDate().getMonthValue());

        messageQueue.offer(calendarMessage);
    }

    private boolean existCalendarMessage(AcademicCalendarMessage calendarMessage) {
        return CALENDAR_CACHE.get(calendarMessage.getStartDate().getYear()).containsValue(calendarMessage);
    }
}
