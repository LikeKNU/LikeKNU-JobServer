package ac.knu.likeknujobserver.calendar.service;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import ac.knu.likeknujobserver.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknujobserver.calendar.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AcademicCalendarService {

    private final AcademicCalendarRepository academicCalendarRepository;
    private final Map<Integer, AcademicCalendarMessage> CALENDAR_CACHE = new ConcurrentHashMap<>();

    public void updateCalendar(AcademicCalendarMessage calendarMessage) {
        if(!isYearRight(calendarMessage)) {

        }


        academicCalendarRepository.save(AcademicCalendar.of(calendarMessage));
    }

    private boolean isYearRight(AcademicCalendarMessage calendarMessage) {
        int now_year = LocalDate.now().getYear();
        return (calendarMessage.getStartDate().getYear() <= now_year) &&
                (calendarMessage.getEndDate().getYear() >= now_year);
    }

}
