package ru.botgame.botexecutor;

/**
 * Created by SBT-Selin-AN on 12.05.2016.
 */
public class Refery {
    public static final int BOARD_SIZE = 19;
    public static final String XXXXX = "xxxxx";
    public static final String OOOOO = "ooooo";

    public void validateBoard(String board, String prevBoard, Bot currBot, Bot bot1, Bot bot2) throws GameOverException {
        // ����� ������ ��������
        // 1. ���� ��������� �� ���������� �����
        boolean emptyFieldExists = false;
        for (char c : board.toCharArray()) {
            if (c == '-') {
                emptyFieldExists = true;
                break;
            }
        }
        if (!emptyFieldExists)
            throw new GameOverException(GameResult.DRAW, null);
        // 2. ���������� ��� ��������� - ����������� ��������
        int difs = 0;
        char[] boardChars = board.toCharArray();
        char[] prevBoardChars = prevBoard.toCharArray();
        for (int i = 0; i < boardChars.length; i++) {
            if (boardChars[i] != prevBoardChars[i])
                difs++;
        }
        if (difs != 1)
            throw new GameOverException(GameResult.WIN, /*currBot == bot1 ? bot2.getBotLocation() : bot1.getBotLocation()*/null);
        // 3. ������ �� ��������
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
                throw new GameOverException(GameResult.WIN, null/*bot1.getBotLocation()*/);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, null /*bot2.getBotLocation()*/);
        }
        for (String line: vert)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, null/*bot1.getBotLocation()*/);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, null /*bot2.getBotLocation()*/);
        }
        for (String line: diag1)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, null/*bot1.getBotLocation()*/);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, null /*bot2.getBotLocation()*/);
        }
        for (String line: diag2)
        {
            if (line.indexOf(XXXXX) >= 0)
                throw new GameOverException(GameResult.WIN, null/*bot1.getBotLocation()*/);
            if (line.indexOf(OOOOO) >= 0)
                throw new GameOverException(GameResult.WIN, null /*bot2.getBotLocation()*/);
        }
    }

}
