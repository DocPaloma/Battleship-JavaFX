package com.example.demo.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShipDrawer {

    public void drawShip(GraphicsContext gc, int x, int y, char type, boolean isHit, boolean isSunk) {
        gc.setFill(Color.LIGHTGRAY); // Default to water color
        if (type == 'A') {
            gc.setFill(Color.GREEN);
        } else if (type == 'D') {
            gc.setFill(Color.BLUE);
        } else if (type == 'F') {
            gc.setFill(Color.YELLOW);
        } else if (type == 'S') {
            gc.setFill(Color.ORANGE);
        }

        if (isSunk) {
            gc.setFill(Color.RED);
        } else if (isHit) {
            gc.setFill(Color.DARKRED);
        }

        gc.fillRect(x * 30, y * 30, 30, 30); // Draw a 30x30 square for each cell
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x * 30, y * 30, 30, 30);
    }

    public void drawWater(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(x * 30, y * 30, 30, 30);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(x * 30, y * 30, 30, 30);
    }
}
