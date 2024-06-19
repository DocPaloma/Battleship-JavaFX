package com.example.demo.controller;

import com.example.demo.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuController {
    @FXML
    private Button playButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button loadGameButton;
    @FXML
    private Button instructionsButton;
    @FXML
    private Label statusLabel;

    private Stage primaryStage;
    private Main battleshipApp;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setBattleshipApp(Main battleshipApp) {
        this.battleshipApp = battleshipApp;
    }

    @FXML
    private void handlePlay() {
        battleshipApp.startGame();
    }

    @FXML
    private void handleNewGame() {
        battleshipApp.newGame();
    }

    @FXML
    private void handleLoadGame() {
        battleshipApp.loadGame();
    }

    @FXML
    private void handleInstructions() {
        battleshipApp.showInstructions();
    }
}



