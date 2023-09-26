package ac.knu.likeknujobserver.calendar.repository;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicCalendarRepository extends JpaRepository<AcademicCalendar, String> {
}
