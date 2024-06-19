package com.example.demo.model;

import java.io.Serializable;

public class Board implements Serializable {
    private static final int boardSize= 10;
    private char[][] board = new char[boardSize][boardSize];
    private Ship[] flota = new Ship[10];
    private Ship[][] shipsGrid;

    public Board() {
        board = new char[10][10];
        shipsGrid = new Ship[10][10];
        initializeBoard();
    }

    public void initializeBoard(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                board[i][j] = '~';
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

    public boolean receiveAttack(int x, int y) {
        if (x < 0 || x >= 10 || y < 0 || y >= 10) {
            System.out.println("Attack out of bounds.");
            return false;
        }

        if (board[y][x] == '~') {
            board[y][x] = 'M';  // Miss
            System.out.println("Miss!");
            return false;
        } else if (board[y][x] == 'H' || board[y][x] == 'M') {
            System.out.println("Already attacked this position.");
            return false;
        } else {
            board[y][x] = 'H';  // Hit
            Ship hitShip = shipsGrid[y][x];
            hitShip.takeDamage();
            if (hitShip.checkIsSunk()) {
                System.out.println("You sunk an enemy ship !");
            } else {
                System.out.println("Hit!");
            }
            return true;
        }
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
    public boolean canPlaceShip(Ship ship, int row, int col, int horizontal) {
        int size = ship.getSize();

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

    //Posiciona los barcos segun su tipo
    public boolean placeShip(Ship ship, int x, int y, int horizontal) {
        int length = ship.getSize();
        if (canPlaceShip(ship, x, y, horizontal)) {
            for (int i = 0; i < length; i++) {
                if (horizontal == 1) {
                    shipsGrid[x][y + i] = ship;
                    board[x][y + i] = ship.getShipChar();
                } else {
                    shipsGrid[x + i][y] = ship;
                    board[x + i][y] = ship.getShipChar();
                }
            }
            return true;
        }
        return false;
    }

    public void setupDefaultMap(int mapNumber) {

        switch (mapNumber) {
            case 1:
                placeShip(new Destroyer(), 0, 0, 1);
                placeShip(new Submarine(), 2, 2, 2);
                placeShip(new Fragata(), 5, 5, 1);
                placeShip(new Aircraft(), 7, 1, 2);
                placeShip(new Destroyer(), 3, 9, 1);
                placeShip(new Submarine(), 2, 2, 2);
                placeShip(new Destroyer(), 0, 7, 1);
                placeShip(new Fragata(), 8, 5, 2);
                placeShip(new Fragata(), 3, 5, 1);
                placeShip(new Fragata(), 4, 5, 1);
                break;
            case 2:
                placeShip(new Destroyer(), 9, 8, 1);
                placeShip(new Submarine(), 1, 1, 2);
                placeShip(new Fragata(), 4, 6, 1);
                placeShip(new Aircraft(), 2, 3, 2);
                placeShip(new Submarine(), 6, 2, 1);
                placeShip(new Fragata(), 4, 7, 1);
                placeShip(new Fragata(), 4, 8, 1);
                placeShip(new Fragata(), 4, 9, 1);
                placeShip(new Destroyer(), 8, 8, 1);
                placeShip(new Destroyer(), 7, 8, 1);
                break;
            case 3:
                placeShip(new Destroyer(), 8, 0, 1);
                placeShip(new Submarine(), 3, 3, 2);
                placeShip(new Fragata(), 6, 6, 1);
                placeShip(new Aircraft(), 0, 7, 2);
                placeShip(new Fragata(), 4, 4, 1);
                break;
        }
    }


    public char[][] getBoard() {
        return board;
    }

    private void markSunkShip(Ship ship) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (shipsGrid[i][j] == ship) {
                    board[i][j] = 'W';
                }
            }
        }
    }

    public boolean isHit(int x, int y) {
        return board[x][y] == 'H' || board[x][y] == 'M' || board[x][y] == 'S';
    }

    public boolean isAllSunk() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (shipsGrid[i][j] != null && !shipsGrid[i][j].checkIsSunk()) {
                    return false;
                }
            }
        }
        return true;
    }

}
