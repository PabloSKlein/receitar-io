package com.receitar.client.model;

import com.receitar.favorite.model.Favorite;
import com.receitar.group.model.GroupUser;
import com.receitar.recipe.model.Recipe;
import com.receitar.review.model.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "user")
    private List<GroupUser> groupUsers;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
