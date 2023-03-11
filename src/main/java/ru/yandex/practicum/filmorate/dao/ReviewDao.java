package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewDao {
    Review createReview(Review review);

    Review getReviewById(Integer reviewId);

    Review updateReview(Review review);

    void removeReview(Integer reviewId);

    List<Review> getReceiveFilmsReviews(Integer count, String filmId);

    void likeReview(Integer reviewId, Integer userId);

    void dislikeReview(Integer reviewId, Integer userId);

    void removeLikeReview(Integer reviewId, Integer userId);

    void removeDislikeReview(Integer reviewId, Integer userId);

    List<Review> receiveAllReview();
}
