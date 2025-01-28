package com.receitar.group.service;

import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.common.exception.NotFoundException;
import com.receitar.group.dto.GroupRecipeCreateDto;
import com.receitar.group.model.Group;
import com.receitar.group.model.GroupRecipe;
import com.receitar.group.repository.GroupRecipeRepository;
import com.receitar.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupRecipeService {

    private final UserService userService;

    private final GroupRecipeRepository groupRecipeRepository;

    public void addRecipeToGroup(GroupRecipeCreateDto groupRecipeCreateDto) {
        User user = userService.getById(groupRecipeCreateDto.userId());

        Recipe recipe = user.getRecipes().stream()
                .filter(it -> it.getId().equals(groupRecipeCreateDto.recipeId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Recipe"));

        Group group = user.getGroupUsers().stream()
                .filter(it -> it.getGroup().getId().equals(groupRecipeCreateDto.groupId()))
                .findFirst().orElseThrow(() -> new NotFoundException("Group"))
                .getGroup();

        GroupRecipe groupRecipe = new GroupRecipe();
        groupRecipe.setGroup(group);
        groupRecipe.setRecipe(recipe);
        groupRecipe.setDate(LocalDate.now());

        groupRecipeRepository.save(groupRecipe);
    }

    public List<GroupRecipe> getAll(UUID groupId, Integer quantity) {
        if (quantity != null && quantity > 0) {
            return groupRecipeRepository.findAllByGroupId(groupId).subList(0, quantity);
        }
        return groupRecipeRepository.findAllByGroupId(groupId);
    }
}
