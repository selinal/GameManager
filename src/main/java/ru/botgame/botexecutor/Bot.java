package ru.botgame.botexecutor;

import java.io.*;

/**
 * Created by SBT-Khalimov-RR on 20.04.2016.
 */
class Bot {
    private Process process;

    private BufferedReader reader;
    private BufferedWriter writer;
    private final String botLocation;

    Bot(String botLocation) {
        this.botLocation = botLocation;
    }

    public void init() throws IOException, GameOverException {
        process = Runtime.getRuntime().exec("cmd /c \"" + botLocation + File.separator + "run.bat\"");

        if (process == null)
            throw new GameOverException(GameResult.WIN, null);
        if (!process.isAlive())
            throw new GameOverException(GameResult.WIN, null);

        reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()), 1024);
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() { return writer; }

    public String getBotLocation() { return botLocation; }

    public void kill() {
        process.destroyForcibly();
    }

    public Process getProcess() { return process; }
}