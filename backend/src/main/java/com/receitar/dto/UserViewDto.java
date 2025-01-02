package com.receitar.dto;

import com.receitar.model.User;

import java.util.UUID;

public record UserViewDto(UUID id, String name) {
    public UserViewDto(User user) {
        this(user.getId(), user.getName());
    }
}
