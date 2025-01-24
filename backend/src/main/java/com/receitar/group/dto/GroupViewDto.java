package com.receitar.group.dto;

import com.receitar.group.model.Group;

import java.time.LocalDate;
import java.util.UUID;

public record GroupViewDto(UUID id, String name, String description, Integer quantity,
                           UUID createdBy, LocalDate createdDate) {
    public GroupViewDto(Group group) {
        this(group.getId(), group.getName(), group.getDescription(),
                group.getGroupUsers().size(), group.getCreatedBy(), group.getCreatedDate());
    }
}

