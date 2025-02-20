package com.spring.mvc.websocket_notification.service;

import com.spring.mvc.websocket_notification.model.User;
import com.spring.mvc.websocket_notification.model.UserNotification;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserNotificationService {
    public UserNotification saveNotification(UserNotification notification);
    public List<UserNotification> getNotificationsForUser(User user);
    public List<UserNotification> getNotificationsForUser(User user, Pageable pageable);
    public long countNotificationsForUser(User user);
    public List<UserNotification> getNotificationsByAction(String action);
}
