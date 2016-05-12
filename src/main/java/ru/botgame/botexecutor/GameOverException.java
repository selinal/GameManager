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
    private String winnerLocation;

    public GameOverException(GameResult result, String botLocation) {
        this.result = result;
        this.winnerLocation = botLocation;
    }

    public String getWinnerLocation() {
        return winnerLocation;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public void setWinnerLocation(String winnerLocation) {
        this.winnerLocation = winnerLocation;
    }
}
