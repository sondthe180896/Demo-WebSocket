package com.spring.mvc.websocket_notification.service.impl;

import com.spring.mvc.websocket_notification.model.User;
import com.spring.mvc.websocket_notification.model.UserNotification;
import com.spring.mvc.websocket_notification.repository.UserNotificationRepository;
import com.spring.mvc.websocket_notification.service.UserNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    @Autowired
    private UserNotificationRepository repository;

    @Override
    @Transactional
    public UserNotification saveNotification(UserNotification notification) {
        notification.setTimestamp(LocalDateTime.now());
        return repository.save(notification);
    }

    @Override
    public List<UserNotification> getNotificationsForUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<UserNotification> getNotificationsForUser(User user, Pageable pageable) {
        return repository.findByUser(user, pageable);
    }

    @Override
    public long countNotificationsForUser(User user) {
        return repository.countByUser(user);
    }

    // Lấy thông báo theo loại hành động
    @Override
    public List<UserNotification> getNotificationsByAction(String action) {
        return repository.findByAction(action);
    }

}
