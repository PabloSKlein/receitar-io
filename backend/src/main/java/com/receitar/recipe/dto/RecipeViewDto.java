package com.receitar.recipe.dto;

import com.receitar.recipe.model.Recipe;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record RecipeViewDto(UUID id, String name, String userName, Double score, Set<IngredientDto> ingredients) {
    public RecipeViewDto(Recipe recipe) {
        this(recipe.getId(), recipe.getName(), recipe.getUser().getName(), null,
                getIngredientsViewDto(recipe));
    }

    public RecipeViewDto(Recipe recipe, Double average) {
        this(recipe.getId(), recipe.getName(), recipe.getUser().getName(), average,
                getIngredientsViewDto(recipe));
    }

    private static Set<IngredientDto> getIngredientsViewDto(Recipe recipe) {
        return recipe.getIngredients().stream()
                .map(it -> new IngredientDto(it.getName(), it.getQuantity(), it.getMeasureType()))
                .collect(Collectors.toSet());
    }
}
