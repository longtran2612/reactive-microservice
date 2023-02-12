package com.example.movieinforservice.controller;

import com.example.movieinforservice.model.MovieInfo;
import com.example.movieinforservice.repository.MovieInfoRepository;
import com.example.movieinforservice.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.awt.*;

/**
 * 1:37 PM 01-Jan-23
 * Long Tran
 */
@RestController
@RequestMapping("/movie-info")
@RequiredArgsConstructor
public class MovieInfoController {
    private final MovieService movieService;

    private final MovieInfoRepository movieInfoRepository;

    Sinks.Many<MovieInfo> movieInfoSink = Sinks.many().replay().latest();

    @PostMapping
    public Mono<MovieInfo> create(@RequestBody @Valid MovieInfo movieInfo){
        return movieService.create(movieInfo).doOnNext(movieSaved->{
            movieInfoSink.tryEmitNext(movieSaved);
        });
    }
    @GetMapping("/{id}")
    public Mono<MovieInfo> getById(@PathVariable String id){
        return movieService.getById(id);
    }
    @GetMapping
    public Flux<MovieInfo> getAllMovies(){
        return movieService.getAllMovies();
    }
    @GetMapping(value = "/stream",produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfo> getAllMoviesStream(){
        return movieInfoSink.asFlux();
    }

    @GetMapping("/movie-id/{movieId}")
    public Mono<MovieInfo> getByMovieId(@PathVariable String movieId){
        return movieInfoRepository.findDistinctByMovieId(movieId);

    }
}
