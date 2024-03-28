package ac.knu.likeknujobserver.menu.domain;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.dto.MealMessage;

public record CafeteriaKey(Campus campus, String cafeteria) {

    public static CafeteriaKey from(Cafeteria cafeteria) {
        return new CafeteriaKey(cafeteria.getCampus(), cafeteria.getCafeteriaName());
    }

    public static CafeteriaKey from(MealMessage mealMessage) {
        return new CafeteriaKey(mealMessage.campus(), mealMessage.cafeteria());
    }
}
