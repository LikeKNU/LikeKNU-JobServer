package ac.knu.likeknujobserver.citybus.service;

import ac.knu.likeknujobserver.citybus.dto.BusArrivalTime;
import ac.knu.likeknujobserver.citybus.repository.CityBusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Transactional(readOnly = true)
@Service
public class CityBusService {

    private final CityBusRepository cityBusRepository;

    public CityBusService(CityBusRepository cityBusRepository) {
        this.cityBusRepository = cityBusRepository;
    }

    @Transactional
    public void updateRealtimeBusArrivalTime(List<BusArrivalTime> busArrivalTimes) {
        Map<String, Map<String, List<BusArrivalTime>>> busArrivalTimeMap = groupingByDepartureStopAndBusName(busArrivalTimes);

        cityBusRepository.findByRealtimeIsTrue().forEach(cityBus -> {
            String busStop = cityBus.getBusStop();
            String busName = cityBus.getBusName();
            Map<String, List<BusArrivalTime>> eachBusArrivalTimeMap = busArrivalTimeMap.get(busStop);

            if (eachBusArrivalTimeMap != null && busArrivalTimeMap.get(busStop).containsKey(busName)) {
                List<LocalTime> arrivalTimes = eachBusArrivalTimeMap.get(busName).stream()
                        .map(BusArrivalTime::getArrivalTime)
                        .toList();
                cityBus.updateArrivalTimes(arrivalTimes);
            }
        });
    }

    private Map<String, Map<String, List<BusArrivalTime>>> groupingByDepartureStopAndBusName(List<BusArrivalTime> busArrivalTimes) {
        return busArrivalTimes.stream()
                .collect(
                        groupingBy(busArrivalTime -> busArrivalTime.getDepartureStop().getStopName(),
                                groupingBy(BusArrivalTime::getBusName))
                );
    }
}
