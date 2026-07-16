package com.gamepoo.scene;

import com.gamepoo.assets.AssetManager;
import com.gamepoo.config.Constants;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Lobby {

    public void start() {
        Pane root = new Pane();

        ImageView bgView = new ImageView(AssetManager.getImage(Constants.PATH_BG_WALLPAPER));
        bgView.setFitWidth(Constants.LOBBY_WIDTH);
        bgView.setFitHeight(Constants.LOBBY_HEIGHT);

        Text title = new Text("SELECT GAME MODE");
        title.setLayoutX(310);
        title.setLayoutY(100);
        Font titleFont = AssetManager.getFontOrFallback(Constants.FONT_PATH, 50, Constants.DEFAULT_FONT_FAMILY);
        title.setFont(titleFont);
        title.setStyle("-fx-fill: #333a51;");

        Button turnBasedButton = createMenuButton("Turn-Based", 250, 250, "#28C59F");
        turnBasedButton.setOnAction(e -> {
            TurnBasedGameScene gameScene = new TurnBasedGameScene();
            gameScene.start();
        });

        Button realTimeButton = createMenuButton("Real-Time", 550, 250, "#FF5252");
        realTimeButton.setOnAction(e -> RealTimeGameScene.launch());

        Button exitButton = createMenuButton("Exit", 460, 400, "#757575");
        exitButton.setPrefSize(100, 50);
        exitButton.setOnAction(e -> SceneManager.getStage().close());

        Text turnBasedDesc = new Text("Classic combat:\nClick to attack");
        turnBasedDesc.setLayoutX(260);
        turnBasedDesc.setLayoutY(360);
        Font descFont = AssetManager.getFontOrFallback(Constants.FONT_PATH, 14, Constants.DEFAULT_FONT_FAMILY);
        turnBasedDesc.setFont(descFont);
        turnBasedDesc.setStyle("-fx-fill: white;");

        Text realTimeDesc = new Text("Action combat:\nMove and fight in real-time");
        realTimeDesc.setLayoutX(555);
        realTimeDesc.setLayoutY(360);
        realTimeDesc.setFont(descFont);
        realTimeDesc.setStyle("-fx-fill: white;");

        root.getChildren().addAll(bgView, title, turnBasedButton, realTimeButton, exitButton, turnBasedDesc, realTimeDesc);

        SceneManager.setScene(root, Constants.LOBBY_WIDTH, Constants.LOBBY_HEIGHT, "Game Mode Selection");
    }

    private Button createMenuButton(String text, double x, double y, String color) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(200, 80);
        button.setFont(Font.font(Constants.DEFAULT_FONT_FAMILY, 22));
        button.setStyle("-fx-font-size: 18px; -fx-background-color: " + color + "; -fx-text-fill: white;");
        addHoverEffect(button);
        return button;
    }

    private void addHoverEffect(Button button) {
        button.setFocusTraversable(false);
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        button.setOnMouseEntered(e -> scaleTransition.play());
        button.setOnMouseExited(e -> scaleTransition.setRate(-1.0));
    }
}
