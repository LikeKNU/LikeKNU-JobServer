package ac.knu.likeknujobserver.citybus.model;

import ac.knu.likeknujobserver.citybus.model.value.BusTimeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@IdClass(BusTimeId.class)
@Table(name = "bus_time")
@Entity
public class BusTime {

    @Id
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private CityBus cityBus;

    @Id
    private LocalDateTime arrivalTime;
}
