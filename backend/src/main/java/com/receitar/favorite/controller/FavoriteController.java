package com.receitar.favorite.controller;

import com.receitar.client.dto.UserViewDto;
import com.receitar.favorite.service.FavoriteService;
import com.receitar.favorite.dto.FavoriteDto;
import com.receitar.recipe.dto.RecipeViewDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    void create(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.create(favoriteDto);
    }

    @GetMapping("/user-id/{userId}")
    List<RecipeViewDto> getAllByUser(@PathVariable UUID userId) {
        return favoriteService.getAllByUser(userId);
    }

    @DeleteMapping
    void delete(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.delete(favoriteDto);
    }

    @GetMapping
    List<UserViewDto> getAllWithFavorites() {
        return favoriteService.getAllWithFavorites();
    }
}
