/*
package ac.knu.likeknujobserver.citybus.init;

import ac.knu.likeknujobserver.citybus.model.CityBus;
import ac.knu.likeknujobserver.citybus.repository.CityBusRepository;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class TimeTableInitializer {

    private static final RestTemplate REST_TEMPLATE = new RestTemplateBuilder().build();

    private final CityBusRepository cityBusRepository;

    public TimeTableInitializer(CityBusRepository cityBusRepository) {
        this.cityBusRepository = cityBusRepository;
    }

    @Transactional
    public void initializeCheonanCityBusTime(Long cityBusId) {
        CityBus cityBus = cityBusRepository.findById(cityBusId)
                .orElseThrow(IllegalArgumentException::new);

        String uri = UriComponentsBuilder.fromUriString("https://its.cheonan.go.kr/bis/showBusTimeTable.do")
                .queryParam("routeName", cityBus.getBusNumber())
                .queryParam("routeDirection", 1)
                .queryParam("relayAreacode")
                .queryParam("routeExplain")
                .queryParam("stName")
                .queryParam("edName")
                .queryParam("fstTime")
                .queryParam("lstTime")
                .queryParam("routeDLength")
                .queryParam("avgTime")
                .build().toUriString();
        String pageSource = REST_TEMPLATE.getForObject(uri, String.class);
        Document document = Jsoup.parse(pageSource);
        Element timeTable = document.getElementsByClass("timeTable").get(0);
        Elements timeElements = timeTable.getElementsByTag("dd");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        for (Element timeElement : timeElements) {
            String text = timeElement.text();
            if (text.contains("(")) {
                text = text.split("\\(")[0];
            }
            LocalTime time = LocalTime.parse(text, dateTimeFormatter).plusMinutes(13);
            cityBus.addArrivalTime(time);
        }
    }
}
*/
