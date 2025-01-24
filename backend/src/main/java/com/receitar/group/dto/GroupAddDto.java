package com.receitar.group.dto;

import java.util.UUID;

public record GroupAddDto(UUID groupId, UUID userId, UUID systemUserId) {
}
