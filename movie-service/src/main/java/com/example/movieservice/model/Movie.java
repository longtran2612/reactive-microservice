package com.example.movieservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * 9:48 AM 18-Jan-23
 * Long Tran
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    private MovieInfo movieInfo;
    private List<MovieReview> reviewList;
}
