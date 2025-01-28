package com.receitar.group.dto;

import java.util.UUID;

public record GroupRecipeCreateDto(UUID userId, UUID recipeId, UUID groupId) {
}
