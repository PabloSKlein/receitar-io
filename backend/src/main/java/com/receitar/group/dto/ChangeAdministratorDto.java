package com.receitar.group.dto;

import java.util.UUID;

public record ChangeAdministratorDto(UUID userId, UUID groupId, Boolean isAdministrator, UUID systemUserId) {
}
