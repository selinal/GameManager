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

    public void init() throws IOException {
        process = Runtime.getRuntime().exec(botLocation);

        reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 1024);
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()), 1024);
    }

    @Override
    public String toString() {
        return "Bot{" +
                "botLocation='" + botLocation + '\'' +
                '}';
    }

    public BufferedReader getReader() {
        return reader;
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public void kill() {
        process.destroyForcibly();
    }

    public Process getProcess() {
        return process;
    }
}