package com.company.erwmp.user.mapper;

import com.company.erwmp.user.dto.UserResponse;
import com.company.erwmp.user.entity.UserEntity;

public class UserMapper {

    public static UserResponse toDto(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getCreatedBy(),
                user.getUpdatedAt(),
                user.getUpdatedBy()
        );
    }
}
