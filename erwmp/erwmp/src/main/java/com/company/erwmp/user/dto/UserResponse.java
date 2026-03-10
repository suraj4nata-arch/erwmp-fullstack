package com.company.erwmp.user.dto;

import com.company.erwmp.user.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
public class UserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    @JsonCreator
    public UserResponse(
            @JsonProperty("id") Long id,
            @JsonProperty("username") String username,
            @JsonProperty("email") String email,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("createdBy") String createdBy,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("updatedBy") String updatedBy
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }


}

/*
*
* Purpose: To control what data goes in and out of your API.

You don’t want to expose your database entities directly to the client.

You might want to send only some fields or combine fields from multiple entities.

You also might want different shapes for request and response.
* */