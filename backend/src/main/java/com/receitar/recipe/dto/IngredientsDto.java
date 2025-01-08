package com.receitar.recipe.dto;

import java.util.Set;

public record IngredientsDto(Set<IngredientDto> ingredients) {
}
