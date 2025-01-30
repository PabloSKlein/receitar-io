package com.receitar.group.dto;

import com.receitar.group.model.Group;
import com.receitar.group.model.GroupUser;

import java.time.LocalDate;
import java.util.UUID;

public record GroupViewDto(UUID id, String name, String description, Integer quantity, long quantityAdmin,
                           UUID createdBy, LocalDate createdDate) {
    public GroupViewDto(Group group) {
        this(group.getId(), group.getName(), group.getDescription(),
                getGroupUserSize(group), getGroupAdminSize(group), group.getCreatedBy(), group.getCreatedDate());
    }

    private static long getGroupAdminSize(Group group) {
        return group.getGroupUsers().stream()
                .filter(GroupUser::isAdministrator)
                .count();
    }

    private static int getGroupUserSize(Group group) {
        return group.getGroupUsers().size();
    }
}

