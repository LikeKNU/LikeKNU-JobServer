package ac.knu.likeknujobserver.citybus.domain;

import ac.knu.likeknujobserver.citybus.domain.value.RouteType;
import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.common.value.Domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "route")
@Entity
public class Route extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RouteType routeType;

    @Column(nullable = false)
    private String departureStop;

    @Column(nullable = false)
    private String arrivalStop;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Campus campus;

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id"))
    @ManyToMany
    private List<CityBus> buses = new ArrayList<>();

    protected Route() {
        super(Domain.ROUTE);
    }

    @Builder
    protected Route(RouteType routeType, String departureStop, String arrivalStop, String origin, String destination, Campus campus) {
        this();
        this.routeType = routeType;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.origin = origin;
        this.destination = destination;
        this.campus = campus;
    }

    public void addCityBus(CityBus cityBus) {
        buses.add(cityBus);
    }
}
