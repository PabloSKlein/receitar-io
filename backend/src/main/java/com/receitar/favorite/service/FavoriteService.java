package com.receitar.favorite.service;

import com.receitar.client.dto.UserViewDto;
import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.favorite.dto.FavoriteDto;
import com.receitar.favorite.model.Favorite;
import com.receitar.favorite.repository.FavoriteRepository;
import com.receitar.recipe.dto.RecipeViewDto;
import com.receitar.recipe.model.Recipe;
import com.receitar.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserService userService;
    private final RecipeService recipeService;

    public void create(FavoriteDto favoriteDto) {
        if (findByUserAndRecipe(favoriteDto.recipeId(), favoriteDto.userId()).isPresent()) {
            throw new RuntimeException("User already have this recipe as a favorite");
        }
        Favorite favorite = new Favorite();
        User user = userService.getById(favoriteDto.userId());
        Recipe recipe = recipeService.getById(favoriteDto.recipeId());

        favorite.setUser(user);
        favorite.setRecipe(recipe);
        favoriteRepository.save(favorite);
    }

    public List<RecipeViewDto> getAllByUser(UUID userId) {
        return favoriteRepository.findAll()
                .stream()
                .filter(favorite -> userId.equals(favorite.getUser().getId()))
                .map(Favorite::getRecipe)
                .map(RecipeViewDto::new)
                .toList();
    }

    public void delete(FavoriteDto favoriteDto) {
        Favorite match = findByUserAndRecipe(favoriteDto.recipeId(), favoriteDto.userId()).orElseThrow();
        favoriteRepository.delete(match);
    }

    private Optional<Favorite> findByUserAndRecipe(UUID recipeId, UUID userId) {
        return favoriteRepository.findAll().stream()
                .filter(favorite -> favorite.getRecipe().getId().equals(recipeId)
                        && favorite.getUser().getId().equals(userId))
                .findFirst();
    }

    //Pegar todos usuários que tem uma receita favorita

    public List<UserViewDto> getAllWithFavorites() {
        return favoriteRepository.findAll().stream()
                .map(Favorite::getUser)
                .map(UserViewDto::new)
                .toList();
    }


}
