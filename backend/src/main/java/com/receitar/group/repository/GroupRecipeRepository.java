package com.receitar.group.repository;

import com.receitar.group.model.GroupRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupRecipeRepository extends JpaRepository<GroupRecipe, UUID> {
    List<GroupRecipe> findAllByGroupId(UUID groupId);
}
