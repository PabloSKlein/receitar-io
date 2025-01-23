package com.receitar.favorite.dto;

import java.util.UUID;

public record FavoriteDto(UUID userId, UUID recipeId) {
}
