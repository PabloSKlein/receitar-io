package com.receitar.recipe.dto;

import com.receitar.recipe.model.MeasureType;

public record IngredientDto(String name, int quantity, MeasureType measureType) {
}
