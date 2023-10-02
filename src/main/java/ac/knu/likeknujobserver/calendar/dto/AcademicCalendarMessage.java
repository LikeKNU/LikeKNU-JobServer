package ac.knu.likeknujobserver.calendar.dto;

import ac.knu.likeknujobserver.calendar.domain.AcademicCalendar;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AcademicCalendarMessage {

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private String contents;

    @Builder
    public AcademicCalendarMessage(LocalDate startDate, LocalDate endDate, String contents) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.contents = contents;
    }

    public static AcademicCalendarMessage of(AcademicCalendar academicCalendar) {
        return AcademicCalendarMessage.builder()
                .startDate(academicCalendar.getStartDate())
                .endDate(academicCalendar.getEndDate())
                .contents(academicCalendar.getContents())
                .build();
    }
}
