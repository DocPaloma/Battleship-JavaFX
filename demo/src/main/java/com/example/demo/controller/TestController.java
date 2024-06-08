package com.example.demo.controller;

import com.example.demo.model.Ship;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class TestController implements Serializable {
    private static final int SIZE = 5;
    private char[][] board;
    private char[][] ships;
    private Map<String, Ship> shipMap;

    public TestController(char[][] board, char[][] ships, Map<String, Ship> shipMap) {
        this.board = board;
        this.ships = ships;
        this.shipMap = shipMap;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        int hits = 0;

        while (hits < 3) {
            printBoard(board);
            System.out.println("Introduce las coordenadas (fila y columna) o escribe 'guardar' para guardar el juego:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("guardar")) {
                saveGame();
                System.out.println("Juego guardado.");
                continue;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
                continue;
            }

            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                System.out.println("Coordenadas fuera de los límites. Intenta de nuevo.");
            } else if (ships[row][col] == 'S' || ships[row][col] == 'D' || ships[row][col] == 'U') {
                System.out.println("¡Tocado!");

                // Reducir la vida del barco
                Ship hitShip = shipMap.get(row + "," + col);
                hitShip.takeDamage();

                if (hitShip.checkIsSunk()) {
                    System.out.println("¡Barco hundido!");
                }


                board[row][col] = 'X';
                hits++;
            } else {
                System.out.println("Agua.");
                board[row][col] = 'O';
            }
        }

        System.out.println("¡Has hundido todos los barcos!");
        scanner.close();
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.dat"))) {
            out.writeObject(board);
            out.writeObject(ships);
            out.writeObject(shipMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game.dat"))) {
            board = (char[][]) in.readObject();
            ships = (char[][]) in.readObject();
            shipMap = (Map<String, Ship>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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

    // Getters to retrieve the current state of the game for the main class
    public char[][] getBoard() {
        return board;
    }

    public char[][] getShips() {
        return ships;
    }

    public Map<String, Ship> getShipMap() {
        return shipMap;
    }
}
