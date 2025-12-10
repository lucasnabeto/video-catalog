package com.learning.videocatalog.controller;

import com.learning.videocatalog.dto.EpisodeDTO;
import com.learning.videocatalog.dto.TvShowDTO;
import com.learning.videocatalog.service.TvShowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tvshows")
public class TvShowController {
    private final TvShowService tvShowtvShowService;

    public TvShowController(TvShowService tvShowtvShowService) {
        this.tvShowtvShowService = tvShowtvShowService;
    }

    @GetMapping
    public List<TvShowDTO> getTvShows() {
        return tvShowtvShowService.getAllTvShows();
    }

    @GetMapping("/top5")
    public List<TvShowDTO> getTop5TvShows() {
        return tvShowtvShowService.getTop5TvShows();
    }

    @GetMapping("/new-releases")
    public List<TvShowDTO> getNewReleasesFromTvShows() {
        return tvShowtvShowService.getMostRecentlyEpisodes();
    }

    @GetMapping("/{id}")
    public TvShowDTO getById(@PathVariable Long id) {
        return tvShowtvShowService.getTvShowById(id);
    }

    @GetMapping("/{id}/seasons/all")
    public List<EpisodeDTO> getAllEpisodesFromTvShow(@PathVariable Long id) {
        return tvShowtvShowService.getAllEpisodesFromTvShow(id);
    }

    @GetMapping("/{tvShowId}/seasons/{seasonId}")
    public List<EpisodeDTO> getAllEpisodesFromTvShowSeason(@PathVariable Long tvShowId, @PathVariable Long seasonId) {
        return tvShowtvShowService.getAllEpisodesFromTvShowSeason(tvShowId, seasonId);
    }

    @GetMapping("/genre/{genre}")
    public List<TvShowDTO> getTvShowsByGenre(@PathVariable String genre) {
        return tvShowtvShowService.getTvShowsByGenre(genre);
    }

    @GetMapping("/{id}/seasons/top")
    public List<EpisodeDTO> getTop5EpisodesFromTvShow(@PathVariable Long id) {
        return tvShowtvShowService.getTop5EpisodesFromTvShow(id);
    }
}
