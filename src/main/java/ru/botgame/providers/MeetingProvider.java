package ru.botgame.providers;

import ru.botgame.entities.Bot;
import ru.botgame.entities.Meeting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 23.04.2016.
 */
public class MeetingProvider {

    private String resultDirectory;

    public MeetingProvider(String resultDirectory) {
        this.resultDirectory = resultDirectory;
    }

    public List<Meeting> getMeetingList(List<Bot> bots) {
        List<Meeting> resultDirectories = new ArrayList<>(bots.size() * (bots.size() - 1));
        File f = new File(resultDirectory);
        f.mkdir();
        for (int i = 0; i < bots.size(); i++) {
            for (int j = i + 1; j < bots.size(); j++) {
                Bot first = bots.get(i);
                Bot second = bots.get(j);
                Meeting meeting = new Meeting(first, second, resultDirectory);
                resultDirectories.add(meeting);
                f = new File(meeting.getMeetingLocation());
                f.mkdir();
                meeting = new Meeting(second, first, resultDirectory);
                f = new File(meeting.getMeetingLocation());
                f.mkdir();
                resultDirectories.add(meeting);
            }
        }
        return resultDirectories;
    }
}
