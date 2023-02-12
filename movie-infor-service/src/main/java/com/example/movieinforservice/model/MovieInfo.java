package com.example.movieinforservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

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
public class MovieInfo {
    @Id
    private String id;
    @NotNull(message = "Movie name is required")
    private String name;
    private String description;
    private Integer year;

    private List<String> cast;
    private LocalDate release;

    @Indexed
    private String movieId;

}
