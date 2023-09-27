package ac.knu.likeknujobserver.calendar.dto;

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

    public void checkYearRight() {
        if(!isYearRight())
            modifyDateCalendarMessage();
    }

    private boolean isYearRight() {
        int now_year = LocalDate.now().getYear();
        return (startDate.getYear() <= now_year) && (endDate.getYear() >= now_year);
    }

    private void modifyDateCalendarMessage() {
        startDate = startDate.minusYears(1);
        endDate = endDate.minusYears(1);
    }
}
