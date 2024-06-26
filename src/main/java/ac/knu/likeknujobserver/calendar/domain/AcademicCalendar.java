package ac.knu.likeknujobserver.calendar.domain;

import ac.knu.likeknujobserver.calendar.dto.AcademicCalendarMessage;
import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "academic_calendar")
@Getter
public class AcademicCalendar extends BaseEntity {

    @Column
    private String contents;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    protected AcademicCalendar() {
        super(Domain.ACADEMIC_CALENDAR);
    }

    @Builder
    public AcademicCalendar(String contents, LocalDate startDate, LocalDate endDate) {
        this();
        this.contents = contents;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static AcademicCalendar of(AcademicCalendarMessage academicCalendarMessage) {
        return AcademicCalendar.builder()
                .contents(academicCalendarMessage.getContents())
                .startDate(academicCalendarMessage.getStartDate())
                .endDate(academicCalendarMessage.getEndDate())
                .build();
    }
}
