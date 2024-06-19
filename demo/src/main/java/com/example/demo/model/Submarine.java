package com.example.demo.model;

import java.awt.*;
import java.util.List;

public class Submarine extends Ship{

    public  Submarine() {
        lifePoints = 3;
        size = 3;
        isAlive = true;
        direction = 0;
        shipChar = 'S';
    }

    public void draw(Graphics2D g, int x, int y, boolean horizontal) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }
}
