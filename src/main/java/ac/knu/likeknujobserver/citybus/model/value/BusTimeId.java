package ac.knu.likeknujobserver.citybus.model.value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class BusTimeId implements Serializable {

    private Long cityBus;
    private LocalDateTime arrivalTime;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        BusTimeId busTimeId = (BusTimeId) object;

        if (!Objects.equals(cityBus, busTimeId.cityBus)) return false;
        return Objects.equals(arrivalTime, busTimeId.arrivalTime);
    }

    @Override
    public int hashCode() {
        int result = cityBus != null ? cityBus.hashCode() : 0;
        result = 31 * result + (arrivalTime != null ? arrivalTime.hashCode() : 0);
        return result;
    }
}
