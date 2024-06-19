package com.example.demo.view;

import com.example.demo.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MenuStage extends Stage{
    private MenuController menuController;

    public MenuStage() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/menu-view2.fxml"));
        Parent menu = loader.load();
        menuController = loader.getController();
        Scene scene = new Scene(menu);
        setTitle("Battleship");
        setResizable(false);
        setScene(scene);
        show();
    }

    public MenuController getMenuController(){
        return menuController;
    }

    public static MenuStage getInstance() throws IOException{
        return MenuStageHolder.INSTANCE = new MenuStage();
    }

    public static void deleteInstance() {
        MenuStageHolder.INSTANCE.close();
        MenuStageHolder.INSTANCE = null;
    }

    private static class MenuStageHolder {
        private static MenuStage INSTANCE;
    }



}
