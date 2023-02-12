package com.example.moviereviewservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 1:31 PM 01-Jan-23
 * Long Tran
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class MovieReview {
    @Id
    private String id;
    @Indexed
    @NotNull(message = "Movie ID is required")
    private String movieId;
    private String review;
    @PositiveOrZero(message = "Rating must be greater than or equal to 0")
    private Integer rating;
}
