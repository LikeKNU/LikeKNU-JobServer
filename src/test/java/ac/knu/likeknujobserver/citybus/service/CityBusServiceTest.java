package ac.knu.likeknujobserver.citybus.service;

import ac.knu.likeknujobserver.citybus.domain.CityBus;
import ac.knu.likeknujobserver.citybus.dto.BusArrivalTimeMessage;
import ac.knu.likeknujobserver.citybus.dto.DepartureStop;
import ac.knu.likeknujobserver.citybus.repository.CityBusRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@DisplayName("실시간 버스 도착 시간 테스트")
@ExtendWith(value = MockitoExtension.class)
class CityBusServiceTest {

    @InjectMocks
    private CityBusService cityBusService;

    @Mock
    private CityBusRepository cityBusRepository;

    @DisplayName("실시간 버스 도착 시간 업데이트를 진행한다.")
    @Test
    void updateRealtimeBusArrivalTime() throws Exception {
        // given                                              
        List<BusArrivalTimeMessage> busArrivalTimes = List.of(
                BusArrivalTimeMessage.builder().departureStop(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY).busName("100").arrivalTime(LocalTime.of(9, 0)).build(),
                BusArrivalTimeMessage.builder().departureStop(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY).busName("100").arrivalTime(LocalTime.of(9, 10)).build(),
                BusArrivalTimeMessage.builder().departureStop(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY).busName("200").arrivalTime(LocalTime.of(9, 3)).build(),
                BusArrivalTimeMessage.builder().departureStop(DepartureStop.DUJEONG_STATION_ENTRANCE).busName("100").arrivalTime(LocalTime.of(10, 0)).build(),
                BusArrivalTimeMessage.builder().departureStop(DepartureStop.DUJEONG_STATION_ENTRANCE).busName("100").arrivalTime(LocalTime.of(10, 10)).build()
        );

        List<CityBus> cityBuses = List.of(
                CityBus.builder().busName("100").busStop(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY.getStopName()).build(),
                CityBus.builder().busName("200").busStop(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY.getStopName()).build(),
                CityBus.builder().busName("100").busStop(DepartureStop.DUJEONG_STATION_ENTRANCE.getStopName()).build(),
                CityBus.builder().busName("200").busStop(DepartureStop.DUJEONG_STATION_ENTRANCE.getStopName()).build()
        );
        Map<String, List<CityBus>> cityBusesMap = cityBuses.stream()
                .collect(Collectors.groupingBy(CityBus::getBusStop));

        // when
        when(cityBusRepository.findByBusStop(eq(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY.getStopName())))
                .thenReturn(cityBusesMap.get(DepartureStop.KONGJU_ENGINEERING_UNIVERSITY.getStopName()));
        when(cityBusRepository.findByBusStop(eq(DepartureStop.DUJEONG_STATION_ENTRANCE.getStopName())))
                .thenReturn(cityBusesMap.get(DepartureStop.DUJEONG_STATION_ENTRANCE.getStopName()));

        cityBusService.updateRealtimeBusArrivalTime(busArrivalTimes);

        // then
        CityBus cityBus = cityBuses.get(0);
        assertAll(
                () -> assertThat(cityBus).isNotNull(),
                () -> assertThat(cityBus.getArrivalTimes()).isNotEmpty(),
                () -> assertThat(cityBus.getArrivalTimes()).hasSize(2),
                () -> assertThat(cityBus.getArrivalTimes()).contains(LocalTime.of(9, 0))
        );
    }
}