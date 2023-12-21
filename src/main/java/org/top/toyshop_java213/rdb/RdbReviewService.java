package org.top.toyshop_java213.rdb;

import org.springframework.stereotype.Service;
import org.top.toyshop_java213.entity.Review;
import org.top.toyshop_java213.rdb.repository.ReviewRepository;
import org.top.toyshop_java213.service.ReviewService;

import java.util.Date;
import java.util.Optional;

@Service
public class RdbReviewService implements ReviewService {

    private final ReviewRepository reviewRepository;
    public RdbReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    @Override
    public Iterable<Review> findAllByProduct(Integer productId) {
        return null;
    }

    @Override
    public Optional<Review> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Review save(Review review) {
        review.setWrittenIn(new Date()); // установить дату формирования
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> update(Review review) {
        return Optional.empty();
    }

    @Override
    public Optional<Review> deleteById(Integer id) {
        Optional<Review> deleted = reviewRepository.findById(id);
        if (deleted.isPresent()) {
            reviewRepository.deleteById(id);
        }
        return deleted;
    }
}
