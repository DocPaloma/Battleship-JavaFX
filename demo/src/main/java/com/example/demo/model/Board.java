package com.example.demo.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Long.SIZE;

public class Board implements Serializable {
    private static int boardSize= 10;
    private char[][] board = new char[boardSize][boardSize];
    private char[][] boardComp = new char[boardSize][boardSize];
    private Ship[] flota = new Ship[10];
    private Ship[] flotaComp = new Ship[10];
    private static Map<String, Ship> shipMap = new HashMap<>();


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

    public void initializeCompBoard(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                boardComp[i][j] = '~';
            }
        }
    }


    public boolean shoot(int row, int col, boolean validS, boolean hit){
        if(board[row][col] == 'M' || board[row][col] == 'X'){
            validS = false;
            hit = false;
            return validS;
        }else if(board[row][col]== 'A' || board[row][col]== 'D' || board[row][col]== 'F' || board[row][col]== 'S'){
            validS = true;
            hit = true;
            return hit;
        }
        else{
            validS = true;
            hit = false;
            return hit;
        }
    }

    //se imprime la tabla para pruebas de consola
    public void printBoard() {
        for (int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                System.out.print(board[i][j]+ " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //se crea la flota a poner del jugador
    public void setFlota() {
        flota[0] = new Aircraft();
        flota[1] = new Submarine();
        flota[2] = new Submarine();
        flota[3] = new Destroyer();
        flota[4] = new Destroyer();
        flota[5] = new Destroyer();
        flota[6] = new Fragata();
        flota[7] = new Fragata();
        flota[8] = new Fragata();
        flota[9] = new Fragata();

        this.flota = flota;
    }

    //metodo para verificar si la posición es valida
    private boolean canPlaceShip(Ship ship,int row, int col) {
        int size = ship.getSize();
        int horizontal = ship.getDir();

        if (horizontal == 1) {
            if (col + size > boardSize) return false;
            for (int i = 0; i < size; i++) {
                if (board[row][col + i] != '-') return false;
            }
        } else if(horizontal == 2){
            if (row + size > boardSize) return false;
            for (int i = 0; i < size; i++) {
                if (board[row + i][col] != '-') return false;
            }
        }

        return true;
    }

    //Metodo para posicionar los barcos si la posición es correcta
    private void placeShips() {
        setFlota();
        for (Ship ship : flota) {
            boolean placed = false;
            while (!placed) {
                int row = (int) (Math.random() * SIZE);
                int col = (int) (Math.random() * SIZE);
                if (canPlaceShip(ship, row, col)) {
                    placeShip(ship, row, col);
                    placed = true;
                }
            }
        }
    }

    //Posiciona los barcos segun su tipo
    private void placeShip(Ship ship, int row, int col) {
        int size = ship.getSize();
        int horizontal = ship.getDir();
        char shipChar;

        if (ship instanceof Destroyer) {
            shipChar = 'D';
        } else if (ship instanceof Aircraft) {
            shipChar = 'A';
        } else if (ship instanceof Fragata) {
            shipChar = 'F';
        } else if (ship instanceof Submarine) {
            shipChar = 'S';
        }

        if (horizontal == 1) {
            for (int i = 0; i < size; i++) {
                board[row][col + i] = 'S';
                shipMap.put(row + "," + (col + i), ship);
            }
        } else {
            for (int i = 0; i < size; i++) {
                board[row + i][col] = 'S';
                shipMap.put(row + "," + (col + i), ship);
            }
        }
    }


}
