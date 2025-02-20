package com.spring.mvc.websocket_notification.repository;

import com.spring.mvc.websocket_notification.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long userId);
}