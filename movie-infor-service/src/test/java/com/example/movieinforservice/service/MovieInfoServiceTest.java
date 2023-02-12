package com.example.movieinforservice.service;

import com.example.movieinforservice.model.MovieInfo;
import com.example.movieinforservice.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
class MovieInfoServiceTest {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        movieInfoRepository.saveAll(List.of(
               MovieInfo.builder().name("test1").build(),
               MovieInfo.builder().name("test2").build(),
               MovieInfo.builder().name("test3").build()
        )).blockLast();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllMovies() {
        var movies = movieInfoRepository.findAll();
        StepVerifier.create(movies)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
        var movie =movieService.create(MovieInfo.builder().name("test").description("test").release(LocalDate.now()).cast(List.of("c1","c2")).build());

        StepVerifier.create(movie)
                .expectSubscription()
                .expectNextMatches(m -> m.getId() != null)
                .verifyComplete();
    }
}