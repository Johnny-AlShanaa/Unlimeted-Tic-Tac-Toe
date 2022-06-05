package com.company.Game;

import java.util.List;

public class Computer {

    int level;
    int depth;

    public Computer(int level) {
        this.level = level;
        if (level == 1)
            depth = 2;
        else if (level == 2)
            depth = 5;
        else if (level == 3)
            depth = Integer.MAX_VALUE;

    }

    public ExBoard computerPlay(ExBoard board) {
        List<ExBoard> boardsAvailable = board.getPossibleNextBoards('o');
        int max = minmax(1, boardsAvailable.get(0), Integer.MIN_VALUE, Integer.MAX_VALUE), index = 0;
        for (int i = 1; i < boardsAvailable.size(); ++i) {
            int temp = minmax(1, boardsAvailable.get(i), Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (temp > max) {
                max = temp;
                index = i;
            }
        }
        return boardsAvailable.get(index);
    }

    private int maxmin(int depth, ExBoard board, int A, int B) {
        if (board.isWin('o'))
            return Integer.MAX_VALUE;
        if (board.isWin('x'))
            return Integer.MIN_VALUE;
        if (this.depth == depth)
            return board.evaluate('o');
        if (board.isFinished())
            return 0;
        List<ExBoard> boards = board.getPossibleNextBoards('o');
        int temp;
        for (int i = 0; i < boards.size(); ++i) {
            temp = minmax(depth + 1, boards.get(i), A, B);
            if (temp > A)
                A = temp;
            if (A >= B)
                return B;
        }

        return A;
    }

    private int minmax(int depth, ExBoard board, int A, int B) {
        if (board.isWin('x'))
            return Integer.MAX_VALUE;
        if (board.isWin('o'))
            return Integer.MIN_VALUE;
        if (this.depth == depth)
            return board.evaluate('x');
        if (board.isFinished())
            return 0;
        List<ExBoard> boards = board.getPossibleNextBoards('x');
        int temp;
        for (int i = 0; i < boards.size(); ++i) {
            temp = maxmin(depth + 1, boards.get(i), A, B);
            if (temp < B)
                B = temp;
            if (B <= A)
                return A;
        }
        return B;
    }
}
