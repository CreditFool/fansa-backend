package com.creditfool.fansa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditfool.fansa.constant.ERole;
import com.creditfool.fansa.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(ERole roleType);
}
