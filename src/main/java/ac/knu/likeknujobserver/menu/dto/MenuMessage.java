package ac.knu.likeknujobserver.menu.dto;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.value.MealType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MenuMessage {

    private String menus;
    private LocalDate date;
    private MealType mealType;
    private Campus campus;
    private Cafeteria cafeteria;

    public MenuMessage(String menus, LocalDate date, MealType mealType, Campus campus, Cafeteria cafeteria) {
        this.menus = menus;
        this.date = date;
        this.mealType = mealType;
        this.campus = campus;
        this.cafeteria = cafeteria;
    }
}
