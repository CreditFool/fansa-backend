package com.creditfool.fansa.service;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.creditfool.fansa.entity.User;

public interface UserService extends UserDetailsService {
    User getUserByUid(UUID uid);

    User createUser(User user);

    boolean isEmailAndUsernameAvailable(String email, String username);
}
