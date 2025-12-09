package com.learning.videocatalog.principal;

import com.learning.videocatalog.model.*;
import com.learning.videocatalog.repository.EpisodeRepository;
import com.learning.videocatalog.repository.TvShowRepository;
import com.learning.videocatalog.service.ConsumeAPI;
import com.learning.videocatalog.service.ConvertData;
import com.learning.videocatalog.service.ConvertDataImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ConsoleApplication {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumeAPI consumeAPI = new ConsumeAPI();
    private final ConvertData converter = new ConvertDataImpl();

    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private final TvShowRepository tvShowRepository;
    private final EpisodeRepository episodeRepository;

    private TvShowData tvShowData;
    private final List<TvShowSeasonData> listTvShowSeasonData = new ArrayList<>();
    private List<TvShow> tvShowsFromDatabase = new ArrayList<>();

    public ConsoleApplication(TvShowRepository tvShowRepository, EpisodeRepository episodeRepository) {
        this.tvShowRepository = tvShowRepository;
        this.episodeRepository = episodeRepository;
    }

    public void startConsoleProgram() {
        System.out.println("Enter the name of the TV Show that you are searching for:");
        var tvShowName = scanner.nextLine().replace(" ", "+");

        var json = getTvShowData(tvShowName);
        var allEpisodes = getTvShowSeasonData(tvShowName, json);
        listTop5TvShows(allEpisodes);
        var episodeList = getFullListOfEpisodesFromTvShow();
        filterEpisodesAfterSpecificReleaseYear(episodeList);
        findEpisodeByPartialTitle(episodeList);
        getRatingStats(episodeList);

        saveEpisodesToDatabase(tvShowData, episodeList);
        getAllTvShowsFromDatabase();
        getSpecificTvShowFromDatabase();
        getAllTvShowsEpisodesFromDatabase();

        scanner.close();
    }

    private String getTvShowData(String tvShowName) {
        var json = consumeAPI.getData(URL + tvShowName + API_KEY);
        tvShowData = converter.getData(json, TvShowData.class);
        System.out.println(tvShowData);

        return json;
    }

    private List<TvShowEpisodeData> getTvShowSeasonData(String tvShowName, String json) {
        for (int i = 1; i <= tvShowData.totalSeasons(); i++) {
            json = consumeAPI.getData(URL + tvShowName + "&season=" + i + API_KEY);
            listTvShowSeasonData.add(converter.getData(json, TvShowSeasonData.class));
        }
        listTvShowSeasonData.forEach(System.out::println);

//        OPTION 1 (WORST):
//        for(int i = 0; i < tvShowData.totalSeasons(); i++){
//            List<TvShowEpisodeData> episodes = listTvShowSeasonData.get(i).episodes();
//            for (int j = 0; j < episodes.size(); j++) {
//                System.out.println(episodes.get(j).title());
//            }
//        }

//      OPTION 2 (BEST):
        listTvShowSeasonData.forEach(season -> season.episodes()
                .forEach(episode -> System.out.println(episode.title()))
        );

        return listTvShowSeasonData.stream()
                .flatMap(season -> season.episodes().stream())
                .collect(Collectors.toList()); // Returns a modifiable list
//              .toList(); // Returns an unmodifiable list
    }

    private void listTop5TvShows(List<TvShowEpisodeData> allEpisodes) {
        System.out.println("Top 5 Episodes:");
        allEpisodes.stream()
                .filter(episode -> !episode.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(TvShowEpisodeData::rating).reversed())
                .limit(5)
                .forEach(episode -> System.out.println(episode.title() + " - Rating: " + episode.rating()));
    }

    private List<Episode> getFullListOfEpisodesFromTvShow() {
        List<Episode> episodeList = listTvShowSeasonData.stream()
                .flatMap(season -> season.episodes()
                        .stream()
                        .map(episode -> new Episode(season.seasonNumber(), episode)))
                .toList();
        episodeList.forEach(System.out::println);

        return episodeList;
    }

    private void filterEpisodesAfterSpecificReleaseYear(List<Episode> episodeList) {
        System.out.println("Since what year do you want to see?");
        var releaseYear = scanner.nextInt();
        scanner.nextLine(); //It's necessary after reading an integer to clean the input buffer.
        LocalDate searchDate = LocalDate.of(releaseYear, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodeList.stream()
                .filter(episode -> episode.getReleaseDate() != null && episode.getReleaseDate().isAfter(searchDate))
                .forEach(episode -> System.out.println(
                        "Season: " + episode.getSeason()
                                + " - Episode: " + episode.getTitle()
                                + " - Release date: " + episode.getReleaseDate().format(formatter)
                ));
    }

    private void findEpisodeByPartialTitle(List<Episode> episodeList) {
        System.out.println("Write part of the episode's title:");
        var partialTitle = scanner.nextLine().toLowerCase();
        Optional<Episode> optionalEpisode = episodeList.stream()
                .filter(e -> e.getTitle().toLowerCase().contains(partialTitle))
                .findFirst();
        if (optionalEpisode.isPresent()) {
            System.out.println("The episode was found! Season: " + optionalEpisode.get().getSeason());
        } else {
            System.out.println("Episode not found!");
        }
    }

    private void getRatingStats(List<Episode> episodeList){
        var ratedEpisodesList = episodeList.stream()
                .filter(e -> e.getRating() > 0.0)
                .toList();

        Map<Integer, Double> seasonsRatings = ratedEpisodesList.stream()
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));
        System.out.println("Ratings per season: " + seasonsRatings);

        DoubleSummaryStatistics stats = ratedEpisodesList.stream()
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("All statistics: " + stats);
        System.out.println("Average rating: " + stats.getAverage());
        System.out.println("Best episode: " + stats.getMax());
        System.out.println("Worst episode: " + stats.getMin());
    }

    private void saveEpisodesToDatabase(TvShowData tvShowData, List<Episode> episodes) {
        TvShow tvShow = new TvShow(tvShowData);
        tvShow.setEpisodes(episodes);
        tvShowRepository.save(tvShow);
    }

    private void getAllTvShowsFromDatabase() {
        tvShowsFromDatabase = tvShowRepository.findAll();
        tvShowsFromDatabase.stream()
                .sorted(Comparator.comparing(TvShow::getGenre))
                .forEach(System.out::println);
    }

    private void getAllTvShowsEpisodesFromDatabase() {
        System.out.println("Type the name of one of the tv shows listed bellow:");
        getAllTvShowsFromDatabase();
        var tvShowName = scanner.nextLine();

        Optional<TvShow> desiredTvShow = tvShowsFromDatabase.stream()
                .filter(tvShow -> tvShow.getTitle()
                        .toLowerCase()
                        .contains(tvShowName))
                .findFirst();

        //TODO: finish this method!
        if (desiredTvShow.isPresent()) {
        }
    }

    private void getSpecificTvShowFromDatabase() {
        System.out.println("Type the name of one of the tv shows listed bellow:");
        getAllTvShowsFromDatabase();

        var tvShowName = scanner.nextLine();
        Optional<TvShow> desiredTvShow = tvShowRepository.findByTitleContainingIgnoreCase(tvShowName);

        if (desiredTvShow.isPresent()) {
            System.out.println("Desired Tv Show was found: " + desiredTvShow.get());
        } else {
            System.out.println("Not found!");
        }
    }
}
