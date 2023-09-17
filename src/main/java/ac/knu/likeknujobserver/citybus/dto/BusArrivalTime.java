package ac.knu.likeknujobserver.citybus.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;

@ToString
@Getter
public class BusArrivalTime {

    private Long id;
    private String busNumber;
    private String busName;
    private DepartureStop departureStop;
    private LocalTime arrivalTime;
    private int remainingStop;
}
