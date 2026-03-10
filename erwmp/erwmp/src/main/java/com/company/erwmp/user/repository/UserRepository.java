package com.company.erwmp.user.repository;

import com.company.erwmp.user.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    List<UserEntity> findByIsDeletedFalse();   //Give me ALL users where isDeleted = false
    Optional<UserEntity> findByIdAndIsDeletedFalse(Long id); //Find a user by ID, only if they are not deleted
    Optional<UserEntity> findByUsernameAndIsDeletedFalse(String username);   //Find a user by username, only if the account is active.
    Page<UserEntity> findByIsDeletedFalse(Pageable pageable);  //Find if the user is not deleted
    Page<UserEntity> findByUsernameContainingAndIsDeletedFalse(String username, Pageable pageable);  //give the users in batches not once
    Page<User> findByDeletedFalseAndUsernameContainingIgnoreCase(String search, Pageable pageable);

}
