package com.receitar.group.controller;

import com.receitar.group.dto.GroupRecipeCreateDto;
import com.receitar.group.dto.GroupRecipeViewDto;
import com.receitar.group.service.GroupRecipeService;
import com.receitar.recipe.dto.RecipeViewDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class GroupRecipeController {

    private final GroupRecipeService groupRecipeService;

    @PostMapping("/groups/recipes")
    void addRecipeToGroup(@RequestBody GroupRecipeCreateDto groupRecipeCreateDto) {
        groupRecipeService.addRecipeToGroup(groupRecipeCreateDto);
    }

    @GetMapping("/groups/{groupId}/recipes")
    List<GroupRecipeViewDto> getAll(@PathVariable UUID groupId, @RequestParam(required = false) Integer quantity) {
        return groupRecipeService.getAll(groupId, quantity)
                .stream()
                .map(GroupRecipeViewDto::new)
                .toList();
    }

    @GetMapping("/groups/users/{userId}/recipes")
    List<RecipeViewDto> getRecipesAddedLastInGroups(@PathVariable UUID userId) {
       return groupRecipeService.getRecipesAddedLastInGroups(userId).stream()
               .map(RecipeViewDto::new)
               .toList();
    }
}
