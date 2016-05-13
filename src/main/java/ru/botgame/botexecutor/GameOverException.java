package ru.botgame.botexecutor;

/**
 * Created with IntelliJ IDEA.
 * User: yanzin-sg
 * Date: 11.05.16
 * Time: 2:15
 * To change this template use File | Settings | File Templates.
 */
public class GameOverException extends Exception {
    private GameResult result;
    private String winnerName;

    public GameOverException(GameResult result, String name) {
        this.result = result;
        this.winnerName = name;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }
}
