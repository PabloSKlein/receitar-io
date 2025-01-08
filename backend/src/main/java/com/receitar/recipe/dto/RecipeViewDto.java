package com.receitar.recipe.dto;

import com.receitar.recipe.model.Recipe;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record RecipeViewDto(UUID id, String name, Set<IngredientDto> ingredients) {
    public RecipeViewDto(Recipe recipe) {
        this(recipe.getId(), recipe.getName(), recipe.getIngredients().stream()
                .map(it -> new IngredientDto(it.getName(), it.getQuantity(), it.getMeasureType()))
                .collect(Collectors.toSet()));
    }
}
