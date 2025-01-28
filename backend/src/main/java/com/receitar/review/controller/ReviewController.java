package com.receitar.review.controller;

import com.receitar.review.dto.ReviewDto;
import com.receitar.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    void create(@RequestBody ReviewDto reviewDto) {
        reviewService.create(reviewDto.userId(), reviewDto.recipeId(), reviewDto.score());
    }
}