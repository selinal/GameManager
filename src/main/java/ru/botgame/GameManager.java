package ru.botgame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.botgame.entities.Bot;
import ru.botgame.entities.Meeting;
import ru.botgame.providers.*;

import java.io.IOException;
import java.util.List;

public class GameManager {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        BotsProvider botsProvider = context.getBean("botsProvider", BotsProvider.class);
        List<Bot> bots = botsProvider.getBots();

        MeetingProvider meetingsProvider = context.getBean("meetingsProvider", MeetingProvider.class);
        List<Meeting> meetingList = meetingsProvider.getMeetingList(bots);

        context.getBean("threadManager", ThreadPoolManager.class).startGames(meetingList);

        Standings standings = new Standings(meetingsProvider, botsProvider);
        try {
            standings.calculateStandings();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
