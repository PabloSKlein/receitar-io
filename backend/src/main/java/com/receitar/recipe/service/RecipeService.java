package com.receitar.recipe.service;

import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.common.exception.NotFoundException;
import com.receitar.recipe.dto.IngredientDto;
import com.receitar.recipe.dto.IngredientsDto;
import com.receitar.recipe.dto.RecipeCreateDto;
import com.receitar.recipe.mapper.IngredientMapper;
import com.receitar.recipe.model.Ingredient;
import com.receitar.recipe.model.Recipe;
import com.receitar.recipe.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;

    private final UserService userService;


    public Recipe create(RecipeCreateDto recipeCreateDto) {
        Recipe recipe = new Recipe();

        User user = userService.getById(recipeCreateDto.userId());

        Set<Ingredient> ingredients = ingredientMapper.fromIngredientsDto(recipe, recipeCreateDto.ingredients());
        recipe.setIngredients(ingredients);
        recipe.setName(recipeCreateDto.name());
        recipe.setUser(user);

        return recipeRepository.save(recipe);
    }

    public void overwriteIngredients(UUID id, Set<IngredientDto> ingredientsDto) {
        Recipe recipe = getById(id);

        Set<Ingredient> ingredients = ingredientMapper.fromIngredientsDto(recipe, ingredientsDto);

        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(ingredients);

        recipeRepository.save(recipe);
    }

    public void addIngredients(UUID id, IngredientsDto ingredientsDto) {
        Recipe recipe = getById(id);

        Set<Ingredient> ingredients = ingredientMapper.fromIngredientsDto(recipe, ingredientsDto.ingredients());

        recipe.getIngredients().addAll(ingredients);
        recipeRepository.save(recipe);

    }

    public void deleteIngredients(UUID id, IngredientsDto ingredientsDto) {
        Recipe recipe = getById(id);

        Set<Ingredient> ingredientsToRemove = recipe.getIngredients().stream()
                .filter(ingredientRecipe -> ingredientsDto.ingredients().stream()
                        .anyMatch(ingredientDto -> ingredientRecipe.getName().equals(ingredientDto.name())
                        )).collect(Collectors.toSet());

        recipe.getIngredients().removeAll(ingredientsToRemove);

        recipeRepository.save(recipe);
    }

    public Recipe getById(UUID id) {
        return recipeRepository.findById(id).orElseThrow(() -> new NotFoundException("Recipe"));
    }

    public List<Recipe> getAll(UUID userId) {
        if (userId == null){
            return recipeRepository.findAll();
        }
        return recipeRepository.findAllByUserId(userId);
    }

    public void deleteById(UUID id) {
        recipeRepository.deleteById(id);
    }

}
