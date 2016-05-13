package ru.botgame.botexecutor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by SBT-Selin-AN on 12.05.2016.
 */
public class Executor {
    private Bot bot1;
    private Bot bot2;
    private Logger gameLog;
    private Logger gameResultLog;
    private Refery refery;
    private long timeout;
    private String board;

    public static void main(String[] args) {
        Executor ex = new Executor();
//        ex.run("bots\\d32bot1.exe", "bots\\d32bot2.exe", "bots");
        //ex.run("bots\\bot1.bat", "bots\\bot2.bat", "bots");
        //ex.run("bots\\BotCpp1.exe", "bots\\BotCpp1.exe", "bots");
        //ex.run("bots\\CSBot1.exe", "bots\\CSBot1.exe", "bots");
    }

    public void run(String bot1Location, String bot2Location, String gameDir, long timeout) {
        this.timeout = timeout;
        bot1 = new Bot(bot1Location);
        bot2 = new Bot(bot2Location);
        gameLog = new Logger(gameDir);
        gameResultLog = new Logger(gameDir);
        refery = new Refery();

        board = null;
        try {
            bot1.init();
            bot2.init();
            board = "19" + new String(new char[361]).replace("\0", "-");
            while (true) {
                doStep(bot1);
                doStep(bot2);
            }
        } catch (GameOverException e) {
            gameResultLog.writeGameResult(e.getResult(), e.getWinnerLocation());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bot1.kill();
            bot2.kill();
        }
    }

    private void doStep(Bot bot) throws IOException, GameOverException {
        String prevBoard = board;
        BufferedWriter writer = bot.getWriter();
        writer.write(board);
        writer.newLine();
        writer.flush();
        wait(bot);
        board = bot.getReader().readLine();
        System.out.println("bot1: " + board);
        gameLog.writeTurnToLog("bot1: " + board.replace("19", ""));
        refery.validateBoard(board, prevBoard, bot, bot1, bot2);
    }

    private void wait(Bot bot) throws IOException, GameOverException {
        long start = System.currentTimeMillis();
        while (!bot.getReader().ready()) {
            long current = System.currentTimeMillis();
            if(current - start > timeout){
                throw new GameOverException(GameResult.WIN, bot.getBotLocation());
            }
        }
    }

}
