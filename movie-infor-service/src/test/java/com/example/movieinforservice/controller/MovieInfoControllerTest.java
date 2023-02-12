package com.example.movieinforservice.controller;

import com.example.movieinforservice.model.MovieInfo;
import com.example.movieinforservice.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MovieInfoControllerTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @Autowired
    WebTestClient webTestClient;

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
    void create() {
        webTestClient.post().uri("/movies")
                .bodyValue(MovieInfo.builder().name("test").description("test").release(LocalDate.now()).cast(List.of("c1","c2")).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.description").isEqualTo("test")
                .jsonPath("$.release").isNotEmpty()
                .jsonPath("$.cast").isArray()
                .jsonPath("$.cast[0]").isEqualTo("c1")
                .jsonPath("$.cast[1]").isEqualTo("c2");
    }

    @Test
    void getById() {

    }

    @Test
    void getAllMovies() {
        webTestClient.get().uri("/movies")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isNotEmpty()
                .jsonPath("$[0].name").isEqualTo("test1")
                .jsonPath("$[1].id").isNotEmpty()
                .jsonPath("$[1].name").isEqualTo("test2")
                .jsonPath("$[2].id").isNotEmpty()
                .jsonPath("$[2].name").isEqualTo("test3");
    }
}