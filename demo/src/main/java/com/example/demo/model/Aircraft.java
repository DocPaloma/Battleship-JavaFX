package com.example.demo.model;


import java.awt.*;

public class Aircraft extends Ship{

    public Aircraft(){
        lifePoints = 4;
        size = 4;
        isAlive = true;
        direction = 0;
        shipChar = 'A';

    }

    public void draw(Graphics2D g, int x, int y, boolean horizontal) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }
}
