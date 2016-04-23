package ru.botgame;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.botgame.entities.Bot;
import ru.botgame.entities.Meeting;
import ru.botgame.providers.BotsProvider;
import ru.botgame.providers.MeetingProvider;
import ru.botgame.providers.ThreadPoolManager;

import java.util.List;

public class GameManager {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        List<Bot> bots = context.getBean("botsProvider", BotsProvider.class).getBots();
        List<Meeting> meetingList = context.getBean("meetingsProvider", MeetingProvider.class).getMeetingList(bots);
        context.getBean("threadManager", ThreadPoolManager.class).startGames(meetingList);
    }
}
