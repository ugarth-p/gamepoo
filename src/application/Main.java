package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Lobby lobby = new Lobby(primaryStage);
            lobby.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startRealTimeMode(Stage primaryStage) {
        try {
            Pane root = new Pane();

            Image image = new Image(Main.class.getResourceAsStream("/playing_scene2.jpg"));
            ImageView backgroundImageView = new ImageView(image);
            backgroundImageView.setFitWidth(1050);
            backgroundImageView.setFitHeight(530);
            backgroundImageView.setPreserveRatio(true);

            root.getChildren().add(backgroundImageView);

            RealTimeGame game = new RealTimeGame(root, 5, primaryStage, null);

            Scene scene = new Scene(root, 1050, 530);

            primaryStage.setTitle("Real-Time Battle");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

            RealTimeMage player1 = new RealTimeMage(100, 200, 250, root);
            RealTimeWarrior player2 = new RealTimeWarrior(800, 200, 250, root);
            Robber robber = new Robber(0, 300);

            root.getChildren().add(player1.getImageView());
            root.getChildren().add(player2.getImageView());
            root.getChildren().add(robber.getImageView());

            RealTimeGameLoop gameLoop = new RealTimeGameLoop(player1, player2, root, robber, game);
            gameLoop.start();
            game.setGameLoop(gameLoop);

            scene.setOnKeyPressed(e -> {
                player1.onKeyPressed(e);
                player2.onKeyPressed(e);
                game.onKeyPressed(e, gameLoop);
            });
            scene.setOnKeyReleased(e -> {
                player1.onKeyReleased(e);
                player2.onKeyReleased(e);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
