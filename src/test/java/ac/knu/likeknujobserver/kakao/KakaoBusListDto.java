package ac.knu.likeknujobserver.kakao;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record KakaoBusListDto(@JsonAlias("in_local") KakaoBusList inLocal) {

    public List<Vehicle> getCityBuses() {
        return inLocal.routes().get(0).summaries().get(0).vehicles();
    }
}

record KakaoBusList(List<KakaoRoute> routes) {
}

record KakaoRoute(List<Summary> summaries) {
}

record Summary(List<Vehicle> vehicles) {
}

@JsonIgnoreProperties(ignoreUnknown = true)
record Vehicle(String name, String name1, String name2) {
}