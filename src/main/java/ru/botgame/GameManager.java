package ru.botgame;

import ru.botgame.entities.Bot;
import ru.botgame.entities.Meeting;
import ru.botgame.providers.BotsProvider;
import ru.botgame.providers.MeetingProvider;
import ru.botgame.providers.Standings;
import ru.botgame.providers.ThreadPoolManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class GameManager {

    public static void main(String[] args) {

        try {
            Properties projectProperties = new Properties();
            FileInputStream in = new FileInputStream("resources" + File.separator + "config.properties");
            projectProperties.load(in);
            in.close();

            String botsDirectory = projectProperties.getProperty("sbt.root.bots.directory");
            String resultDirectory = projectProperties.getProperty("sbt.root.results.directory");
            int capacity = Integer.parseInt(projectProperties.getProperty("sbt.thread.pool.capacity"));
            long awaitTime = Integer.parseInt(projectProperties.getProperty("sbt.thread.termination.time.minutes"));

            BotsProvider botsProvider = new BotsProvider(botsDirectory);
            List<Bot> bots = botsProvider.getBots();

            MeetingProvider meetingsProvider = new MeetingProvider(resultDirectory);
            List<Meeting> meetingList = meetingsProvider.getMeetingList(bots);

            ThreadPoolManager threadPoolManager = new ThreadPoolManager(capacity, awaitTime);
            threadPoolManager.startGames(meetingList);

            Standings standings = new Standings(resultDirectory, bots, meetingList);
            standings.calculateStandings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
