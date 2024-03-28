package ac.knu.likeknujobserver.menu.domain;

import ac.knu.likeknujobserver.common.BaseEntity;
import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.common.value.Domain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(name = "cafeteria")
@Entity
public class Cafeteria extends BaseEntity {

    private String cafeteriaName;

    private String weekdayBreakfast;

    private String weekdayLunch;

    private String weekdayDinner;

    private String weekendBreakfast;

    private String weekendLunch;

    private String weekendDinner;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    private Integer sequence;

    @OneToMany(mappedBy = "cafeteria")
    private List<Menu> menus = new ArrayList<>();

    protected Cafeteria() {
        super(Domain.CAFETERIA);
    }
}
