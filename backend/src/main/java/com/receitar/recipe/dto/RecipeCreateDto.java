package com.receitar.recipe.dto;

import java.util.Set;
import java.util.UUID;

public record RecipeCreateDto(String name, Set<IngredientDto> ingredients, UUID userId) {
}
