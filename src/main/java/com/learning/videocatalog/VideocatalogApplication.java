package com.learning.videocatalog;

import com.learning.videocatalog.model.TvShowData;
import com.learning.videocatalog.model.TvShowEpisodeData;
import com.learning.videocatalog.model.TvShowSeasonData;
import com.learning.videocatalog.principal.ConsoleApplication;
import com.learning.videocatalog.repository.EpisodeRepository;
import com.learning.videocatalog.repository.TvShowRepository;
import com.learning.videocatalog.service.ConsumeAPI;
import com.learning.videocatalog.service.ConvertData;
import com.learning.videocatalog.service.ConvertDataImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class VideocatalogApplication implements CommandLineRunner {

    @Autowired
    private TvShowRepository tvShowRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    static void main(String[] args) {
        SpringApplication.run(VideocatalogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConsoleApplication consoleApplication = new ConsoleApplication(tvShowRepository, episodeRepository);
        consoleApplication.startConsoleProgram();

        searchUnmodifiableTvShow();
    }

    private void searchUnmodifiableTvShow(){
		var consumeAPI = new ConsumeAPI();
		var json = consumeAPI.getData("https://www.omdbapi.com/?t=arrow&apikey=6585022c");
		System.out.println(json);

		ConvertData converter = new ConvertDataImpl();
		var tvShowData = converter.getData(json, TvShowData.class);
		System.out.println(tvShowData);

		json = consumeAPI.getData("https://www.omdbapi.com/?t=arrow&&season=1&episode=1&apikey=6585022c");
		var tvShowEpisodeData = converter.getData(json, TvShowEpisodeData.class);
		System.out.println(tvShowEpisodeData);

		var listTvShowSeasonData = new ArrayList<>();
		for (int i = 1; i < tvShowData.totalSeasons(); i++) {
			json = consumeAPI.getData("https://www.omdbapi.com/?t=arrow&&season="+ i +"&apikey=6585022c");
			listTvShowSeasonData.add(converter.getData(json, TvShowSeasonData.class));
		}
		listTvShowSeasonData.forEach(System.out::println);
    }
}
