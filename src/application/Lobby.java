package application;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Lobby {

    private Stage stage;

    public Lobby(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        stage.setTitle("Game Mode Selection");

        Pane root = new Pane();
        root.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Background
        Image bgImage = new Image("/wallpaper.jpg");
        ImageView bgView = new ImageView(bgImage);
        bgView.setX(0);
        bgView.setY(0);
        bgView.setFitWidth(1024);
        bgView.setFitHeight(575);

        // Title
        Text title = new Text("SELECT GAME MODE");
        title.setLayoutX(310);
        title.setLayoutY(100);
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 50);
        title.setFont(titleFont != null ? titleFont : Font.font("Verdana", 50));
        title.setStyle("-fx-fill: #333a51;");

        // Turn-Based button
        Button turnBasedButton = new Button("Turn-Based");
        turnBasedButton.setLayoutX(250);
        turnBasedButton.setLayoutY(250);
        turnBasedButton.setPrefWidth(200);
        turnBasedButton.setPrefHeight(80);
        turnBasedButton.setFont(Font.font("Verdana", 22));
        turnBasedButton.setStyle("-fx-font-size: 18px; -fx-background-color: #28C59F; -fx-text-fill: white;");
        turnBasedButton.setOnAction(e -> {
            System.out.println("Turn-Based mode selected");
            TurnBasedGame turnBasedGame = new TurnBasedGame(stage);
            turnBasedGame.start();
        });
        addHoverEffect(turnBasedButton);

        // Real-Time button
        Button realTimeButton = new Button("Real-Time");
        realTimeButton.setLayoutX(550);
        realTimeButton.setLayoutY(250);
        realTimeButton.setPrefWidth(200);
        realTimeButton.setPrefHeight(80);
        realTimeButton.setFont(Font.font("Verdana", 22));
        realTimeButton.setStyle("-fx-font-size: 18px; -fx-background-color: #FF5252; -fx-text-fill: white;");
        realTimeButton.setOnAction(e -> {
            System.out.println("Real-Time mode selected");
            Main.startRealTimeMode(stage);
        });
        addHoverEffect(realTimeButton);

        // Exit button
        Button exitButton = new Button("Exit");
        exitButton.setLayoutX(460);
        exitButton.setLayoutY(400);
        exitButton.setPrefWidth(100);
        exitButton.setPrefHeight(50);
        exitButton.setFont(Font.font("Verdana", 22));
        exitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #757575; -fx-text-fill: white;");
        exitButton.setOnAction(e -> {
            System.out.println("Exit button clicked");
            stage.close();
        });
        addHoverEffect(exitButton);

        // Mode descriptions
        Text turnBasedDesc = new Text("Classic combat:\nClick to attack");
        turnBasedDesc.setLayoutX(260);
        turnBasedDesc.setLayoutY(360);
        Font descFont = Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 14);
        turnBasedDesc.setFont(descFont != null ? descFont : Font.font("Verdana", 14));
        turnBasedDesc.setStyle("-fx-fill: white;");

        Text realTimeDesc = new Text("Action combat:\nMove and fight in real-time");
        realTimeDesc.setLayoutX(555);
        realTimeDesc.setLayoutY(360);
        Font rtDescFont = Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 14);
        realTimeDesc.setFont(rtDescFont != null ? rtDescFont : Font.font("Verdana", 14));
        realTimeDesc.setStyle("-fx-fill: white;");

        root.getChildren().addAll(bgView, title, turnBasedButton, realTimeButton, exitButton, turnBasedDesc, realTimeDesc);

        Scene scene = new Scene(root, 1024, 575);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void addHoverEffect(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
        scaleTransition.setByX(0.1);
        scaleTransition.setByY(0.1);
        button.setOnMouseEntered(e -> scaleTransition.play());
        button.setOnMouseExited(e -> scaleTransition.setRate(-1.0));
    }
}
