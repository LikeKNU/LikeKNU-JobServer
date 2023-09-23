package ac.knu.likeknujobserver.notification.repository;

import ac.knu.likeknujobserver.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
}
