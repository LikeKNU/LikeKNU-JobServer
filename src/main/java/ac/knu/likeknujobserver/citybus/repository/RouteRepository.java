package ac.knu.likeknujobserver.citybus.repository;

import ac.knu.likeknujobserver.citybus.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
}