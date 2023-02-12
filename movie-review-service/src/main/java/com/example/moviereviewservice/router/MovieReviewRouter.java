package com.example.moviereviewservice.router;

import com.example.moviereviewservice.handler.MovieReviewHandler;
import com.example.moviereviewservice.model.MovieReview;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 5:07 PM 17-Jan-23
 * Long Tran
 */
@Configuration
public class MovieReviewRouter {

    @Bean
    public RouterFunction<ServerResponse> reviewRoute(MovieReviewHandler movieReviewHandler) {
        return route()
                .nest(path("/reviews"), builder -> builder
                        .GET("/{id}", movieReviewHandler::getById)
                        .GET("", movieReviewHandler::getAll)
                        .GET("/stream",movieReviewHandler::getStream)
                        .GET("/movie-id/{movieId}",movieReviewHandler::getByMovieId)
                        .POST("", movieReviewHandler::create)
                        .PUT("/{id}", movieReviewHandler::update)
                        .DELETE("/{id}", movieReviewHandler::delete)
                )
                .build();
    }

}
