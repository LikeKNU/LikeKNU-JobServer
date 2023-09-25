package ac.knu.likeknujobserver.menu.repository;

import ac.knu.likeknujobserver.common.value.Campus;
import ac.knu.likeknujobserver.menu.domain.Cafeteria;
import ac.knu.likeknujobserver.menu.domain.value.CafeteriaName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CafeteriaRepository extends JpaRepository<Cafeteria, String> {

    Cafeteria findCafeteriaByCampusAndCafeteriaName(Campus campus, CafeteriaName cafeteriaName);
    
}
