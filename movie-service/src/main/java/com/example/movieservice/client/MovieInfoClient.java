package com.example.movieservice.client;

import com.example.movieservice.model.MovieInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 10:14 AM 18-Jan-23
 * Long Tran
 */
@Component
@RequiredArgsConstructor
public class MovieInfoClient {
    private final WebClient webClient;

    private final String movieInfoServiceUrl = "http://localhost:5000/movie-info/";

    public Mono<MovieInfo> retrieveMovieInfo(String movieId) {
        return webClient.get()
                .uri(movieInfoServiceUrl + "movie-id/" + movieId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, x -> {
                    if (x.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.error(new IllegalAccessException("movie not found"));
                    }
                    return x.bodyToMono(String.class).flatMap(v ->
                            Mono.error(new IllegalAccessException("loi call apii")));
                })
                .bodyToMono(MovieInfo.class).log().retry(5);
    }


}
