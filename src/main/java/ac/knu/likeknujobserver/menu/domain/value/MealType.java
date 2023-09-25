package ac.knu.likeknujobserver.menu.domain.value;

import lombok.Getter;

@Getter
public enum MealType {

    BREAKFAST("조식", 9),
    LUNCH("중식", 14),
    DINNER("석식", 19);

    private final String mealTypeKr;
    private final int hour;

    MealType(String mealTypeKr, int hour) {
        this.mealTypeKr = mealTypeKr;
        this.hour = hour;
    }
}
