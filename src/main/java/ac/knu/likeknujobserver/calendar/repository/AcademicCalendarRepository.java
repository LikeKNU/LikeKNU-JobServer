package ac.knu.likeknujobserver.calendar.repository;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, String> {

    List<AcademicCalendar> findByStartDateGreaterThanEqual(LocalDate startDate);
}
