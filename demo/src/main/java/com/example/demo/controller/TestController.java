package com.example.demo.controller;

import com.example.demo.model.Board;
import com.example.demo.model.Opponent;
import com.example.demo.model.SaveGameException;
import com.example.demo.model.Ship;

import java.io.*;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Long.SIZE;

public class TestController implements Serializable {
    private Board pBoard;
    private Opponent computerOpponent;
    private Board opponentBoard;
    private char[][] playerBoard;
    private Ship[] pFlota = new Ship[10];
    private Random random;

    public TestController() {
        opponentBoard = new Board();
        pBoard = new Board();
        pBoard.setFlota();
        pFlota = pBoard.getFlota();
        random = new Random();
        computerOpponent = new Opponent();

    }

    public void playGame() {
        placeShips();
        System.out.println("All ships placed. Ready to start the game!");
        boolean gameOver = false;
        while (!gameOver) {  // Replace this with proper game over conditions
            playerTurn();
            try {
                saveGame("data_save");
            } catch (SaveGameException e) {
                System.out.println(e.getMessage());
            }
            try {
                Thread.sleep(2000);  // Delay for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            computerOpponent.attack(pBoard);
            try {
                saveGame("data_save");
            } catch (SaveGameException e) {
                System.out.println(e.getMessage());
            }
            // Check for game over conditions here
        }
    }

    public void saveGame(String fileName) throws SaveGameException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data_save"))) {
            oos.writeObject(playerBoard);
            oos.writeObject(computerOpponent.getBoard());
            System.out.println("Game saved.");
        } catch (IOException e) {
            throw new SaveGameException("Error saving game: " + e.getMessage());
        }
    }

    public void loadGame(String fileName) throws SaveGameException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data_save"))) {
            playerBoard = ((Board) ois.readObject()).getBoard();
            opponentBoard = (Board) ois.readObject();
            computerOpponent = new Opponent();
            computerOpponent.setBoard(opponentBoard);
            System.out.println("Game loaded.");
        } catch (IOException | ClassNotFoundException e) {
            throw new SaveGameException("Error loading game: " + e.getMessage());
        }
    }

    private void printBoard(char[][] board) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void placeShips() {
        Scanner scanner = new Scanner(System.in);
        for (Ship ship : pFlota) {
            int num = 1;
            boolean placed = false;
            while (!placed) {
                System.out.println("Placing " + " of size " + ship.getSize());
                System.out.print("Enter X coordinate: ");
                int x = scanner.nextInt();
                System.out.print("Enter Y coordinate: ");
                int y = scanner.nextInt();
                System.out.print("Horizontal? (1(yes)/2(false)): ");
                int horizontal = scanner.nextInt();
                if (pBoard.canPlaceShip(ship, x, y, horizontal)) {
                    pBoard.placeShip(ship, x, y, horizontal);
                    placed = true;
                    num++;
                } else {
                    System.out.println("Invalid position. Try again.");
                }
            }
        }
        System.out.println("Setting up opponent's board...");
        computerOpponent.setupDefaultMap();
        System.out.println("Opponent board set up complete.");
    }


    public void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your turn to attack!");
        boolean validAttack = false;
        while (!validAttack) {
            System.out.print("Enter X coordinate: ");
            int x = scanner.nextInt();
            System.out.print("Enter Y coordinate: ");
            int y = scanner.nextInt();
            validAttack = computerOpponent.getBoard().receiveAttack(x, y);
        }
    }



}
