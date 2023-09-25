package ac.knu.likeknujobserver.menu.domain.value;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.dto.MenuMessage;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum CacheCamCafe {

    SINGWAN_STUDENT(Campus.SINGWAN, CafeteriaName.STUDENT_CAFETERIA),
    SINGWAN_EMPLOYEE(Campus.SINGWAN, CafeteriaName.EMPLOYEE_CAFETERIA),
    SINGWAN_EUN_VIS(Campus.SINGWAN, CafeteriaName.EUNHAENGSA_VISION),
    SINGWAN_DREAM(Campus.SINGWAN, CafeteriaName.DREAM),
    CHEONAN_STUDENT(Campus.CHEONAN, CafeteriaName.STUDENT_CAFETERIA),
    CHEONAN_EMPLOYEE(Campus.CHEONAN, CafeteriaName.EMPLOYEE_CAFETERIA),
    CHEONAN_DORM(Campus.CHEONAN, CafeteriaName.DORMITORY),
    YESAN_STUDENT(Campus.YESAN, CafeteriaName.STUDENT_CAFETERIA),
    YESAN_EMPLOYEE(Campus.YESAN, CafeteriaName.EMPLOYEE_CAFETERIA);

    private final Campus campus;
    private final CafeteriaName cafeteriaName;

    CacheCamCafe(Campus campus, CafeteriaName cafeteriaName) {
        this.campus = campus;
        this.cafeteriaName = cafeteriaName;
    }

    public static CacheCamCafe of(MenuMessage menuMessage) {
        Campus campus = menuMessage.getCampus();
        CafeteriaName cafeteriaName = menuMessage.getCafeteria();

        return Stream.of(CacheCamCafe.values())
                .filter(c -> c.getCampus().equals(campus) && c.cafeteriaName.equals(cafeteriaName))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
