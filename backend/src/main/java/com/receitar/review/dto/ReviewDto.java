package com.receitar.review.dto;

import java.util.UUID;

public record ReviewDto(UUID userId, UUID recipeId, Double score) {
}