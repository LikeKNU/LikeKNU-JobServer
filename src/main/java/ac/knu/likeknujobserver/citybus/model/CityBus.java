package ac.knu.likeknujobserver.citybus.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "city_bus")
@Entity
public class CityBus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber;

    private String busName;

    private String busColor;

    private String busStop;

    private boolean isRealtime;

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "bus_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    @ManyToMany
    private List<Route> routes;

    @CollectionTable(name = "bus_time", joinColumns = @JoinColumn(name = "bus_id"))
    @Column(name = "arrival_time")
    @ElementCollection
    private List<LocalTime> arrivalTimes = new ArrayList<>();

    public void addArrivalTime(LocalTime time) {
        arrivalTimes.add(time);
    }
}
