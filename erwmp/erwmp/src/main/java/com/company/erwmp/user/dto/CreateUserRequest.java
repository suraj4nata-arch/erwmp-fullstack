package com.company.erwmp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}

/*
*
* It’s a DTO (Data Transfer Object) — basically a simple class that only contains the data you expect from the client
*
* CreateUserRequest = the menu card
Only what the customer needs to tell the kitchen
You don’t hand over the chef’s secret recipe to every customer
* */