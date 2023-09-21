package ac.knu.likeknujobserver.citybus.domain;

import ac.knu.likeknujobserver.citybus.domain.value.RouteType;
import ac.knu.likeknujobserver.common.EntityGraphNames;
import ac.knu.likeknujobserver.common.value.Campus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@NamedEntityGraphs(
        value = {
                @NamedEntityGraph(
                        name = EntityGraphNames.ROUTE_BUSES,
                        attributeNodes = @NamedAttributeNode(value = "buses")
                )
        }
)
@Table(name = "route")
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RouteType routeType;

    private String departureStop;

    private String arrivalStop;

    private String origin;

    private String destination;

    @Enumerated(value = EnumType.STRING)
    private Campus campus;

    @JoinTable(name = "bus_route",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id"))
    @ManyToMany
    private List<CityBus> buses;

    protected Route() {
    }

    @Builder
    protected Route(RouteType routeType, String departureStop, String arrivalStop, String origin, String destination, Campus campus) {
        this.routeType = routeType;
        this.departureStop = departureStop;
        this.arrivalStop = arrivalStop;
        this.origin = origin;
        this.destination = destination;
        this.campus = campus;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Route route = (Route) object;

        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
