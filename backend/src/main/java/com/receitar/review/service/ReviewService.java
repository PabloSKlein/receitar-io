package com.receitar.review.service;

import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.common.exception.BusinessException;
import com.receitar.recipe.model.Recipe;
import com.receitar.recipe.service.RecipeService;
import com.receitar.review.model.Review;
import com.receitar.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ReviewService {
    private final UserService userService;
    private final RecipeService recipeService;
    private final ReviewRepository reviewRepository;

    public void create(UUID userId, UUID recipeId, Double score) {
        //could be replaced by spring beans validation.
        if (score == null || score > 5.0 || score < 0) {
            throw new BusinessException("Score must be between 0 and 5.");
        }

        User user = userService.getById(userId);
        Recipe recipe = recipeService.getById(recipeId);

        Review review = new Review();

        review.setRecipe(recipe);
        review.setUser(user);
        review.setScore(score);

        reviewRepository.save(review);
    }
}