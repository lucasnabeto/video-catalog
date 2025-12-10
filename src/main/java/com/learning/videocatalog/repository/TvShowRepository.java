package com.learning.videocatalog.repository;

import com.learning.videocatalog.model.Episode;
import com.learning.videocatalog.model.Genre;
import com.learning.videocatalog.model.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
    //Derived query Spring Data JPA:
    Optional<TvShow> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT t FROM TvShow t WHERE t.totalSeasons <= : totalSeasons")
    Optional<TvShow> findTvShowWithJPQL(int totalSeasons);

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE t = :tvShow ORDER BY e.rating DESC LIMIT 5")
    Optional<Episode> findEpisodeWithJPQL(TvShow tvShow);

    List<TvShow> findTop5ByOrderByRatingDesc();

    @Query("SELECT t FROM TvShow t JOIN t.episodes e GROUP BY t ORDER BY MAX(e.releaseDate) DESC LIMIT 5")
    List<TvShow> findMostRecentlyEpisodes();

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE t.id = :tvShowId AND e.season = :seasonId")
    List<Episode> getAllEpisodesFromTvShowSeason(Long tvShowId, Long seasonId);

    List<TvShow> findByGenre(Genre genre);

    @Query("SELECT e FROM TvShow t JOIN t.episodes e WHERE t.id = :id ORDER BY e.rating DESC LIMIT 5")
    List<Episode> getTop5EpisodesFromTvShow(Long id);
}
