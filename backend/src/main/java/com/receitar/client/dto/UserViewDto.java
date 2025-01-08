package com.receitar.client.dto;

import com.receitar.client.model.User;

import java.util.UUID;

public record UserViewDto(UUID id, String name) {
    public UserViewDto(User user) {
        this(user.getId(), user.getName());
    }
}
