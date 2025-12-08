package com.learning.videocatalog.model;

import java.util.OptionalDouble;

public class TvShow {
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private Genre genre;
    private String actors;
    private String poster;
    private String plot;

    public TvShow(TvShowData tvShowData) {
        this.title = tvShowData.title();
        this.totalSeasons = tvShowData.totalSeasons();
        this.rating = OptionalDouble.of(Double.parseDouble(tvShowData.rating())).orElse(0.0);
        this.genre = Genre.fromString(tvShowData.genre().split(",")[0].trim());
        this.actors = tvShowData.actors();
        this.poster = tvShowData.poster();
        this.plot = tvShowData.plot();
    }

    @Override
    public String toString() {
        return "Genre=" + genre +
                "title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", plot='" + plot + '\'';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
