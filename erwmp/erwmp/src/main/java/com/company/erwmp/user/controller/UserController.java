package com.company.erwmp.user.controller;

import com.company.erwmp.user.dto.*;
import com.company.erwmp.user.entity.UserEntity;
import com.company.erwmp.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(
            @RequestBody CreateUserRequest request) {

        UserEntity createdUser = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest updateRequest) {

        UserEntity updatedUser = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("DELETE HIT FOR USER: " + id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }


    @GetMapping
    public Page<UserResponse> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return userService.getUsers(page, size, sortBy, direction);
    }



    @GetMapping("/search")
    public Page<UserEntity> search(
            @RequestParam String keyword,
            Pageable pageable) {
        return userService.searchUsers(keyword, pageable);
    }




}



/*
* This method:
Receives user data from the client (Postman / frontend)
Sends that data to the service layer
Gets the saved user
Sends a proper HTTP response back
👉 In short: “Take order → cook food → serve food with a receipt”
*
*
* We use:
ResponseEntity<UserEntity>
because it lets us return three things together:
📦 Data (UserEntity)
🚦 HTTP status code (201, 200, 400, etc.)
🧾 Headers (optional)
* */
/*

* @RequestBody UserEntity userRequest
*What it means
    The client sends JSON data
    Spring converts that JSON into a Java object
    That object is stored in userRequest
*
* */
/*
* UserEntity createdUser = userService.createUser(
        userRequest.getUsername(),
        userRequest.getEmail(),
        userRequest.getPassword()

  * Creating (saving) the user first, and then getting back the saved user details

);
* */