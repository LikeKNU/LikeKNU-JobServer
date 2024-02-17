package ac.knu.likeknujobserver.citybus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@ToString
@Getter
public class BusArrivalTimeMessage {

    @NotBlank
    private String busName;
    @NotNull
    private DepartureStop departureStop;
    @NotNull
    private LocalTime arrivalTime;

    protected BusArrivalTimeMessage() {
    }

    @Builder
    public BusArrivalTimeMessage(String busName, DepartureStop departureStop, LocalTime arrivalTime) {
        this.busName = busName;
        this.departureStop = departureStop;
        this.arrivalTime = arrivalTime;
    }
}
