package ac.knu.likeknujobserver.menu.domain;

import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Domain;
import ac.knu.likeknujobserver.menu.domain.value.MealType;
import ac.knu.likeknujobserver.menu.dto.MenuMessage;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "menu")
@Getter
public class Menu extends BaseEntity {

    @Column
    private String menus;

    @Column
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column
    private LocalDate menuDate;

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cafeteria cafeteria;

    protected Menu() {
        super(Domain.MENU);
    }

    @Builder
    public Menu(String menus, MealType mealType, LocalDate menuDate, Cafeteria cafeteria) {
        this();
        this.menus = menus;
        this.mealType = mealType;
        this.menuDate = menuDate;
        this.cafeteria = cafeteria;
    }

    public static Menu of(MenuMessage menuMessage, Cafeteria cafeteria) {
        return Menu.builder()
                .cafeteria(cafeteria)
                .menus(menuMessage.getMenus())
                .mealType(menuMessage.getMealType())
                .menuDate(menuMessage.getDate())
                .build();
    }
}
