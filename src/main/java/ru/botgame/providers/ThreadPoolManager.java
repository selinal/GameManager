package ru.botgame.providers;

import ru.botgame.botexecutor.Main;
import ru.botgame.entities.Meeting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created on 23.04.2016.
 */
public class ThreadPoolManager {

    private int threadPoolCapacity = 1;
    private long awaitTimeMinutes = 1;

    public ThreadPoolManager(int threadPoolCapacity, long awaitTimeMinutes) {
        this.threadPoolCapacity = threadPoolCapacity;
        this.awaitTimeMinutes = awaitTimeMinutes;
    }

    public void startGames(List<Meeting> meetingList) {
        Date start = new Date();
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCapacity, new DemonThreadFactory());
        for (final Meeting meeting : meetingList) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {

                    Main m = new Main();
                    m.run(meeting.getFirstBot().getLocation(), meeting.getSecondBot().getLocation());

                 }
            });
        }
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(awaitTimeMinutes, TimeUnit.MINUTES)){
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdownNow();
        }

        //games finished and we have some results

        System.out.println("The End.\n" /*+ results*/);
//        System.err.println((new Date().getTime() - start.getTime()));
//        System.err.println((new Date().getTime() - start.getTime())/1000);
    }
}
