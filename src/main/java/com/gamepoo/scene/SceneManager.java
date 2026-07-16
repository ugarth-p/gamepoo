package com.gamepoo.scene;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class SceneManager {
    private static Stage primaryStage;

    private SceneManager() {}

    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.setResizable(false);
    }

    public static void setScene(Pane root, int width, int height, String title) {
        primaryStage.setTitle(title);
        Scene scene = new Scene(root, width, height);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getStage() {
        return primaryStage;
    }
}
