package ru.botgame.botexecutor;

import java.io.IOException;

/**
 * Created by SBT-Selin-AN on 12.05.2016.
 */
public class Executor {
    private Bot bot1;
    private Bot bot2;
    private Logger gameLog;
    private Logger gameResultLog;
    private Refery refery;

    public static void main(String[] args) {
        Executor ex = new Executor();
        ex.run("bots\\d32bot1.exe", "bots\\d32bot2.exe", "bots");
        //ex.run("bots\\bot1.bat", "bots\\bot2.bat", "bots");
        //ex.run("bots\\BotCpp1.exe", "bots\\BotCpp1.exe", "bots");
        //ex.run("bots\\CSBot1.exe", "bots\\CSBot1.exe", "bots");
    }

    public void run(String bot1Location, String bot2Location, String gameDir) {
        bot1 = new Bot(bot1Location);
        bot2 = new Bot(bot2Location);
        gameLog = new Logger(gameDir);
        gameResultLog = new Logger(gameDir);
        refery = new Refery();

        String board = null;
        try {
            bot1.init();
            bot2.init();
            board = "19" + new String(new char[361]).replace("\0", "-");
            String prevBoard;

            while (true) {
                prevBoard = board;
                bot1.getWriter().write(board + "\n");
//                bot1.getWriter().flush();
                while (!bot1.getReader().ready()) {
                }
                board = bot1.getReader().readLine();
                board = bot1.getReader().readLine();

                gameLog.writeTurnToLog("bot1: " + board.replace("19", ""));
                refery.validateBoard(board, prevBoard, bot1, bot1, bot2);

                prevBoard = board;
                bot2.getWriter().write(board + "\n");
//                bot2.getWriter().flush();
                while (!bot2.getReader().ready()) {
                }
                board = bot2.getReader().readLine();

                gameLog.writeTurnToLog("bot2: " + board.replace("19", ""));
                refery.validateBoard(board, prevBoard, bot2, bot1, bot2);
            }
        } catch (GameOverException e) {
            gameResultLog.writeGameResult(e.getResult(), e.getWinnerLocation());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bot1.kill();
            bot2.kill();
        }
    }

}
