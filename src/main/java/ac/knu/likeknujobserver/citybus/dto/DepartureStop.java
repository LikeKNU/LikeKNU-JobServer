package ac.knu.likeknujobserver.citybus.dto;

import lombok.Getter;

@Getter
public enum DepartureStop {

    KONGJU_ENGINEERING_UNIVERSITY("55020184", "공주대공과대학"),
    DUJEONG_STATION_ENTRANCE("368516", "두정역입구"),
    CHEONAN_STATION("368238", "천안역"),
    SINGWAN_TERMINAL("362043", "종합버스터미널(신관초방면)"),

    SINGWAN_TERMINAL_KAKAO("BS436082", "종합버스터미널(신관초방면)"),
    SINGWAN_TERMINAL_OCRYONG_KAKAO("BS436106", "종합버스터미널(옥룡동방면)"),
    JUGONG_APARTMENT_KAKAO("BS435938", "주공1차아파트(터미널방면)"),
    JUNGDONG_CROSSROADS_KAKAO("BS435990", "중동사거리(옥룡동방면)");

    private final String stopId;
    private final String stopName;

    DepartureStop(String stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
    }
}
