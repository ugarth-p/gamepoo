package application;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RealTimeGame {
    private int rounds;
    private int currentRound;

    Label roundName = new Label("Round");
    Label roundNbr = new Label("nbr.");

    Label winnerNameLabel = new Label();
    Label winnerText = new Label("Is The Winner");
    TranslateTransition winnerNameLabelTransition = new TranslateTransition(Duration.seconds(2), winnerNameLabel);
    TranslateTransition winnerTextTransition = new TranslateTransition(Duration.seconds(1.5), winnerText);

    Pane mainRoot;

    TranslateTransition roundNameAnimation = new TranslateTransition(Duration.seconds(3), roundName);
    TranslateTransition roundNbrAnimation = new TranslateTransition(Duration.seconds(2.5), roundNbr);

    private StackPane pauseMenu;
    private RealTimeGameLoop gameLoop;

    int allRounds;

    private Stage primaryStage;
    private Scene lobby;

    public RealTimeGame(Pane root, int rounds, Stage primaryStage, Scene lobby) {
        this.primaryStage = primaryStage;
        this.lobby = lobby;
        allRounds = rounds;

        this.pauseMenu = createPauseMenuPane();
        pauseMenu.setLayoutY(100);
        pauseMenu.setLayoutX(375);
        pauseMenu.setVisible(false);

        winnerNameLabel.setLayoutX(180);
        winnerNameLabel.setLayoutY(-200);
        try {
            winnerNameLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 150));
        } catch (Exception e) {
            winnerNameLabel.setFont(Font.font("Verdana", 120));
        }
        winnerNameLabel.setTextFill(Color.RED);

        winnerText.setLayoutX(180);
        winnerText.setLayoutY(-200);
        try {
            winnerText.setFont(Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 150));
        } catch (Exception e) {
            winnerText.setFont(Font.font("Verdana", 120));
        }
        winnerText.setTextFill(Color.RED);

        mainRoot = root;
        roundName.setLayoutX(525);
        roundNbr.setLayoutX(525);

        root.getChildren().addAll(roundNbr, roundName);
        try {
            roundName.setFont(Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 100));
            roundNbr.setFont(Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 100));
        } catch (Exception e) {
            roundName.setFont(Font.font("Verdana", 80));
            roundNbr.setFont(Font.font("Verdana", 80));
        }

        this.rounds = rounds;
        this.currentRound = 1;

        root.getChildren().addAll(winnerNameLabel, winnerText, pauseMenu);
    }

    public void setGameLoop(RealTimeGameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    public void onKeyPressed(KeyEvent event, RealTimeGameLoop gameLoop) {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (pauseMenu.isVisible()) {
                hidePausePanel();
                gameLoop.start();
            } else {
                showPausePanel();
                gameLoop.stop();
            }
        }
        if (event.getCode() == KeyCode.ENTER) {
            hidePausePanel();
            if (rounds == currentRound) {
                currentRound = 0;
                gameLoop.resetPlayerScore();
            }
            gameLoop.start();
        }
    }

    public void roundAnimation(int nbr) {
        roundNbrAnimation.setFromY(-100);
        roundNbrAnimation.setToY(260);

        roundNameAnimation.setFromY(600);
        roundNameAnimation.setToY(200);

        this.roundNbr.setText("nbr." + nbr);
        roundNbrAnimation.play();
        roundNameAnimation.play();
    }

    public void roundAnimationDisappear() {
        roundNbrAnimation.play();
        roundNameAnimation.play();
    }

    public void addRounds(String name) {
        this.currentRound++;
        this.roundNbr.setText("nbr." + currentRound);
        if (currentRound == rounds) {
            winnerTransition(name);
            endGame();
            System.out.println("gameOver");
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getRounds() {
        return rounds;
    }

    public void endGame() {
    }

    public void showPausePanel() {
        this.pauseMenu.setVisible(true);
    }

    public void hidePausePanel() {
        this.pauseMenu.setVisible(false);
        this.gameLoop.start();
    }

    private void restartGame() {
        winnerNameLabel.setTranslateY(-200);
        winnerText.setTranslateY(-200);

        this.gameLoop.restartGame();
        this.rounds = allRounds;
        this.currentRound = -1;
        addRounds(null);
        this.pauseMenu.setVisible(false);
        this.gameLoop.start();
    }

    public void winnerTransition(String winnerName) {
        winnerTextTransition.setFromY(800);
        winnerTextTransition.setFromX(180);
        winnerTextTransition.setToY(300);
        winnerTextTransition.setToX(180);

        winnerNameLabelTransition.setFromY(-50);
        winnerNameLabelTransition.setFromX(180);
        winnerNameLabelTransition.setToY(200);
        winnerNameLabelTransition.setToX(180);
        winnerNameLabel.setText(winnerName);
        winnerNameLabelTransition.play();
        winnerTextTransition.play();
    }

    public StackPane createPauseMenuPane() {
        StackPane root = new StackPane();

        AnchorPane menuBackground = new AnchorPane();
        menuBackground.setStyle("-fx-background-color: #2B2B2B; -fx-background-radius: 15;");
        menuBackground.setPrefSize(320, 250);

        Text title = new Text("PAUSE");
        try {
            title.setFont(Font.loadFont(getClass().getResourceAsStream("/application/font2.ttf"), 56));
        } catch (Exception e) {
            title.setFont(Font.font("Verdana", 50));
        }
        title.setFill(Color.WHITE);

        Button homeButton = new Button("Home");
        homeButton.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        homeButton.setOnAction(e -> {
            restartGame();
            this.primaryStage.setScene(lobby);
        });

        Button playButton = new Button("Resume");
        playButton.setStyle("-fx-background-color: #28C59F; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        playButton.setOnAction(e -> {
            hidePausePanel();
        });

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-background-color: #FFA726; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        restartButton.setOnAction(e -> {
            restartGame();
        });

        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(homeButton, playButton, restartButton);

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.getChildren().addAll(title, buttonLayout);

        root.getChildren().addAll(menuBackground, menuLayout);

        return root;
    }
}
