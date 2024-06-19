package com.example.demo;


import com.example.demo.controller.GameController;
import com.example.demo.model.SaveGameException;
import com.example.demo.view.MenuStage;
import javafx.application.Application;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private GameController gameController;

    @Override
    public void start(Stage stage){


    }

    public static void main(String[] args) {
        launch();
    }

    private void showMenu() {
        try {
            MenuStage menuStage = MenuStage.getInstance();
            menuStage.getMenuController().setBattleshipApp(this);
            menuStage.getMenuController().setPrimaryStage(primaryStage);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInstructions() {
        gameController.showInstructions();
    }

    public void newGame() {
        gameController.startNewGame();
    }

    public void loadGame() {
        gameController.loadSavedGame();
    }

    public void startGame() {
        gameController.startNewGame();
    }

}


