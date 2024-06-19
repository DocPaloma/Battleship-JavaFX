package com.example.demo.model;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.List;

public abstract class Ship {
    protected int lifePoints;
    protected int size;
    protected boolean isAlive;
    protected int direction;
    protected char shipChar;

    public Ship() {

    }

    public boolean checkIsSunk(){
        return lifePoints == 0;
    }



    public int takeDamage(){
        lifePoints = lifePoints - 1;
        return lifePoints;
    }

    public void setDirVert(){
        direction = 1;
    }

    public void setDirHor(){
        direction = 2;
    }

    public int getSize(){
        return size;
    }

    public int getDir(){
        return direction;
    }

    public char getShipChar() {
        return shipChar;
    }

    public abstract void draw(Graphics2D g, int x, int y, boolean horizontal);
}
