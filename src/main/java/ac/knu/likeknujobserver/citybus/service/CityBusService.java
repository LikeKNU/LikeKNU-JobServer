package ac.knu.likeknujobserver.citybus.service;

import ac.knu.likeknujobserver.citybus.dto.BusArrivalTime;
import ac.knu.likeknujobserver.citybus.repository.CityBusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CityBusService {

    private final CityBusRepository cityBusRepository;

    public CityBusService(CityBusRepository cityBusRepository) {
        this.cityBusRepository = cityBusRepository;
    }

    public void updateRealtimeBusArrivalTime(Map<String, Map<String, List<BusArrivalTime>>> busArrivalTimeMap) {
    }
}
