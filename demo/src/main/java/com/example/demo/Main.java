package com.example.demo;

import com.example.demo.model.Board;
import javafx.application.Application;
import javafx.stage.Stage;


import static sun.tools.jconsole.Version.print;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        Board game = new Board();
        game.printBoard();

    }

    public static void main(String[] args) {
        launch();
    }
}