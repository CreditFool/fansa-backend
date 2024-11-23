package com.creditfool.fansa.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.creditfool.fansa.constant.ERole;
import com.creditfool.fansa.entity.Role;
import com.creditfool.fansa.repository.RoleRepository;
import com.creditfool.fansa.service.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSaveRole(ERole roleType) {
        Optional<Role> role = roleRepository.findByRoleType(roleType);
        if (role.isPresent()) {
            return role.get();
        }

        Role newRole = Role.builder()
                .roleType(roleType)
                .build();
        newRole = roleRepository.save(newRole);

        log.info("new role created: {}", newRole);
        return newRole;
    }
}
