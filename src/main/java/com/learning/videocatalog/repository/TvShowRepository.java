package com.learning.videocatalog.repository;

import com.learning.videocatalog.model.Episode;
import com.learning.videocatalog.model.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    //Derived query Spring Data JPA:
    Optional<TvShow> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT t FROM TvShow t WHERE t.totalSeasons <= : totalSeasons")
    Optional<TvShow> findTvShowWithJPQL(int totalSeasons);

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE t = :tvShow ORDER BY e.rating DESC LIMIT 5")
    Optional<Episode> findEpisodeWithJPQL(TvShow tvShow);
}
