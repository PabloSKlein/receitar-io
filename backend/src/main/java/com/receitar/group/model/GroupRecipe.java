package com.receitar.group.model;

import com.receitar.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Table
@Entity
@Setter
@Getter
public class GroupRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    private LocalDate date;

}