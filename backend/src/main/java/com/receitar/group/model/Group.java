package com.receitar.group.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "app_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private UUID createdBy;

    private LocalDate createdDate;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupUser> groupUsers;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupRecipe> groupRecipes;
}
