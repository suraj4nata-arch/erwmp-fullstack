package com.company.erwmp.user.service;

import com.company.erwmp.role.entity.RoleEntity;
import com.company.erwmp.role.repository.RoleRepository;
import com.company.erwmp.user.dto.CreateUserRequest;
import com.company.erwmp.user.dto.LoginResponse;
import com.company.erwmp.user.dto.UserResponse;
import com.company.erwmp.user.dto.UserUpdateRequest;
import com.company.erwmp.user.entity.UserEntity;
import com.company.erwmp.user.mapper.UserMapper;
import com.company.erwmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.company.erwmp.Security.JwtUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private KafkaProducer kafkaProducer;

    @CacheEvict(value = "users", allEntries = true)   //Clear the "users" cache when a new user is created.
    public UserEntity createUser(CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        RoleEntity userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(hashedPassword)
                .active(true)
                .createdAt(LocalDateTime.now())
                .roles(Set.of(userRole))
                .build();
        kafkaProducer.sendMessage("New user created: " + user.getUsername());
        System.out.println("Message sent to Kafka");
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {    //method to delete the soft users

        UserEntity user = userRepository.findByIdAndIsDeletedFalse(userId)  //by the user by id and user is not already delted
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setIsDeleted(true);
        userRepository.save(user);
    }
    @Cacheable("users")
    @PreAuthorize("hasAuthority('USER_VIEW')")
    public UserResponse getUser(Long id) {
        log.info("Fetching user with id={}", id);  //log.info("User id = {}", id);

        UserEntity user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> {
                    log.warn("User not found with id={}", id);
                    return new IllegalArgumentException("User not found");
                });

        log.debug("User entity loaded: {}", user.getUsername());
        System.out.println("Fetching user from DATABASE...");
        return UserMapper.toDto(user);
    }

    public Page<UserResponse> getUsers(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<UserEntity> usersPage = userRepository.findByIsDeletedFalse(pageable);

        return usersPage.map(UserMapper::toDto); //Take a page of users from the database, convert each user into a DTO (API-friendly format), and return that page to the client
    }

    public Page<UserEntity> searchUsers(String keyword, Pageable pageable) {
        return userRepository.findByUsernameContainingAndIsDeletedFalse(keyword, (org.springframework.data.domain.Pageable) pageable);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findByIsDeletedFalse();
    }



    public LoginResponse login(String username, String rawPassword) {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String token = jwtUtils.generateToken(user.getUsername());

        return new LoginResponse("Login successful", token);
    }


    public UserEntity updateUser(Long id, UserUpdateRequest updateRequest) {

        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setUsername(updateRequest.getUsername());
        existingUser.setEmail(updateRequest.getEmail());
        return userRepository.save(existingUser);
    }


}


/*
* This createUser method creates a new user, checks for duplicates, assigns a default role, saves the user in the database,
  and returns the saved user.
*
* login()
*  Takes care of all login functionalities
* */