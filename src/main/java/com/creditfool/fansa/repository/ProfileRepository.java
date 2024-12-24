package com.creditfool.fansa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.creditfool.fansa.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUserUid(UUID uid);

    Optional<Profile> findByUserUsernameAndDeletedAtIsNull(String username);
}
