package com.company.Game;

import java.util.LinkedList;
import java.util.List;

public class ExBoard {
    private Board[][] boards = new Board[3][3];
    private char[][] avaliable = {{'A', 'A', 'A'}, {'A', 'A', 'A'}, {'A', 'A', 'A'}};
    private int fills = 0;
    private int lastx = -1, lasty = -1;

    public ExBoard() {
        this.fills = 0;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                boards[i][j] = new Board();
    }

    public ExBoard(ExBoard exboard) {
        this.fills = exboard.fills;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                boards[i][j] = new Board(exboard.boards[i][j]);
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                avaliable[i][j] = exboard.avaliable[i][j];
    }

    public boolean check(int xx, int xy, int x, int y) {
        if (!checkX(xx, xy)) {
            return false;
        }
        return boards[xx][xy].check(x, y);
    }

    private boolean checkX(int x, int y) {
        if (x > 2 || y > 2)
            return false;
        if (x < 0 || y < 0)
            return false;
        if (avaliable[x][y] != 'A')
            return false;
        if (x == lastx && y == lasty)
            return true;
        if (lastx == -1)
            return true;
        return false;
    }

    public void play(char player, int xx, int xy, int x, int y) {
        if (boards[xx][xy].isEmpty(x, y)) {
            boards[xx][xy].play(player, x, y);
            update(player, xx, xy);
            if (avaliable[x][y] != 'A') {
                lastx = lasty = -1;
            } else {
                lastx = x;
                lasty = y;
            }
        }
    }

    public String getaval() {
        String s = "";
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j)
                s += avaliable[i][j];
        return s;
    }

    public void update(char player, int x, int y) {
        if (boards[x][y].isWin(player)) {
            avaliable[x][y] = Character.toUpperCase(player);
            ++fills;
        } else if (boards[x][y].isFinished()) {
            avaliable[x][y] = 'N';
            ++fills;
        }
    }

    public boolean isWin(char player) {
        for (int i = 0; i < 3; ++i) {
            boolean temp = false;
            for (int j = 0; j < 3; ++j) {
                if (!boards[i][j].isWin(player)) {
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
                if (!boards[j][i].isWin(player)) {
                    temp = false;
                    break;
                } else
                    temp = true;
            }
            if (temp)
                return true;
        }

        if (boards[0][0].isWin(player) && boards[1][1].isWin(player) && boards[2][2].isWin(player))
            return true;

        if (boards[0][2].isWin(player) && boards[1][1].isWin(player) && boards[2][0].isWin(player))
            return true;
        return false;
    }

    public boolean isFinished() {
        return fills == 9;
    }

    private char otherPlayer(char player) {
        if (player == 'x')
            return 'o';
        return 'x';
    }

    @Override
    public String toString() {
        System.out.println(lastx + "   " + lasty);
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < 3; ++k) {
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    sb.append(boards[k][j].getLine(i));
                    sb.append("|");
                }
                sb.append("\n");
            }
            if (k < 2) {
                sb.append("  _________||___________||___________||");
                sb.append("\n           ||           ||           ||");
                sb.append("\n");
            }
        }
        sb.append((lastx + 1) + "  " + (lasty + 1));
        return sb.toString();
    }

    public List<ExBoard> getPossibleNextBoards(char nextPlayer) {
        List<ExBoard> nextBoards = new LinkedList<>();
        ExBoard tempex;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (checkX(i, j)) {
                    List<Integer> temp = new LinkedList<>();
                    temp = boards[i][j].getPossibleNextBoards(nextPlayer);
                    for (int k = 0; k < temp.size(); ++k) {
                        tempex = new ExBoard(this);
                        tempex.play(nextPlayer, i, j, temp.get(k), temp.get(k + 1));
                        ++k;
                        nextBoards.add(tempex);
                    }
                }
            }
        }
        return nextBoards;
    }

    public int exevaluate(char player) {
        int eval = 0;
        if (isWin(player))
            return Integer.MAX_VALUE;
        if (isWin(otherPlayer(player)))
            return Integer.MIN_VALUE;
        boolean temp = false;
        for (int i = 0; i < 3; ++i) {
            temp = false;
            for (int j = 0; j < 3; ++j) {
                if (avaliable[i][j] == Character.toUpperCase(otherPlayer(player))) {
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
                if (avaliable[j][i] == Character.toUpperCase(otherPlayer(player))) {
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
            if (avaliable[i][i] == Character.toUpperCase(otherPlayer(player))) {
                temp = false;
                break;
            }
        }
        if (temp)
            ++eval;

        temp = true;
        for (int i = 0; i < 3; ++i) {
            if (avaliable[i][2 - i] == Character.toUpperCase(otherPlayer(player))) {
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
                if (avaliable[i][j] == Character.toUpperCase(otherPlayer(player))) {
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
                if (avaliable[j][i] == Character.toUpperCase(otherPlayer(player))) {
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
            if (avaliable[i][i] == Character.toUpperCase(otherPlayer(player))) {
                temp = false;
                break;
            }
        }
        if (temp)
            --eval;

        temp = true;
        for (int i = 0; i < 3; ++i) {
            if (avaliable[i][2 - i] == Character.toUpperCase(otherPlayer(player))) {
                temp = false;
                break;
            }
        }
        if (temp)
            --eval;
        return eval;
    }

    public int evaluate(char player) {
        int eval = 0;
        if (isWin(player))
            return Integer.MAX_VALUE;
        if (isWin(otherPlayer(player)))
            return Integer.MIN_VALUE;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (i != lastx && j != lasty) {
                    eval += boards[i][j].evaluate(player);
                }
            }
        }
        if (lastx == -1 && lasty == -1)
            eval -= 10;
        else
            eval -= boards[lastx][lasty].evaluate(otherPlayer(player));
        eval += exevaluate(player);
        return eval;
    }
}


