package ac.knu.likeknujobserver.citybus.service;

import ac.knu.likeknujobserver.citybus.dto.BusArrivalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
public class BusArrivalTimeConsumer {

    private final CityBusService cityBusService;

    public BusArrivalTimeConsumer(CityBusService cityBusService) {
        this.cityBusService = cityBusService;
    }

    @RabbitListener(queues = "${rabbitmq.bus-arrival-time-queue-name}")
    public void updateRealtimeBusArrivalTime(List<BusArrivalTime> busArrivalTimes) {
        Map<String, Map<String, List<BusArrivalTime>>> busArrivalTimeMap = busArrivalTimes.stream()
                .collect(
                        groupingBy(busArrivalTime -> busArrivalTime.getDepartureStop().getStopName(),
                                groupingBy(BusArrivalTime::getBusName))
                );
        cityBusService.updateRealtimeBusArrivalTime(busArrivalTimeMap);
    }
}
