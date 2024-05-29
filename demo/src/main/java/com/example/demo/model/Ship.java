package com.example.demo.model;

import java.lang.reflect.Array;
import java.util.List;

public class Ship {
    protected int lifePoints;
    protected boolean isAlive = true;

    public Ship() {

    }

    public boolean checkIsAlive(boolean isAlive, int lifePoints){
        if (lifePoints == 0){
            isAlive = false;
            return isAlive;
        }
        else {
            return isAlive;
        }

    }

    public int takeDamage(int lifePoints){
        lifePoints = lifePoints - 1;
        return lifePoints;
    }

    public void direction(){

    }

}
