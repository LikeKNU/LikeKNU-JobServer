package ac.knu.likeknujobserver.menu.domain;

import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.common.value.Domain;
import ac.knu.likeknujobserver.menu.domain.value.CafeteriaName;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "cafeteria")
@Entity
public class Cafeteria extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CafeteriaName cafeteriaName;

    @Column
    private String weekdayBreakfast;

    @Column
    private String weekdayLunch;

    @Column
    private String weekdayDinner;

    @Column
    private String weekendBreakfast;

    @Column
    private String weekendLunch;

    @Column
    private String weekendDinner;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @OneToMany(mappedBy = "cafeteria")
    private List<Menu> menus = new ArrayList<>();

    protected Cafeteria() {
        super(Domain.CAFETERIA);
    }

    @Builder
    public Cafeteria(CafeteriaName cafeteriaName, String weekdayBreakfast, String weekdayLunch, String weekdayDinner, String weekendBreakfast, String weekendLunch, String weekendDinner, Campus campus) {
        this();
        this.cafeteriaName = cafeteriaName;
        this.weekdayBreakfast = weekdayBreakfast;
        this.weekdayLunch = weekdayLunch;
        this.weekdayDinner = weekdayDinner;
        this.weekendBreakfast = weekendBreakfast;
        this.weekendLunch = weekendLunch;
        this.weekendDinner = weekendDinner;
        this.campus = campus;
    }
}
