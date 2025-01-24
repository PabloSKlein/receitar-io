package com.receitar.group.dto;

import com.receitar.group.model.GroupUser;

import java.util.UUID;

public record GroupUserViewDto(UUID userId, String userName, boolean isAdmin) {
    public GroupUserViewDto(GroupUser groupUser){
        this(groupUser.getUser().getId(), groupUser.getUser().getName(), groupUser.isAdministrator());
    }
}
