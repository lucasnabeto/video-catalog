package com.learning.videocatalog.dto;

public record EpisodeDTO(Integer season,
                         Integer episodeNumber,
                         String title,
                         Double rating) {
}
