package com.example.demo.model;

import java.util.Random;

public class Opponent {
    private Board board;
    private Random random;

    public Opponent(){
        board = new Board();
        random = new Random();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setupDefaultMap() {
        int mapNumber = random.nextInt(3) + 1;
        board.setupDefaultMap(mapNumber);
    }

    public void attack(Board playerBoard) {
        boolean validAttack = false;
        while (!validAttack) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            validAttack = playerBoard.receiveAttack(x, y);
        }
    }

}
