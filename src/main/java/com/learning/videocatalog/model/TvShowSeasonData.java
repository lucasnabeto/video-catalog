package com.learning.videocatalog.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TvShowSeasonData(@JsonAlias("Season") Integer seasonNumber,
                               @JsonAlias("Episodes") List<TvShowEpisodeData> episodes) {
}
