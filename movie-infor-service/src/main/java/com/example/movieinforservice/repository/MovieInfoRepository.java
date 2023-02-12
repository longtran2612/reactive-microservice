package com.example.movieinforservice.repository;

import com.example.movieinforservice.model.MovieInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 1:35 PM 01-Jan-23
 * Long Tran
 */

public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo, String> {
    Mono<MovieInfo> findDistinctByMovieId(String movieId);
}
