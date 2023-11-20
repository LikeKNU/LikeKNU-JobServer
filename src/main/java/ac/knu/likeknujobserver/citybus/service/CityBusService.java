package ac.knu.likeknujobserver.citybus.service;

import ac.knu.likeknujobserver.citybus.dto.BusArrivalTimeMessage;
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
    public void updateRealtimeBusArrivalTime(List<BusArrivalTimeMessage> busArrivalTimes) {
        Map<String, Map<String, List<BusArrivalTimeMessage>>> busArrivalTimeMap = groupingByDepartureStopAndBusName(busArrivalTimes);

        cityBusRepository.findByIsRealtimeIsTrue()
                .forEach(cityBus -> {
                    String busStop = cityBus.getBusStop();
                    String busName = cityBus.getBusName();
                    Map<String, List<BusArrivalTimeMessage>> eachBusArrivalTimeMap = busArrivalTimeMap.get(busStop);

                    if (eachBusArrivalTimeMap != null && busArrivalTimeMap.get(busStop).containsKey(busName)) {
                        List<LocalTime> arrivalTimes = eachBusArrivalTimeMap.get(busName)
                                .stream()
                                .map(BusArrivalTimeMessage::getArrivalTime)
                                .toList();
                        cityBus.updateArrivalTimes(arrivalTimes);
                    }
                });
    }

    private Map<String, Map<String, List<BusArrivalTimeMessage>>> groupingByDepartureStopAndBusName(List<BusArrivalTimeMessage> busArrivalTimes) {
        return busArrivalTimes.stream()
                .collect(
                        groupingBy(busArrivalTime -> busArrivalTime.getDepartureStop().getStopName(),
                                groupingBy(BusArrivalTimeMessage::getBusName))
                );
    }
}
