package com.receitar.recipe.dto;

import java.util.Set;

public record RecipeCreateDto(String name, Set<IngredientDto> ingredients) {
}
