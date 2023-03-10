package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.ReviewDaoImpl;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewDaoImpl reviewDao;

    public Review createReview(Review review) {
        return reviewDao.createReview(review);
    }

    public Review updateReview(Review review) {
        return reviewDao.updateReview(review);
    }

    public void removeReview(Integer reviewId) {
        reviewDao.removeReview(reviewId);
    }

    public Review getReviewById(Integer reviewId) {
        return reviewDao.getReviewById(reviewId);
    }

    public List<Review> getReceiveFilmsReviews(Integer count, String filmId) {
        return reviewDao.getReceiveFilmsReviews(count, filmId);
    }

    public void likeReview(Integer reviewId, Integer userId) {
        reviewDao.likeReview(reviewId, userId);
    }

    public void dislikeReview(Integer reviewId, Integer userId) {
        reviewDao.dislikeReview(reviewId, userId);
    }

    public void removeLikeReview(Integer reviewId, Integer userId) {
        reviewDao.removeLikeReview(reviewId, userId);
    }

    public void removeDislikeReview(Integer reviewId, Integer userId) {
        reviewDao.removeDislikeReview(reviewId, userId);
    }

    public List<Review> receiveAllReview() {
        return reviewDao.receiveAllReview();
    }
}
