package com.creditfool.fansa.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.creditfool.fansa.constant.Message;
import com.creditfool.fansa.entity.User;
import com.creditfool.fansa.repository.UserRepository;
import com.creditfool.fansa.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static boolean isEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        return Pattern.matches(emailRegex, email.toUpperCase());
    }

    @Override
    public User getUserByUid(UUID uid) {
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException(Message.USER_NOT_FOUND);
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        emailOrUsername = emailOrUsername.trim();
        if (isEmail(emailOrUsername)) {
            Optional<User> user = userRepository.findByEmailIgnoreCaseAndDeletedAtIsNull(emailOrUsername);
            if (user.isPresent()) {
                return user.get();
            }
        } else {
            Optional<User> user = userRepository.findByUsernameAndDeletedAtIsNull(emailOrUsername);
            if (user.isPresent()) {
                return user.get();
            }
        }
        throw new UsernameNotFoundException(Message.USER_NOT_FOUND);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean isEmailAndUsernameAvailable(String email, String username) {
        List<User> users = userRepository.findAllByActiveEmailAndUsername(email, username);
        return users.isEmpty();
    }

}
