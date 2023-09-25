package ac.knu.likeknujobserver.menu.repository;

import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    List<Menu> findMenusByMenuDateAfterAndCafeteria(LocalDate menuDate, Cafeteria cafeteria);

}
