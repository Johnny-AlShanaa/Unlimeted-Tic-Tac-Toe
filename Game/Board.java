package com.company.Game;

import java.util.LinkedList;
import java.util.List;


public class Board {

    private char[][] grid = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
    private int fills;

    public Board() {
        fills = 0;
    }

    public Board(Board board) {
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board.grid[i], 0, grid[i], 0, 3);
        }
        this.fills = board.fills;
    }

    public List<Integer> getPossibleNextBoards(char nextPlayer) {
        List<Integer> nextBoards = new LinkedList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (grid[i][j] == ' ') {
                    nextBoards.add(i);
                    nextBoards.add(j);
                }
            }
        }
        return nextBoards;
    }

    public void play(char player, int x, int y) {
        grid[x][y] = player;
        fills++;
    }

    public int evaluate(char player) {
        int eval = 0;
        if (isWin(player))
            return Integer.MAX_VALUE;
        if (isWin(otherPlayer(player)))
            return Integer.MIN_VALUE;
        boolean temp = false;
        for (int i = 0; i < 3; ++i) {
            temp = false;
            for (int j = 0; j < 3; ++j) {
                if (grid[i][j] == otherPlayer(player)) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                ++eval;
        }

        for (int i = 0; i < 3; ++i) {
            temp = false;
            for (int j = 0; j < 3; ++j) {
                if (grid[j][i] == otherPlayer(player)) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                ++eval;
        }

        temp = true;
        for (int i = 0; i < 3; ++i) {
            if (grid[i][i] == otherPlayer(player)) {
                temp = false;
                break;
            }
        }
        if (temp)
            ++eval;

        temp = true;
        for (int i = 0; i < 3; ++i) {
            if (grid[i][2 - i] == otherPlayer(player)) {
                temp = false;
                break;
            }
        }
        if (temp)
            ++eval;

        player = otherPlayer(player);

        for (int i = 0; i < 3; ++i) {
            temp = false;
            for (int j = 0; j < 3; ++j) {
                if (grid[i][j] == otherPlayer(player)) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                --eval;
        }


        for (int i = 0; i < 3; ++i) {
            temp = false;
            for (int j = 0; j < 3; ++j) {
                if (grid[j][i] == otherPlayer(player)) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                --eval;
        }

        temp = true;
        for (int i = 0; i < 3; ++i) {
            if (grid[i][i] == otherPlayer(player)) {
                temp = false;
                break;
            }
        }
        if (temp)
            --eval;

        temp = true;
        for (int i = 0; i < 3; ++i) {
            if (grid[i][2 - i] == otherPlayer(player)) {
                temp = false;
                break;
            }
        }
        if (temp)
            --eval;
        return eval;
    }

    public boolean isWin(char player) {

        for (int i = 0; i < 3; ++i) {
            boolean temp = false;
            for (int j = 0; j < 3; ++j) {
                if (grid[i][j] != player) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                return true;
        }

        for (int i = 0; i < 3; ++i) {
            boolean temp = false;
            for (int j = 0; j < 3; ++j) {
                if (grid[j][i] != player) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                return true;
        }

        if (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player)
            return true;

        if (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player)
            return true;
        return false;
    }

    public boolean check(int x, int y) {
        return (x >= 0 && x < 3 && y >= 0 && y < 3 && grid[x][y] == ' ');
    }

    public boolean isFinished() {
        return fills == 9;
    }

    private char otherPlayer(char player) {
        if (player == 'x')
            return 'o';
        return 'x';
    }

    public boolean isEmpty(int x, int y) {
        if (grid[x][y] == ' ')
            return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(grid[i][j]);
                sb.append(" | ");
            }
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append('\n');
        }
        return sb.toString();
    }

    public String getLine(int i) {
        String s = "";
        for (int j = 0; j < 3; ++j) {
            s += " ";
            s += grid[i][j];
            s += " |";
        }
        return s;
    }
}
