package com.gamepoo.launcher;

import com.gamepoo.scene.Lobby;
import com.gamepoo.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameLauncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            SceneManager.initialize(primaryStage);
            Lobby lobby = new Lobby();
            lobby.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
