package ac.knu.likeknujobserver.citybus.service;

import ac.knu.likeknujobserver.citybus.dto.BusArrivalTimeMessage;
import jakarta.validation.Valid;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusArrivalTimeConsumer {

    private final CityBusService cityBusService;

    public BusArrivalTimeConsumer(CityBusService cityBusService) {
        this.cityBusService = cityBusService;
    }

    @RabbitListener(queues = "${rabbitmq.bus-arrival-time-queue-name}")
    public void consumeBusArrivalTimeMessage(@Valid List<BusArrivalTimeMessage> busArrivalTimes) {
        cityBusService.updateRealtimeBusArrivalTime(busArrivalTimes);
    }
}
