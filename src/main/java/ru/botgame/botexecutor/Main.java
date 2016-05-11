package ru.botgame.botexecutor;

import java.io.IOException;

public class Main {
    public static final int BOARD_SIZE = 19;
    public static final String XXXXX = "xxxxx";
    public static final String OOOOO = "ooooo";
    private Bot bot1;
    private Bot bot2;

    public static void main(String[] args) {
        Main m = new Main();
        m.run("bots\\d32bot1.exe", "bots\\d32bot2.exe");
    }

    private void run(String bot1Location, String bot2Location) {
        bot1 = new Bot(bot1Location);
        bot2 = new Bot(bot2Location);

        String board = null;
        try {
            bot1.init();
            bot2.init();
            board = "19" + new String(new char[361]).replace("\0", "-");
            String prevBoard;

            while (true) {
                prevBoard = board;
                bot1.getWriter().write(board + "\n");
                bot1.getWriter().flush();
                while (!bot1.getReader().ready()) {
                }
                board = bot1.getReader().readLine();

                System.out.println("bot1: " + board);
                validateBoard(board, prevBoard, bot1);

                prevBoard = board;
                bot2.getWriter().write(board + "\n");
                bot2.getWriter().flush();
                while (!bot2.getReader().ready()) {
                }
                board = bot2.getReader().readLine();

                System.out.println("bot2: " + board);
                validateBoard(board, prevBoard, bot2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GameOverException e) {
            writeGameResult(e.getResult(), e.getWinner(), board);
        } finally {
            bot1.kill();
            bot2.kill();
        }
    }

    private void writeGameResult(GameResult result, Bot winner, String board) {
        // todo:implement
        System.out.println(result + ": " + winner);
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            System.out.println(board.substring(2 + i * BOARD_SIZE, 2 + i * BOARD_SIZE + BOARD_SIZE));
        }
    }

    private void validateBoard(String board, String prevBoard, Bot bot) throws GameOverException {
        // здесь всякие проверки
        // 1. игра закончена по заполнению доски
        boolean emptyFieldExists = false;
        for (char c : board.toCharArray()) {
            if (c == '-') {
                emptyFieldExists = true;
                break;
            }
        }
        if (!emptyFieldExists)
            throw new GameOverException(GameResult.DRAW, null);
        // 2. невалидный ход соперника - технический проигрыш
        int difs = 0;
        char[] boardChars = board.toCharArray();
        char[] prevBoardChars = prevBoard.toCharArray();
        for (int i = 0; i < boardChars.length; i++) {
            if (boardChars[i] != prevBoardChars[i])
                difs++;
        }
        if (difs != 1)
            throw new GameOverException(GameResult.WIN, bot == bot1 ? bot2 : bot1);
        // 3. победа по условиям
        String[] horiz = new String[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            int startPos = 2 + i * BOARD_SIZE;
            horiz[i] = board.substring(startPos, startPos + BOARD_SIZE);
        }
        String[] vert = new String[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            int startPos = 2 + i;
            StringBuilder sb = new StringBuilder(BOARD_SIZE);
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(boardChars[startPos]);
                startPos += BOARD_SIZE;
            }
            vert[i] = sb.toString();
        }
        char[][] cells = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            cells[i] = horiz[i].toCharArray();
        }

        //   \\\\\\\
        String[] diag1 = new String[BOARD_SIZE * 2 - 1];
        for (int y = 0; y < BOARD_SIZE; y++) {
            StringBuilder sb = new StringBuilder(BOARD_SIZE);
            int ty = y;
            for (int x = 0; ty < BOARD_SIZE; x++) {
                sb.append(cells[ty++][x]);
            }
            diag1[y] = sb.toString();
        }
        for (int x = 1; x < BOARD_SIZE; x++) {
            StringBuilder sb = new StringBuilder(BOARD_SIZE);
            int tx = x;
            for (int y = 0; tx < BOARD_SIZE; y++) {
                sb.append(cells[y][tx++]);
            }
            diag1[x + BOARD_SIZE - 1] = sb.toString();
        }

        //   //////
        String[] diag2 = new String[BOARD_SIZE * 2 - 1];
        for (int x = BOARD_SIZE - 1; x >= 0; x--) {
            StringBuilder sb = new StringBuilder(BOARD_SIZE);
            int tx = x;
            for (int y = 0; tx >=0; y++) {
                sb.append(cells[y][tx--]);
            }
            diag2[BOARD_SIZE - x - 1] = sb.toString();
        }
        for (int x = 1; x < BOARD_SIZE; x++) {
            StringBuilder sb = new StringBuilder(BOARD_SIZE);
            int tx = x;
            for (int y = BOARD_SIZE - 1; tx < BOARD_SIZE; y--) {
                sb.append(cells[y][tx++]);
            }
            diag2[x + BOARD_SIZE - 1] = sb.toString();
        }

        for (String line: horiz)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, bot1);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, bot2);
        }
        for (String line: vert)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, bot1);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, bot2);
        }
        for (String line: diag1)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, bot1);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, bot2);
        }
        for (String line: diag2)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, bot1);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, bot2);
        }

    }
}
