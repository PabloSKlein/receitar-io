package com.receitar.group.dto;

import com.receitar.group.model.GroupRecipe;

import java.time.LocalDate;

public record GroupRecipeViewDto(String name, LocalDate createdDate, Integer favorites) {
    public GroupRecipeViewDto(GroupRecipe groupRecipe) {
        this(groupRecipe.getRecipe().getName(), groupRecipe.getDate(),
                groupRecipe.getRecipe().getFavorites().size());
    }
}
