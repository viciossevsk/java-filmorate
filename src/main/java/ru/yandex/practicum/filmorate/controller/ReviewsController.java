package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewsController {

    private final ReviewService reviewService;

    @PostMapping()
    public Review createReview(@Valid @RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @PutMapping()
    public Review updateReview(@Valid @RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public void removeReview(@PathVariable("id") Integer reviewId) {
        reviewService.removeReview(reviewId);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable("id") Integer reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @GetMapping()
    public List<Review> getReceiveFilmsReviews(
            @RequestParam(defaultValue = "10", required = false) Integer count,
            @RequestParam(defaultValue = "0", required = false) String filmId) {
        if (filmId.equals("0")) {
            return reviewService.receiveAllReview();
        }
        return reviewService.getReceiveFilmsReviews(count, filmId);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeReview(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        reviewService.likeReview(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void dislikeReview(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        reviewService.dislikeReview(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeReview(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        reviewService.removeLikeReview(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void removeDislikeReview(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        reviewService.removeDislikeReview(id, userId);
    }
}
