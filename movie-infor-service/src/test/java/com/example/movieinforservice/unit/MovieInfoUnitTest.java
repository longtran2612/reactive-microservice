package com.example.movieinforservice.unit;

import com.example.movieinforservice.controller.MovieInfoController;
import com.example.movieinforservice.model.MovieInfo;
import com.example.movieinforservice.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

/**
 * 2:41 PM 01-Jan-23
 * Long Tran
 */
@WebFluxTest(controllers = MovieInfoController.class)
@AutoConfigureWebClient
public class MovieInfoUnitTest {

    @Autowired
    WebTestClient webTestClient;
    @MockBean
    MovieService movieService;

    @Test
    void getAll(){
       when(movieService.getAllMovies()).thenReturn(Flux.just(
               MovieInfo.builder().name("test1").build(),
               MovieInfo.builder().name("test2").build()
//               Movie.builder().name("test3").build()
       ));
         webTestClient.get().uri("/movies")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);

    }
}
