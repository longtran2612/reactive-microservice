package com.example.moviereviewservice.handler;

import com.example.moviereviewservice.model.MovieReview;
import com.example.moviereviewservice.repository.MovieReviewRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.stream.Collectors;

/**
 * 5:10 PM 17-Jan-23
 * Long Tran
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MovieReviewHandler {

    private final Validator validator;
    private final MovieReviewRepository movieReviewRepository;

    Sinks.Many<MovieReview> sinks = Sinks.many().replay().latest();

    public Mono<ServerResponse> getById(ServerRequest request) {
        return ServerResponse.ok().body(movieReviewRepository.findById(request.pathVariable("id")), MovieReview.class);
    }
    public Mono<ServerResponse> getAll(ServerRequest request) {
        var movieInfo = request.queryParam("movieInfo");
        return movieInfo.map(s -> ServerResponse.ok().body(movieReviewRepository.findByMovieId(s), MovieReview.class)).orElseGet(() -> ServerResponse.ok().body(movieReviewRepository.findAll(), MovieReview.class));
    }

   public Mono<ServerResponse> create(ServerRequest request) {
       return request.bodyToMono(MovieReview.class)
               .doOnNext(this::validate)
               .doOnNext(sinks::tryEmitNext)
//               .doOnNext(validator::validate)
               .flatMap(movieReview -> ServerResponse.ok().body(movieReviewRepository.save(movieReview), MovieReview.class));
   }

    private void validate(MovieReview movieReview) {
        var violations = validator.validate(movieReview);
        log.error("Violations: {}", violations);
        if (!violations.isEmpty()) {
            throw new IllegalStateException(violations.stream().map(ConstraintViolation::getMessage).sorted().collect(Collectors.joining(",")));
        }
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        var id = request.pathVariable("id");
        var movieReviewMono = movieReviewRepository.findById(id).switchIfEmpty(Mono.error(new IllegalStateException("Movie review not found")));
        return request.bodyToMono(MovieReview.class)
                .zipWith(movieReviewMono, (movieReview, movieReview1) -> {
                    movieReview1.setMovieId(movieReview.getMovieId());
                    movieReview1.setReview(movieReview.getReview());
                    return movieReview1;
                })
                .flatMap(movieReview -> ServerResponse.ok().body(movieReviewRepository.save(movieReview), MovieReview.class))
                .switchIfEmpty(Mono.error(new IllegalStateException("Movie review not found")));
   }
   public Mono<ServerResponse> delete(ServerRequest request) {
        return movieReviewRepository.deleteById(request.pathVariable("id"))
                .then(ServerResponse.ok().build());
   }

   public Mono<ServerResponse> getByMovieId(ServerRequest request){
       var movieId = request.pathVariable("movieId");
        return ServerResponse.ok().body(movieReviewRepository.findByMovieId(movieId),MovieReview.class);
   }

   public Mono<ServerResponse> getStream(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_NDJSON).body(sinks.asFlux(),MovieReview.class).log();
   }
}
