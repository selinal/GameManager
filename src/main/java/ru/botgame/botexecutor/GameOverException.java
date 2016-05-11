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
    private Bot winner;

    public GameOverException(GameResult result, Bot winner) {
        this.result = result;
        this.winner = winner;
    }

    public Bot getWinner() {
        return winner;
    }

    public void setWinner(Bot winner) {
        this.winner = winner;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }
}
