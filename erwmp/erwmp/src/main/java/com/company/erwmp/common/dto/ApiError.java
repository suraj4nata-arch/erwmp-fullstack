package com.company.erwmp.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ApiError {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
/*
*
* timestamp → when error occurred
* status → HTTP status code
* error → short description
* message → detailed error message
* path → URL of the failed request
*
* */