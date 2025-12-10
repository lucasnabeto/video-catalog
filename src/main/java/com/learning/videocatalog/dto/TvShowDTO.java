package com.learning.videocatalog.dto;

import com.learning.videocatalog.model.Genre;

public record TvShowDTO(long id,
                        String title,
                        Integer totalSeasons,
                        Double rating,
                        Genre genre,
                        String actors,
                        String poster,
                        String plot){
        }
