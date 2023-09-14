package ac.knu.likeknujobserver.common.value;

import lombok.Getter;

@Getter
public enum Campus {

    SINGWAN("신관"), CHEONAN("천안"), YESAN("예산");

    private final String campusName;

    Campus(String campusName) {
        this.campusName = campusName;
    }
}
