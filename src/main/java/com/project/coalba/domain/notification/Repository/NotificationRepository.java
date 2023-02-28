package com.project.coalba.domain.notification.Repository;

import com.project.coalba.domain.auth.entity.User;
import com.project.coalba.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    Optional<Notification> findByUser(User user);
}
