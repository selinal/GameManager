package ru.botgame.providers;

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
                    //TODO: this is test code. Remove it!!! Insert here code for calling bots game
                    int time = (int) Math.abs(Math.random() * 5000);
                    time = time < 5_000 ? time : 5_000;
                    System.out.println(Thread.currentThread().getName() + " started " + time);
                    //useful methods
                    System.out.println(meeting.getFirstBot().getLocation());
                    System.out.println(meeting.getSecondBot().getLocation());
                    System.out.println(meeting.getMeetingLocation());
                    //
//                    try {
//                        Thread.sleep(time);
                        for (long i = 0, j = 0; i < 3_000_000_000L; i++) {
                            j++;
                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println(Thread.currentThread().getName() + " finished");
                    //TODO: this is test code. Remove it!!!
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

        //TODO: games finished and we have some results
        System.out.println("The End.\n" /*+ results*/);
        System.out.println((new Date().getTime() - start.getTime()));
        System.out.println((new Date().getTime() - start.getTime())/1000);
    }
}
