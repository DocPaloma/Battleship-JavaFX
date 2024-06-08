package com.example.demo.model;

import java.lang.reflect.Array;
import java.util.List;

public class Ship {
    protected int lifePoints;
    protected int size;
    protected boolean isAlive;
    protected int direction;

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

}
