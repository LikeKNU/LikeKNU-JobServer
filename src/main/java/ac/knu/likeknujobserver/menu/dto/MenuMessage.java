package ac.knu.likeknujobserver.menu.dto;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.domain.value.CafeteriaName;
import ac.knu.likeknujobserver.menu.domain.value.MealType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MenuMessage {

    private String menus;

    @NotBlank
    private LocalDate date;

    @NotBlank
    private MealType mealType;

    @NotBlank
    private Campus campus;

    @NotBlank
    private CafeteriaName cafeteria;

    @Builder
    public MenuMessage(String menus, LocalDate date, MealType mealType, Campus campus, CafeteriaName cafeteria) {
        this.menus = menus;
        this.date = date;
        this.mealType = mealType;
        this.campus = campus;
        this.cafeteria = cafeteria;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }

    public static MenuMessage of(Menu menu) {
        return MenuMessage.builder()
                .menus(menu.getMenus())
                .mealType(menu.getMealType())
                .campus(menu.getCafeteria().getCampus())
                .cafeteria(menu.getCafeteria().getCafeteriaName())
                .date(menu.getMenuDate())
                .build();
    }
}
