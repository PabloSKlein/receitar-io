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

    @GetMapping
    List<RecipeViewDto> getAll() {
        return recipeService.getAll()
                .stream()
                .map(RecipeViewDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    RecipeViewDto getById(@PathVariable UUID id) {
        return new RecipeViewDto(recipeService.getById(id));
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable UUID id) {
        recipeService.deleteById(id);
    }

    @PostMapping("/{id}/ingredients")
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
