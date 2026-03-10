package com.company.erwmp.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private String token; // placeholder for now
}

/**
 * token → usually a JWT or session token for future requests
 *
 */