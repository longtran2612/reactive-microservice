package com.example.movieservice.controller;

import com.example.movieservice.client.MovieInfoClient;
import com.example.movieservice.client.MovieReviewClient;
import com.example.movieservice.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 9:46 AM 18-Jan-23
 * Long Tran
 */
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieInfoClient movieInfoClient;
    private final MovieReviewClient movieReviewClient;

    @GetMapping("/{id}")
    public Mono<Movie> retrieveMovie(@PathVariable String id){
      return   movieInfoClient.retrieveMovieInfo(id)
                .flatMap(movieInfo -> {
                    var movieReview = movieReviewClient.retrieveMovieReview(id).collectList();
                    return movieReview.map(review -> new Movie(movieInfo,review));
                });
    }
}
