package com.example.demo.model;

import java.awt.*;

public class Fragata extends Ship{

    public Fragata(){
        lifePoints = 1;
        size = 1;
        isAlive = true;
        direction = 0;
        shipChar = 'F';
    }

    public void draw(Graphics2D g, int x, int y, boolean horizontal) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }
}
