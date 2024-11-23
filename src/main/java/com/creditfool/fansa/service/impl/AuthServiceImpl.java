package com.creditfool.fansa.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.creditfool.fansa.config.InitAdminProperties;
import com.creditfool.fansa.constant.ERole;
import com.creditfool.fansa.constant.Message;
import com.creditfool.fansa.entity.Role;
import com.creditfool.fansa.entity.User;
import com.creditfool.fansa.model.request.LoginRequest;
import com.creditfool.fansa.model.request.RegisterRequest;
import com.creditfool.fansa.model.response.LoginResponse;
import com.creditfool.fansa.service.AuthService;
import com.creditfool.fansa.service.RoleService;
import com.creditfool.fansa.service.UserService;
import com.creditfool.fansa.util.JwtUtil;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final RoleService roleService;

    private final InitAdminProperties initAdminProperties;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private List<String> getRoleList(User user) {
        return user.getRoles().stream()
                .map(role -> role.getRoleType().name().replace("ROLE_", "").toLowerCase())
                .toList();
    }

    @PostConstruct
    private void createInitAdmin() {

        if (!userService.isEmailAndUsernameAvailable(
                initAdminProperties.getEmail(),
                initAdminProperties.getUsername())) {

            return;
        }

        Role superAdminRole = roleService.getOrSaveRole(ERole.ROLE_SUPER_ADMIN);
        Role adminRole = roleService.getOrSaveRole(ERole.ROLE_ADMIN);
        Role userRole = roleService.getOrSaveRole(ERole.ROLE_USER);

        User user = User.builder()
                .uid(UUID.randomUUID())
                .email(initAdminProperties.getEmail())
                .username(initAdminProperties.getUsername())
                .password(passwordEncoder.encode(initAdminProperties.getPassword()))
                .roles(List.of(superAdminRole, adminRole, userRole))
                .build();
        user = userService.createUser(user);
        log.info("admin created: {}", user.getUid().toString());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.emailOrUsername(),
                            request.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String token = jwtUtil.generateToken(user);

            log.info("user login: {}", user.getUid().toString());
            return new LoginResponse(
                    token,
                    user.getUid().toString(),
                    user.getUsername(),
                    getRoleList(user));

        } catch (BadCredentialsException e) {
            log.info("login failed: %s", request.emailOrUsername());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Message.INVALID_CREDENTIAL);

        } catch (Exception e) {
            log.error("login error: ", e.getCause());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest request) {
        if (!userService.isEmailAndUsernameAvailable(request.email(), request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Message.USERNAME_OR_EMAIL_ALREADY_EXIST);
        }

        Role userRole = roleService.getOrSaveRole(ERole.ROLE_USER);
        User user = User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .uid(UUID.randomUUID())
                .roles(List.of(userRole))
                .build();
        user = userService.createUser(user);
        log.info("user registered: {}", user.getUid().toString());
    }

}
