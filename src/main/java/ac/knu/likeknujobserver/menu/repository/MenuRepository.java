package ac.knu.likeknujobserver.menu.repository;

import ac.knu.likeknujobserver.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {
}
