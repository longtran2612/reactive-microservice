package com.example.movieservice.client;

import com.example.movieservice.model.MovieReview;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * 10:23 AM 18-Jan-23
 * Long Tran
 */
@Component
@RequiredArgsConstructor
public class MovieReviewClient {

    private final WebClient webClient;

    private final String movieReviewServiceUrl = "http://localhost:5001/reviews/";

    public Flux<MovieReview> retrieveMovieReview(String movieId){
        return webClient.get()
                .uri(movieReviewServiceUrl +"movie-id/" + movieId)
                .retrieve()
                .bodyToFlux(MovieReview.class).log().retry(5);
    }
}
