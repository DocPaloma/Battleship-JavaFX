package com.example.demo.model;



import java.lang.reflect.Array;

public class Board {
    private static int boardSize= 10;
    private char[][] board = new char[boardSize][boardSize];
    private Object[] flota = new Object[10];

    public Board(){
        initializeBoard();
    }

    public void initializeBoard(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                board[i][j] = '~';
            }
        }
    }

    public boolean shoot(int row, int col, boolean validS){
        if(board[row][col] == 'M' || board[row][col] == 'X'){
            validS = false;
            return validS;
        }
        else{
            validS = true;
            return validS;
        }
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                System.out.print(board[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public void setFlota(Ship[] flota) {
        Aircraft s1 = new Aircraft();

        
        this.flota = flota;
    }
}
