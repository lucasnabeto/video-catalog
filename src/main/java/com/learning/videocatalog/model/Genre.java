package com.learning.videocatalog.model;

public enum Genre {
    ACTION("Action"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    COMEDY("Comedy"),
    CRIME("Crime");

    private String genreOMDB;

    Genre(String genreOMDB) {
        this.genreOMDB = genreOMDB;
    }

    public static Genre fromString(String text) {
        for (Genre genre : Genre.values()) {
            if (genre.genreOMDB.equalsIgnoreCase(text)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("No genre was found!");
    }
}
