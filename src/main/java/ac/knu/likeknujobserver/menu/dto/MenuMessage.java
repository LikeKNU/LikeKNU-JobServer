package ac.knu.likeknujobserver.menu.dto;

import ac.knu.likeknujobserver.menu.domain.Menu;

import java.time.LocalDate;

public record MenuMessage(LocalDate date, String menu) {

    public static MenuMessage from(Menu menu) {
        return new MenuMessage(menu.getMenuDate(), menu.getMenus());
    }
}
