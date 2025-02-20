package com.spring.mvc.websocket_notification.controller;

import com.spring.mvc.websocket_notification.model.User;

import com.spring.mvc.websocket_notification.model.UserNotification;
import com.spring.mvc.websocket_notification.repository.UserRepository;
import com.spring.mvc.websocket_notification.service.impl.UserNotificationServiceImpl;
import com.spring.mvc.websocket_notification.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserNotificationServiceImpl userNotificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> userList = userService.getAll();
        return ResponseEntity.ok(userList);
    }

    // Endpoint tạo User
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // Endpoint cập nhật User
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails
    ) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint xóa User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifications")
    public UserNotification createNotification(@RequestBody UserNotification notification) {
        return userNotificationService.saveNotification(notification);
    }

    @GetMapping("/notification")
    public UserNotification getNotification(@RequestParam Long userId){
        return (UserNotification) userNotificationService.getNotificationsForUser(userRepository.findUserById(userId));
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<List<UserNotification>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Sắp xếp theo thời gian giảm dần (mới nhất lên trước)
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        List<UserNotification> notifications = userNotificationService.getNotificationsForUser(user, pageable);

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{userId}/notifications/count")
    public ResponseEntity<Long> countUserNotifications(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long count = userNotificationService.countNotificationsForUser(user);
        return ResponseEntity.ok(count);
    }

}