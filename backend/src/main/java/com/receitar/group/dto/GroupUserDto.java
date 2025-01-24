package com.receitar.group.dto;

import java.util.UUID;

public record GroupUserDto(UUID userId, UUID groupId, UUID systemUserId) {
}
