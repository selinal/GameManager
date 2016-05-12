package ru.botgame.botexecutor;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static String gameDir;

    public Logger(String gameDir) {
        this.gameDir = gameDir;
    }

    // ����������� ����
    public static void writeTurnToLog(String turn) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(gameDir + "\\gameLog.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            writer.write(turn + "\n");
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    // ������ ���������� ����
    public void writeGameResult(GameResult result, String winnerBotLocation) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(gameDir + "\\results.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            writer.write(result + ": " + winnerBotLocation);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
