package ac.knu.likeknujobserver.menu.repository;

import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.Menu;
import ac.knu.likeknujobserver.menu.domain.value.MealType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    @EntityGraph(attributePaths = {"cafeteria"})
    List<Menu> findMenusByMenuDateAfterAndCafeteria(LocalDate menuDate, Cafeteria cafeteria);

    @EntityGraph(attributePaths = {"cafeteria"})
    Optional<Menu> findByCafeteriaAndMenuDateAndMealType(Cafeteria cafeteria, LocalDate menuDate, MealType mealType);

}
