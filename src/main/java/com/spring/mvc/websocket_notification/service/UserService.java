package com.spring.mvc.websocket_notification.service;

import com.spring.mvc.websocket_notification.model.User;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public List<User> getAll();
    public User updateUser(Long id, User userDetails);
    public void deleteUser(Long id);
}
