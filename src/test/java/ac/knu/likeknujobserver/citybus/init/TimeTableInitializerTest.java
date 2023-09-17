package ac.knu.likeknujobserver.citybus.init;

import ac.knu.likeknujobserver.citybus.repository.CityBusRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimeTableInitializerTest {

    @Autowired
    private TimeTableInitializer timeTableInitializer;
    @Autowired
    private CityBusRepository cityBusRepository;

    @Test
    void initialize() throws Exception {
        cityBusRepository.findAll().stream()
                .filter(cityBus -> !cityBus.isRealtime())
                .forEach(cityBus -> timeTableInitializer.initializeCheonanCityBusTime(cityBus.getId()));
    }
}