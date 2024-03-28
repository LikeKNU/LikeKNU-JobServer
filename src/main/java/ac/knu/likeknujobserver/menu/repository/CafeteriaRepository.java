package ac.knu.likeknujobserver.menu.repository;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, String> {

    Optional<Cafeteria> findByCampusAndCafeteriaName(Campus campus, String cafeteria);
}
