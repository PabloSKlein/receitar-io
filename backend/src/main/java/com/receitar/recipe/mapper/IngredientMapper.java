package com.receitar.recipe.mapper;

import com.receitar.recipe.dto.IngredientDto;
import com.receitar.recipe.model.Ingredient;
import com.receitar.recipe.model.Recipe;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {
    public Set<Ingredient> fromIngredientsDto(Recipe recipe, Set<IngredientDto> ingredientsDto) {
        return ingredientsDto
                .stream()
                .map(ingredientDto -> fromIngredientDto(recipe, ingredientDto))
                .collect(Collectors.toSet());
    }

    public Ingredient fromIngredientDto(Recipe recipe, IngredientDto ingredientDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredient.setName(ingredientDto.name());
        ingredient.setQuantity(ingredientDto.quantity());
        ingredient.setMeasureType(ingredientDto.measureType());
        return ingredient;
    }
}
