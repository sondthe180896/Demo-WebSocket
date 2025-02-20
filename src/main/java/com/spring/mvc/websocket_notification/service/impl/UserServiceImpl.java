package com.spring.mvc.websocket_notification.service.impl;

import com.spring.mvc.websocket_notification.model.User;
import com.spring.mvc.websocket_notification.model.UserNotification;
import com.spring.mvc.websocket_notification.repository.UserRepository;
import com.spring.mvc.websocket_notification.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserNotificationServiceImpl userNotificationService;

    @Override
    public List<User> getAll(){
        try {

            return userRepository.findAll();
        } catch (Exception e){

            return userRepository.findAll();
        }
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User savedUser = userRepository.save(user);

        try {
            UserNotification notification = new UserNotification("CREATE", savedUser);
            userNotificationService.saveNotification(notification);
            // Log chi tiết để debug
            logger.info("Sending WebSocket notification: {}", notification);
            messagingTemplate.convertAndSend("/topic/users", notification);
        } catch (Exception e) {
            logger.error("Error sending WebSocket message", e);
        }

        return savedUser;
    }

    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        User updatedUser = userRepository.save(user);

        // Gửi thông báo WebSocket
        UserNotification notification = new UserNotification("UPDATE", updatedUser);
        userNotificationService.saveNotification(notification);
        messagingTemplate.convertAndSend("/topic/users", notification);

        return updatedUser;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);

        // Gửi thông báo WebSocket
        UserNotification notification = new UserNotification("DELETE", user);
        userNotificationService.saveNotification(notification);
        messagingTemplate.convertAndSend("/topic/users", notification);
    }
}