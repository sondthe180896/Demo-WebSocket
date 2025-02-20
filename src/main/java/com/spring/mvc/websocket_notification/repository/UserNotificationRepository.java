package com.spring.mvc.websocket_notification.repository;

import com.spring.mvc.websocket_notification.model.User;
import com.spring.mvc.websocket_notification.model.UserNotification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findByUser(User user);
    List<UserNotification> findByAction(String action);
    List<UserNotification> findByUser(User user, Pageable pageable);
    long countByUser(User user);
}
