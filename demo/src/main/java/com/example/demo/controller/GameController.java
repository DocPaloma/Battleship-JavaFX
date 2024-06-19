package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.view.ShipDrawer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import javafx.scene.control.Button;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

public class GameController {
    private static final int GRID_SIZE = 10;
    private Board playerBoard;
    private Board opponentBoard;
    private boolean playerTurn;
    private Stage primaryStage;
    private Label statusLabel;
    private ShipDrawer shipDrawer;
    private Canvas playerCanvas;
    private Canvas opponentCanvas;
    private boolean placingShips = true;
    private Ship currentShip;

    public GameController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.playerBoard = new Board();
        this.opponentBoard = new Board();
        this.shipDrawer = new ShipDrawer();
        this.playerCanvas = new Canvas(GRID_SIZE * 30, GRID_SIZE * 30);
        this.opponentCanvas = new Canvas(GRID_SIZE * 30, GRID_SIZE * 30);
        playerTurn = true; // Player starts first
    }

    public void startNewGame() {
        playerBoard = new Board();
        opponentBoard = new Board();
        setupGameBoard();
    }

    public void loadSavedGame() {
        try {
            loadGame("game_save.dat");
            setupGameBoard();
        } catch (SaveGameException e) {
            statusLabel.setText("No saved game found, starting a new game.");
            startNewGame();
        }
    }

    private void setupGameBoard() {
        BorderPane gamePane = new BorderPane();
        statusLabel = new Label("Place your ships!");

        drawBoard(playerCanvas.getGraphicsContext2D(), playerBoard, true);
        drawBoard(opponentCanvas.getGraphicsContext2D(), opponentBoard, false);

        playerCanvas.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / 30);
            int y = (int) (event.getY() / 30);
            handlePlaceShip(x, y);
        });

        opponentCanvas.setOnMouseClicked(event -> {
            int x = (int) (event.getX() / 30);
            int y = (int) (event.getY() / 30);
            handlePlayerMove(x, y);
        });

        gamePane.setLeft(playerCanvas);
        gamePane.setRight(opponentCanvas);
        gamePane.setBottom(statusLabel);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);

        Scene gameScene = new Scene(gamePane, 600, 400);
        primaryStage.setScene(gameScene);
        primaryStage.setTitle("Battleship Game");
    }

    private void handlePlaceShip(int x, int y) {
        if (placingShips) {
            if (playerBoard.placeShip(currentShip, x, y, 1)) { // Assuming horizontal placement for simplicity
                drawBoard(playerCanvas.getGraphicsContext2D(), playerBoard, true);
                currentShip = nextShipToPlace();
                if (currentShip == null) {
                    placingShips = false;
                    statusLabel.setText("All ships placed. Start attacking!");
                }
            } else {
                statusLabel.setText("Invalid position. Try again.");
            }
        }
    }

    private void handlePlayerMove(int x, int y) {
        if (!placingShips) {
            if (!opponentBoard.isHit(x, y)) {
                boolean hit = opponentBoard.receiveAttack(x, y);
                drawBoard(opponentCanvas.getGraphicsContext2D(), opponentBoard, false);
                if (opponentBoard.isAllSunk()) {
                    statusLabel.setText("Game Over! Player wins!");
                } else {
                    opponentMove();
                    drawBoard(playerCanvas.getGraphicsContext2D(), playerBoard, true);
                }
            } else {
                statusLabel.setText("Position (" + x + ", " + y + ") has already been attacked.");
            }

            // Save game automatically after each turn
            try {
                saveGame("game_save.dat");
            } catch (SaveGameException e) {
                statusLabel.setText("Error saving game: " + e.getMessage());
            }
        }
    }

    private void opponentMove() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
        } while (playerBoard.isHit(x, y));

        boolean hit = playerBoard.receiveAttack(x, y);
        drawBoard(playerCanvas.getGraphicsContext2D(), playerBoard, true);
        if (playerBoard.isAllSunk()) {
            statusLabel.setText("Game Over! Opponent wins!");
        }
    }

    private void drawBoard(GraphicsContext gc, Board board, boolean isPlayerBoard) {
        char[][] boardRepresentation = board.getBoard();
        BufferedImage bi = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (isPlayerBoard) {
                    if (boardRepresentation[i][j] == '~') {
                        shipDrawer.drawWater(gc, i, j);
                    } else {
                        boolean isHit = boardRepresentation[i][j] == 'H';
                        boolean isSunk = boardRepresentation[i][j] == 'S';
                        shipDrawer.drawShip(gc, i, j, boardRepresentation[i][j], isHit, isSunk);
                    }
                } else {
                    // For opponent board, only show water unless there's a hit or sunk ship
                    if (boardRepresentation[i][j] == '~' || boardRepresentation[i][j] == 'M') {
                        shipDrawer.drawWater(gc, i, j);
                    } else if (boardRepresentation[i][j] == 'H' || boardRepresentation[i][j] == 'S') {
                        boolean isSunk = boardRepresentation[i][j] == 'S';
                        shipDrawer.drawShip(gc, i, j, boardRepresentation[i][j], true, isSunk);
                    } else {
                        Ship ship = getShipBySymbol(boardRepresentation[i][j]);
                        if (ship != null) {
                            ship.draw(g, j * 30, i * 30, true); // Adjust orientation as needed
                        }
                    }
                }
            }
        }
    }

    private Ship nextShipToPlace() {
        // Define the order of ships to be placed. Here it's hardcoded for simplicity.
        if (currentShip instanceof Aircraft) {
            return new Destroyer();
        } else if (currentShip instanceof Destroyer) {
            return new Submarine();
        } else if (currentShip instanceof Submarine) {
            return new Fragata();
        } else if (currentShip == null) {
            return new Aircraft();
        }
        return null;
    }

    public void showInstructions() {
        VBox instructionPane = new VBox();
        instructionPane.setAlignment(Pos.CENTER);
        instructionPane.setSpacing(10);

        Label instructions = new Label("Battleship Instructions:\n\n" +
                "1. Place your ships on the left grid.\n" +
                "2. Click on the right grid to attack.\n" +
                "3. First to sink all opponent's ships wins.\n" +
                "4. Save and load your game progress automatically.\n" +
                "5. Symbols: ~ = Water, A = Aircraft, D = Destroyer, F = Fragata, S = Submarine, H = Hit, M = Miss, S = Sunk.");

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> primaryStage.setScene(primaryStage.getScene()));

        instructionPane.getChildren().addAll(instructions, backButton);

        Scene instructionScene = new Scene(instructionPane, 400, 300);
        primaryStage.setScene(instructionScene);
    }

    public void saveGame(String fileName) throws SaveGameException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(playerBoard);
            oos.writeObject(opponentBoard);
        } catch (IOException e) {
            throw new SaveGameException("Error saving game", e);
        }
    }

    public void loadGame(String fileName) throws SaveGameException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            playerBoard = (Board) ois.readObject();
            opponentBoard = (Board) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SaveGameException("Error loading game", e);
        }
    }

    private Ship getShipBySymbol(char symbol) {
        switch (symbol) {
            case 'A':
                return new Aircraft();
            case 'D':
                return new Destroyer();
            case 'S':
                return new Submarine();
            case 'F':
                return new Fragata();
            default:
                return null;
        }
    }
}
