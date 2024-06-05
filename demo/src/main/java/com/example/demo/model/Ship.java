package com.example.demo.model;

import java.lang.reflect.Array;
import java.util.List;

public class Ship {
    protected int lifePoints;
    protected int size;
    protected boolean isAlive;
    protected boolean verticalDir;

    public Ship() {

    }

    public boolean checkIsAlive(){
        if (lifePoints == 0){
            isAlive = false;
            return isAlive;
        }
        else {
            return isAlive;
        }

    }

    public int takeDamage(){
        lifePoints = lifePoints - 1;
        return lifePoints;
    }

    public void setVerticalDir(){
        verticalDir = true;
    }

    public int getSize(){
        return size;
    }

    public boolean getVerticalDir(){
        return verticalDir;
    }

}
