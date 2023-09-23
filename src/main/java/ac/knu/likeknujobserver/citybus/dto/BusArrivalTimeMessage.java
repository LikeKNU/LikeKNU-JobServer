package ac.knu.likeknujobserver.citybus.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class BusArrivalTimeMessage {

    private String busName;
    private DepartureStop departureStop;
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
