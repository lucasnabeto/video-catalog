package com.learning.videocatalog.service;

import com.learning.videocatalog.dto.EpisodeDTO;
import com.learning.videocatalog.dto.TvShowDTO;
import com.learning.videocatalog.model.Episode;
import com.learning.videocatalog.model.Genre;
import com.learning.videocatalog.model.TvShow;
import com.learning.videocatalog.repository.TvShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TvShowService {
    private final TvShowRepository tvShowRepository;

    public TvShowService(TvShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }

    public List<TvShowDTO> getAllTvShows() {
        return toListTvShowDTO(tvShowRepository.findAll());
    }

    public List<TvShowDTO> getTop5TvShows() {
        return toListTvShowDTO(tvShowRepository.findTop5ByOrderByRatingDesc());
    }

    public List<TvShowDTO> getMostRecentlyEpisodes() {
        return toListTvShowDTO(tvShowRepository.findMostRecentlyEpisodes());
    }

    private List<TvShowDTO> toListTvShowDTO(List<TvShow> tvShows) {
        return tvShows.stream()
                .map(tvShow -> new TvShowDTO(
                        tvShow.getId(),
                        tvShow.getTitle(),
                        tvShow.getTotalSeasons(),
                        tvShow.getRating(),
                        tvShow.getGenre(),
                        tvShow.getActors(),
                        tvShow.getPoster(),
                        tvShow.getPlot()
                ))
                .toList();
    }

    private List<EpisodeDTO> toListEpisodeDTO(List<Episode> episodes) {
        return episodes.stream()
                .map(episode -> new EpisodeDTO(
                        episode.getSeason(),
                        episode.getEpisodeNumber(),
                        episode.getTitle(),
                        episode.getRating())
                )
                .toList();
    }

    public TvShowDTO getTvShowById(Long id) {
        Optional<TvShow> tvShow = tvShowRepository.findById(id);
        if (tvShow.isPresent()) {
            var desiredTvShow = tvShow.get();
            return new TvShowDTO(
                    desiredTvShow.getId(),
                    desiredTvShow.getTitle(),
                    desiredTvShow.getTotalSeasons(),
                    desiredTvShow.getRating(),
                    desiredTvShow.getGenre(),
                    desiredTvShow.getActors(),
                    desiredTvShow.getPoster(),
                    desiredTvShow.getPlot()
            );
        }

        return null;
    }

    public List<EpisodeDTO> getAllEpisodesFromTvShow(Long id) {
        Optional<TvShow> tvShow = tvShowRepository.findById(id);
        if (tvShow.isPresent()) {
            var desiredTvShow = tvShow.get();
            return toListEpisodeDTO(desiredTvShow.getEpisodes());
        }

        return null;
    }

    public List<EpisodeDTO> getAllEpisodesFromTvShowSeason(Long tvShowId, Long seasonId) {
        return toListEpisodeDTO(tvShowRepository.getAllEpisodesFromTvShowSeason(tvShowId, seasonId));
    }

    public List<TvShowDTO> getTvShowsByGenre(String genreName) {
        Genre genre = Genre.fromString(genreName);
        return toListTvShowDTO(tvShowRepository.findByGenre(genre));
    }

    public List<EpisodeDTO> getTop5EpisodesFromTvShow(Long id) {
        return toListEpisodeDTO(tvShowRepository.getTop5EpisodesFromTvShow(id));
    }
}
