package ac.knu.likeknujobserver.calendar.service;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import ac.knu.likeknujobserver.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknujobserver.calendar.repository.AcademicCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AcademicCalendarService {

    private final AcademicCalendarRepository academicCalendarRepository;

    private static final Map<Integer, Map<Integer, Set<AcademicCalendarMessage>>> CALENDAR_CACHE = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        int nowYear = LocalDate.now().getYear();

        Map<Integer, Set<AcademicCalendarMessage>> messageSet = new ConcurrentHashMap<>();
        IntStream.rangeClosed(1, 12)
                .forEach(i -> messageSet.put(i, Collections.synchronizedSet(new HashSet<>())));

        CALENDAR_CACHE.put(nowYear, messageSet);
        CALENDAR_CACHE.put(nowYear + 1, messageSet);

        importFromCalendarRepositoryAndCache();
    }

    public void updateCalendar(AcademicCalendarMessage calendarMessage) {
        if(existCalendarMessage(calendarMessage))
            return;

        cachingCalendarMessage(calendarMessage);
        academicCalendarRepository.save(AcademicCalendar.of(calendarMessage));
    }

    private void importFromCalendarRepositoryAndCache() {
        List<AcademicCalendar> academicCalendarList = academicCalendarRepository.findByStartDateGreaterThanEqual(LocalDate.now());

        academicCalendarList.forEach((AcademicCalendar ac) -> {
            AcademicCalendarMessage acm = AcademicCalendarMessage.of(ac);
            getSetFromCache(acm).add(acm);
        });
    }

    private Set<AcademicCalendarMessage> getSetFromCache(AcademicCalendarMessage calendarMessage) {
        return CALENDAR_CACHE.get(calendarMessage.getStartDate().getYear()).get(calendarMessage.getStartDate().getMonthValue());
    }

    private void cachingCalendarMessage(AcademicCalendarMessage calendarMessage) {
        Set<AcademicCalendarMessage> messageSet = getSetFromCache(calendarMessage);
        messageSet.add(calendarMessage);
    }

    private boolean existCalendarMessage(AcademicCalendarMessage calendarMessage) {
        return CALENDAR_CACHE
                .get(calendarMessage.getStartDate().getYear())
                .get(calendarMessage.getStartDate().getMonthValue())
                .contains(calendarMessage);
    }

    @Component
    static class AcademicCalendarCacheScheduler {

        /**
         * 매년 1월 27일 12시 캐시 초기화
         */
        @Scheduled(cron = "0 0 12 27 1 *")
        public void scheduledCalendarCache() {
            CALENDAR_CACHE.remove(LocalDate.now().getYear());
        }
    }
}
