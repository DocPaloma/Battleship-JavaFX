package com.example.demo;


import com.example.demo.controller.TestController;
import com.example.demo.model.Board;
import com.example.demo.model.SaveGameException;
import javafx.application.Application;
import javafx.stage.Stage;


import java.util.Scanner;

import static sun.tools.jconsole.Version.print;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        TestController controlador = new TestController();
        try {
            controlador.loadGame("game_save.dat");
        } catch (SaveGameException e) {
            System.out.println("No saved game found, starting a new game.");
        }
        controlador.playGame();


    }

    public static void main(String[] args) {
        launch();
    }


}


