package ac.knu.likeknujobserver.citybus.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class BusArrivalTime {

    private String busName;
    private DepartureStop departureStop;
    private LocalTime arrivalTime;

    protected BusArrivalTime() {
    }

    @Builder
    public BusArrivalTime(String busName, DepartureStop departureStop, LocalTime arrivalTime) {
        this.busName = busName;
        this.departureStop = departureStop;
        this.arrivalTime = arrivalTime;
    }
}
