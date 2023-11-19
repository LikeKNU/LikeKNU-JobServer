package ac.knu.likeknujobserver.kakao;

import ac.knu.likeknujobserver.citybus.domain.CityBus;
import ac.knu.likeknujobserver.citybus.domain.Route;
import ac.knu.likeknujobserver.citybus.repository.CityBusRepository;
import ac.knu.likeknujobserver.citybus.repository.RouteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Slf4j
@SpringBootTest
public class KakaoBusListTest {

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    CityBusRepository cityBusRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void test() {
        Route route = routeRepository.findById("route_67dde868ae45423984d824bf66fa1e7e")
                .orElseThrow();

        try (Reader reader = new FileReader("src/main/resources/static/kakao-bus-list.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            KakaoBusListDto kakaoBusListDto = objectMapper.readValue(reader, KakaoBusListDto.class);
            List<Vehicle> cityBuses = kakaoBusListDto.getCityBuses();
            cityBuses.stream()
                    .map(vehicle -> CityBus.builder()
                            .busNumber(vehicle.name())
                            .busName(String.join(" ", vehicle.name1(), vehicle.name2()))
                            .isRealtime(true)
                            .busColor("95C53C")
                            .busStop(route.getDepartureStop())
                            .build())
                    .map(cityBusRepository::save)
                    .forEach(route::addCityBus);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
