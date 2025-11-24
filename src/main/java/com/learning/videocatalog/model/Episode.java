package com.learning.videocatalog.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private String rating;
    private LocalDate releaseDate;

    public Episode(Integer season, TvShowEpisodeData tvShowEpisodeData) {
        this.season = season;
        this.title = tvShowEpisodeData.title();
        this.episodeNumber = tvShowEpisodeData.episodeNumber();
        this.rating = tvShowEpisodeData.rating();

        try {
            this.releaseDate = LocalDate.parse(tvShowEpisodeData.releaseDate());
        } catch (DateTimeParseException e) {
            this.releaseDate = null;
        }
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "season=" + season +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", rating='" + rating + '\'' +
                ", releaseDate=" + releaseDate;
    }
}
