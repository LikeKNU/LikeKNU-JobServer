package ac.knu.likeknujobserver.menu.domain;

import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Domain;
import ac.knu.likeknujobserver.menu.domain.value.MealType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Entity
@Table(name = "menu")
@Getter
public class Menu extends BaseEntity {

    private String menus;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

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

    public void updateMenus(String menu) {
        if (StringUtils.hasText(menu)) {
            this.menus = menu;
        }
    }
}
