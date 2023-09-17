package ac.knu.likeknujobserver.citybus.dto;

import lombok.Getter;

@Getter
public enum DepartureStop {

    KONGJU_ENGINEERING_UNIVERSITY("368511", "공주대공과대학"),
    DUJEONG_STATION_ENTRANCE("368516", "두정역입구"),
    CHEONAN_STATION("368238", "천안역"),
    SINGAWN_TERMINAL("362043", "종합버스터미널(신관초방면)");

    private final String stopId;
    private final String stopName;

    DepartureStop(String stopId, String stopName) {
        this.stopId = stopId;
        this.stopName = stopName;
    }
}
