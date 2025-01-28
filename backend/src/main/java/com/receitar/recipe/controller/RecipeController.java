package com.receitar.recipe.controller;

import com.receitar.recipe.dto.IngredientsDto;
import com.receitar.recipe.dto.RecipeCreateDto;
import com.receitar.recipe.dto.RecipeViewDto;
import com.receitar.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @PostMapping
    RecipeViewDto create(@RequestBody RecipeCreateDto recipeCreateDto) {
        return new RecipeViewDto(recipeService.create(recipeCreateDto));
    }

    @GetMapping("/all/{userId}")
    List<RecipeViewDto> getAllById(@PathVariable UUID userId) {
        return recipeService.getAllById(userId);
    }

    @GetMapping("/{id}")
    RecipeViewDto getById(@PathVariable UUID id) {
        return recipeService.getRecipeViewById(id);
    }

    @GetMapping
    List<RecipeViewDto> getALl() {
        return recipeService.getALl().stream()
                .map(RecipeViewDto::new)
                .toList();
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable UUID id) {
        recipeService.deleteById(id);
    }

    @PutMapping("/{id}/ingredients")
    void overwriteIngredients(@PathVariable UUID id, @RequestBody IngredientsDto ingredientsDto) {
        recipeService.overwriteIngredients(id, ingredientsDto.ingredients());
    }

    @PostMapping("/{id}/ingredients")
    void addIngredients(@PathVariable UUID id, @RequestBody IngredientsDto ingredientsDto) {
        recipeService.addIngredients(id, ingredientsDto);
    }

    @DeleteMapping("/{id}/ingredients")
    void deleteIngredients(@PathVariable UUID id, @RequestBody IngredientsDto ingredientsDto) {
        recipeService.deleteIngredients(id, ingredientsDto);
    }
}
