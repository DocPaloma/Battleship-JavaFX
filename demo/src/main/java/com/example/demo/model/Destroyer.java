package com.example.demo.model;

import java.awt.*;

public class Destroyer extends Ship{

    public Destroyer(){
        lifePoints = 2;
        size = 2;
        isAlive = true;
        direction = 0;
        shipChar = 'D';
    }

    public void draw(Graphics2D g, int x, int y, boolean horizontal) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }
}
