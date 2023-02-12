package com.example.moviereviewservice.repository;

import com.example.moviereviewservice.model.MovieReview;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * 4:02 PM 17-Jan-23
 * Long Tran
 */

public interface MovieReviewRepository extends ReactiveMongoRepository<MovieReview, String> {
    Flux<MovieReview> findByMovieId(String movieId);
}
