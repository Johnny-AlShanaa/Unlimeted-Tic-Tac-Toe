package com.company.Game;

import java.util.Scanner;

public class Game {

    char computer = 'o';
    char human = 'x';
    ExBoard exBoard = new ExBoard();
    Computer c = new Computer(2);

    public void play() {
        System.out.println(exBoard);
        while (true) {
            humanPlay();
            System.out.println(exBoard);
            if (exBoard.isWin(human)) {
                System.out.println("finished you Win");
                return;
            }
            System.out.println(exBoard.getPossibleNextBoards(human).size());
            System.out.println(exBoard.getaval());
            if (exBoard.isFinished()) {
                System.out.println("finished equals");
                return;
            }
            exBoard = c.computerPlay(exBoard);
            System.out.println(exBoard);
            if (exBoard.isWin(computer)) {
                System.out.println("finished computer Win");
                return;
            }
            System.out.println(exBoard.getPossibleNextBoards(human).size());
            System.out.println(exBoard.getaval());
            if (exBoard.isFinished()) {
                System.out.println("finished equals");
                return;
            }
        }
    }

    private void humanPlay() {
        Scanner s = new Scanner(System.in);
        boolean temp = true;
        int xcol = 0, xrow = 0, col = 0, row = 0;
        while (temp) {
            System.out.print("Enter xrow xcolumn row column :  ");
            xrow = s.nextInt();
            xcol = s.nextInt();
            row = s.nextInt();
            col = s.nextInt();
            temp = !exBoard.check(xrow - 1, xcol - 1, row - 1, col - 1);
        }
        exBoard.play(human, xrow - 1, xcol - 1, row - 1, col - 1);
    }

    public static void main(String[] args) {
        String ss = "_______________________________________________________________________________________________";
        System.out.println(ss);
        Game g = new Game();
        g.play();
    }
}
