package ac.knu.likeknujobserver.citybus.domain;

import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Domain;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "city_bus")
@Entity
public class CityBus extends BaseEntity {

    @Column(nullable = false)
    private String busNumber;

    private String busName;

    private String busColor;

    @Column(nullable = false)
    private String busStop;

    private Boolean isRealtime;

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "bus_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    @ManyToMany
    private List<Route> routes;

    @CollectionTable(name = "bus_time", joinColumns = @JoinColumn(name = "bus_id"))
    @Column(name = "arrival_time")
    @ElementCollection
    private List<LocalTime> arrivalTimes = new ArrayList<>();

    protected CityBus() {
        super(Domain.CITY_BUS);
    }

    @Builder
    public CityBus(String busNumber, String busName, String busColor, String busStop, boolean isRealtime) {
        this();
        this.busNumber = busNumber;
        this.busName = busName;
        this.busColor = busColor;
        this.busStop = busStop;
        this.isRealtime = isRealtime;
    }

    public void addArrivalTime(LocalTime arrivalTime) {
        arrivalTimes.add(arrivalTime);
    }

    public void updateArrivalTimes(List<LocalTime> arrivalTimes) {
        this.arrivalTimes.clear();
        this.arrivalTimes.addAll(arrivalTimes);
    }
}
