package com.learning.videocatalog.principal;

import com.learning.videocatalog.model.*;
import com.learning.videocatalog.service.ConsumeAPI;
import com.learning.videocatalog.service.ConvertData;
import com.learning.videocatalog.service.ConvertDataImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvertData converter = new ConvertDataImpl();

    private final String URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void showMenu() {
        System.out.println("Enter the name of the TV Show that you are searching for:");
        var tvShowName = scanner.nextLine().replace(" ", "+");

        var json = consumeAPI.getData(URL + tvShowName + API_KEY);
        var tvShowData = converter.getData(json, TvShowData.class);
        System.out.println(tvShowData);

        List<TvShowSeasonData> listTvShowSeasonData = new ArrayList<>();
        for (int i = 1; i <= tvShowData.totalSeasons(); i++) {
            json = consumeAPI.getData(URL + tvShowName + "&season=" + i + API_KEY);
            listTvShowSeasonData.add(converter.getData(json, TvShowSeasonData.class));
        }
        listTvShowSeasonData.forEach(System.out::println);

//        for(int i = 0; i < tvShowData.totalSeasons(); i++){
//            List<TvShowEpisodeData> episodes = listTvShowSeasonData.get(i).episodes();
//            for (int j = 0; j < episodes.size(); j++) {
//                System.out.println(episodes.get(j).title());
//            }
//        }

        listTvShowSeasonData.forEach(season -> season.episodes()
                .forEach(episode -> System.out.println(episode.title()))
        );

        List<TvShowEpisodeData> allEpisodes = listTvShowSeasonData.stream()
                .flatMap(season -> season.episodes().stream())
                .collect(Collectors.toList()); // Returns a modifiable list
//                .toList(); // Returns an unmodifiable list

        System.out.println("Top 5 Episodes:");
        allEpisodes.stream()
                .filter(episode -> !episode.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(TvShowEpisodeData::rating).reversed())
                .limit(5)
                .forEach(episode -> System.out.println(episode.title() + " - Rating: " + episode.rating()));

        List<Episode> episodeList = listTvShowSeasonData.stream()
                .flatMap(season -> season.episodes()
                        .stream()
                        .map(episode -> new Episode(season.seasonNumber(), episode)))
                .collect(Collectors.toList());
        episodeList.forEach(System.out::println);

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

        System.out.println("Write part of the episode's title:");
        var partialTitle = scanner.nextLine().toLowerCase();
        System.out.println("user filled with: " + partialTitle);
        Optional<Episode> optionalEpisode = episodeList.stream()
                .filter(e -> e.getTitle().toLowerCase().contains(partialTitle))
                .findFirst();
        if (optionalEpisode.isPresent()) {
            System.out.println("The episode was found!");
            System.out.println("Season: " + optionalEpisode.get().getSeason());
        } else {
            System.out.println("Episode not found!");
        }

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

        scanner.close();
    }

    private void listFilteredTvShows() {
        List<TvShowData> tvShowData = new ArrayList<>();
        List<TvShow> tvShows = new ArrayList<>();
        tvShows = tvShowData.stream()
                .map(TvShow::new)
                .toList();
        tvShows.stream()
                .sorted(Comparator.comparing(TvShow::getGenre))
                .forEach(System.out::println);
    }
}
