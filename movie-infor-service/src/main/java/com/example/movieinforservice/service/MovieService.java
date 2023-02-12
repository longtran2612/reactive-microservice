package com.example.movieinforservice.service;

import com.example.movieinforservice.model.MovieInfo;
import com.example.movieinforservice.repository.MovieInfoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 1:36 PM 01-Jan-23
 * Long Tran
 */
@Service
public class MovieService {

    private final MovieInfoRepository movieInfoRepository;

    public MovieService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Flux<MovieInfo> getAllMovies() {
        return movieInfoRepository.findAll();
    }
    public Mono<MovieInfo> getById(String id){
        return movieInfoRepository.findById(id);
    }

    public Mono<MovieInfo> create(@Valid MovieInfo movieInfo){
        return movieInfoRepository.save(movieInfo);
    }
}
