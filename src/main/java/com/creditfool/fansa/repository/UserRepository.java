package com.creditfool.fansa.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.creditfool.fansa.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUid(UUID uid);

    Optional<User> findByUsernameAndDeletedAtIsNull(String username);

    Optional<User> findByEmailIgnoreCaseAndDeletedAtIsNull(String username);

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND (u.email = ?1 OR u.username = ?2)")
    List<User> findAllByActiveEmailAndUsername(String email, String username);

}
