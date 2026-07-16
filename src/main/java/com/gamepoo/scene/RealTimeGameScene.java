package com.gamepoo.scene;

import com.gamepoo.assets.AssetManager;
import com.gamepoo.config.Constants;
import com.gamepoo.core.GameLoop;
import com.gamepoo.model.RealTimePlayer;
import com.gamepoo.model.Robber;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class RealTimeGameScene {
    private final int totalRounds;
    private int currentRound;
    private final Pane root;
    private final GameLoop gameLoop;
    private final Label roundLabel;
    private final Label roundNumberLabel;
    private final Label winnerNameLabel;
    private final Label winnerTextLabel;
    private final Pane pauseOverlay;
    private final TranslateTransition winnerNameTransition;
    private final TranslateTransition winnerTextTransition;
    private boolean gameOver;
    private final RealTimePlayer player1;
    private final RealTimePlayer player2;

    public static void launch() {
        Pane root = new Pane();

        ImageView background = new ImageView(AssetManager.getImage(Constants.PATH_BG_PLAYING));
        background.setFitWidth(Constants.SCENE_WIDTH);
        background.setFitHeight(Constants.SCENE_HEIGHT);
        background.setPreserveRatio(true);
        root.getChildren().add(background);

        RealTimePlayer player1 = new RealTimePlayer("Mage", Constants.MAGE_HEALTH, Constants.MAGE_DAMAGE,
                100, 200, 250, 1, Constants.PATH_WITCH, root);
        RealTimePlayer player2 = new RealTimePlayer("Warrior", Constants.WARRIOR_HEALTH, Constants.WARRIOR_DAMAGE,
                800, 200, 250, 2, Constants.PATH_SOUL_KNIGHT, root);
        Robber robber = new Robber(0, 300);

        root.getChildren().addAll(player1.getImageView(), player2.getImageView(), robber.getImageView());

        RealTimeGameScene gameScene = new RealTimeGameScene(player1, player2, robber, Constants.DEFAULT_ROUNDS);

        root.getChildren().add(gameScene.getRoundLabels());
        root.getChildren().add(gameScene.getWinnerLabels());
        root.getChildren().add(gameScene.getPauseOverlay());
        root.getChildren().add(gameScene.getReturnButton());

        Scene scene = new Scene(root, Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        SceneManager.getStage().setTitle("Real-Time Battle");
        SceneManager.getStage().setScene(scene);
        SceneManager.getStage().show();

        gameScene.start();

        scene.setOnKeyPressed(e -> {
            gameScene.handleKeyPressed(e);
            handlePlayerInput(e, player1, player2);
        });

        scene.setOnKeyReleased(e -> handlePlayerInputReleased(e, player1, player2));
    }

    private static void handlePlayerInput(KeyEvent e, RealTimePlayer p1, RealTimePlayer p2) {
        switch (e.getCode()) {
            case W -> p1.getPhysics().jump();
            case A -> p1.setMovingLeft(true);
            case D -> p1.setMovingRight(true);
            case S -> { p1.getPhysics().setCrouching(true); p1.getImageView().setFitHeight(Constants.PLAYER_CROUCH_HEIGHT); }
            case TAB -> p1.throwProjectile();
            case UP -> p2.getPhysics().jump();
            case LEFT -> p2.setMovingLeft(true);
            case RIGHT -> p2.setMovingRight(true);
            case DOWN -> { p2.getPhysics().setCrouching(true); p2.getImageView().setFitHeight(Constants.PLAYER_CROUCH_HEIGHT); }
            case SHIFT -> p2.throwProjectile();
        }
    }

    private static void handlePlayerInputReleased(KeyEvent e, RealTimePlayer p1, RealTimePlayer p2) {
        switch (e.getCode()) {
            case A -> p1.setMovingLeft(false);
            case D -> p1.setMovingRight(false);
            case S -> { p1.getPhysics().setCrouching(false); p1.getImageView().setFitHeight(Constants.PLAYER_HEIGHT); }
            case LEFT -> p2.setMovingLeft(false);
            case RIGHT -> p2.setMovingRight(false);
            case DOWN -> { p2.getPhysics().setCrouching(false); p2.getImageView().setFitHeight(Constants.PLAYER_HEIGHT); }
        }
    }

    public RealTimeGameScene(RealTimePlayer player1, RealTimePlayer player2, Robber robber, int rounds) {
        this.player1 = player1;
        this.player2 = player2;
        this.totalRounds = rounds;
        this.currentRound = 1;
        this.gameOver = false;
        this.root = new Pane();

        this.gameLoop = new GameLoop(player1, player2, root, robber, this);

        roundLabel = new Label("Round");
        roundNumberLabel = new Label("nbr.1");
        Font rnFont = AssetManager.getFontOrFallback(Constants.FONT_PATH, 100, Constants.DEFAULT_FONT_FAMILY);
        roundLabel.setFont(rnFont);
        roundNumberLabel.setFont(rnFont);
        roundLabel.setLayoutX(525);
        roundLabel.setLayoutY(0);
        roundNumberLabel.setLayoutX(525);
        roundNumberLabel.setLayoutY(0);

        winnerNameLabel = new Label("");
        Font wf = AssetManager.getFontOrFallback(Constants.FONT_PATH, 150, Constants.DEFAULT_FONT_FAMILY);
        winnerNameLabel.setFont(wf);
        winnerNameLabel.setTextFill(Color.RED);
        winnerNameLabel.setLayoutX(180);
        winnerNameLabel.setLayoutY(-200);

        winnerTextLabel = new Label("Is The Winner");
        winnerTextLabel.setFont(wf);
        winnerTextLabel.setTextFill(Color.RED);
        winnerTextLabel.setLayoutX(180);
        winnerTextLabel.setLayoutY(-200);

        winnerNameTransition = new TranslateTransition(Duration.seconds(2), winnerNameLabel);
        winnerTextTransition = new TranslateTransition(Duration.seconds(1.5), winnerTextLabel);

        pauseOverlay = createPauseOverlay();

        Button returnButton = createReturnButton();
        returnButton.setLayoutX(10);
        returnButton.setLayoutY(10);
        returnButton.setPrefSize(120, 30);
        returnButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 12px;");
    }

    public void start() {
        gameLoop.start();
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void onPlayerScored(String scorerName) {
        currentRound++;
        roundNumberLabel.setText("nbr." + currentRound);
        if (currentRound >= totalRounds) {
            showWinner(scorerName);
            gameOver = true;
        }
    }

    private void showWinner(String winnerName) {
        winnerNameLabel.setText(winnerName);
        winnerNameTransition.setFromY(-50);
        winnerNameTransition.setFromX(180);
        winnerNameTransition.setToY(200);
        winnerNameTransition.setToX(180);

        winnerTextTransition.setFromY(800);
        winnerTextTransition.setFromX(180);
        winnerTextTransition.setToY(300);
        winnerTextTransition.setToX(180);

        winnerNameTransition.play();
        winnerTextTransition.play();
    }

    public void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            if (pauseOverlay.isVisible()) {
                gameLoop.stop();
                SceneManager.getStage().close();
            } else {
                pauseOverlay.setVisible(true);
                gameLoop.stop();
            }
            event.consume();
        } else if (event.getCode() == KeyCode.ENTER) {
            pauseOverlay.setVisible(false);
            if (gameOver) {
                currentRound = 0;
                gameLoop.resetScores();
                gameOver = false;
            }
            gameLoop.start();
            event.consume();
        }
    }

    private Pane createPauseOverlay() {
        Pane overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.7); -fx-background-radius: 15;");
        overlay.setPrefSize(320, 250);
        overlay.setLayoutX(375);
        overlay.setLayoutY(100);

        Text title = new Text("PAUSE");
        Font pauseFont = AssetManager.getFontOrFallback(Constants.FONT_PATH, 56, Constants.DEFAULT_FONT_FAMILY);
        title.setFont(pauseFont);
        title.setFill(Color.WHITE);
        title.setLayoutX(80);
        title.setLayoutY(80);

        Button resumeButton = new Button("Resume");
        resumeButton.setLayoutX(100);
        resumeButton.setLayoutY(150);
        resumeButton.setStyle("-fx-background-color: #28C59F; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px;");
        resumeButton.setOnAction(e -> {
            pauseOverlay.setVisible(false);
            gameLoop.start();
        });

        overlay.getChildren().addAll(title, resumeButton);
        overlay.setVisible(false);
        return overlay;
    }

    private Button createReturnButton() {
        Button button = new Button("Return to Lobby");
        button.setOnAction(e -> {
            gameLoop.stop();
            Lobby lobby = new Lobby();
            lobby.start();
        });
        return button;
    }

    public Pane getRoundLabels() {
        Pane container = new Pane();
        container.getChildren().addAll(roundNumberLabel, roundLabel);
        return container;
    }

    public Pane getWinnerLabels() {
        Pane container = new Pane();
        container.getChildren().addAll(winnerNameLabel, winnerTextLabel);
        return container;
    }

    public Pane getPauseOverlay() {
        return pauseOverlay;
    }

    public Button getReturnButton() {
        return createReturnButton();
    }
}
