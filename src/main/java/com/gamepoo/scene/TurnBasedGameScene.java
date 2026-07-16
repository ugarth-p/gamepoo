package com.gamepoo.scene;

import com.gamepoo.assets.AssetManager;
import com.gamepoo.config.Constants;
import com.gamepoo.model.*;
import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TurnBasedGameScene {
    private Character selectedCharacter;
    private final Character warrior = new TurnBasedWarrior("Thor", 530, 300);
    private final Character mage = new TurnBasedMage("Gandalf", 900, 390);
    private final Character thief = new TurnBasedThief("Loki", 70, 365);
    private Character enemy1;
    private Character enemy2;
    private boolean playerTurn = true;
    private boolean specialMode = false;

    public void start() {
        showMenu();
    }

    private void showMenu() {
        Pane root = new Pane();

        Button playButton = createButton("Play", 450, 250, 120, 50, "#28C59F");
        playButton.setOnAction(e -> showCharacterSelection());

        Button quitButton = createButton("Quit", 460, 350, 100, 50, "#FF5252");
        quitButton.setOnAction(e -> SceneManager.getStage().close());

        Button backButton = createButton("Back to Lobby", 440, 440, 140, 50, "#757575");
        backButton.setOnAction(e -> {
            Lobby lobby = new Lobby();
            lobby.start();
        });

        ImageView bg = new ImageView(AssetManager.getImage(Constants.PATH_BG_WALLPAPER));
        bg.setFitWidth(Constants.LOBBY_WIDTH);
        bg.setFitHeight(Constants.LOBBY_HEIGHT);

        root.getChildren().addAll(bg, playButton, quitButton, backButton);
        SceneManager.setScene(root, Constants.LOBBY_WIDTH, Constants.LOBBY_HEIGHT, "Game Lobby");
    }

    private void showCharacterSelection() {
        Pane root = new Pane();

        ImageView bg = new ImageView(AssetManager.getImage(Constants.PATH_BG_CHARACTER_SELECT));
        bg.setFitWidth(728);
        bg.setFitHeight(409);

        ImageView knightImage = new ImageView(AssetManager.getImage(Constants.PATH_SOUL_KNIGHT));
        knightImage.setX(50);
        knightImage.setY(230);
        knightImage.setFitHeight(160);
        knightImage.setFitWidth(160);

        Button warriorButton = new Button("Choose Warrior (Thor):");
        warriorButton.setLayoutX(70);
        warriorButton.setLayoutY(240);
        addHoverEffect(warriorButton);
        warriorButton.setOnAction(e -> {
            selectedCharacter = warrior;
            enemy1 = mage;
            enemy2 = thief;
            showCombat();
        });

        ImageView mageImage = new ImageView(AssetManager.getImage(Constants.PATH_WITCH));
        mageImage.setX(285);
        mageImage.setY(237);
        mageImage.setFitHeight(160);
        mageImage.setFitWidth(160);

        Button mageButton = new Button("Choose Mage (Gandalf)");
        mageButton.setLayoutX(290);
        mageButton.setLayoutY(240);
        addHoverEffect(mageButton);
        mageButton.setOnAction(e -> {
            selectedCharacter = mage;
            enemy1 = warrior;
            enemy2 = thief;
            showCombat();
        });

        ImageView assassinImage = new ImageView(AssetManager.getImage(Constants.PATH_ASSASSIN));
        assassinImage.setX(470);
        assassinImage.setY(230);
        assassinImage.setFitHeight(160);
        assassinImage.setFitWidth(160);

        Button thiefButton = new Button("Choose Thief (Loki)");
        thiefButton.setLayoutX(490);
        thiefButton.setLayoutY(240);
        addHoverEffect(thiefButton);
        thiefButton.setOnAction(e -> {
            selectedCharacter = thief;
            enemy1 = warrior;
            enemy2 = mage;
            showCombat();
        });

        Button backButton = new Button("Back");
        backButton.setLayoutX(10);
        backButton.setLayoutY(10);
        backButton.setPrefSize(80, 40);
        backButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px;");
        addHoverEffect(backButton);
        backButton.setOnAction(e -> showMenu());

        root.getChildren().addAll(bg, knightImage, mageImage, assassinImage,
                warriorButton, mageButton, thiefButton, backButton);

        Scene characterScene = new Scene(root, 728, 409);
        SceneManager.getStage().setScene(characterScene);
    }

    private void showCombat() {
        VBox combatRoot = new VBox(10);

        HBox characterInfo = new HBox(15);
        Text selectedHealthText = new Text("Health: " + selectedCharacter.health);
        Text enemy1HealthText = new Text("Health: " + enemy1.health);
        Text enemy2HealthText = new Text("Health: " + enemy2.health);

        characterInfo.getChildren().addAll(
                new Text(selectedCharacter.getName() + " - "), selectedHealthText,
                new Text(enemy1.getName() + " - "), enemy1HealthText,
                new Text(enemy2.getName() + " - "), enemy2HealthText);

        Pane root = new Pane();

        Button attackButton = new Button("Attack");
        attackButton.setLayoutX(100);
        attackButton.setLayoutY(600);
        attackButton.setPrefSize(130, 30);
        addHoverEffect(attackButton);

        Button specialButton = new Button("Use Special Ability");
        specialButton.setLayoutX(250);
        specialButton.setLayoutY(600);
        specialButton.setPrefSize(160, 30);
        addHoverEffect(specialButton);

        Button backButton = new Button("Back to Menu");
        backButton.setLayoutX(500);
        backButton.setLayoutY(600);
        addHoverEffect(backButton);
        backButton.setOnAction(e -> showMenu());

        Button targetEnemy1 = new Button("Target " + enemy1.getName());
        targetEnemy1.setLayoutX(100);
        targetEnemy1.setLayoutY(600);
        targetEnemy1.setPrefSize(150, 30);
        targetEnemy1.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white;");
        addHoverEffect(targetEnemy1);

        Button targetEnemy2 = new Button("Target " + enemy2.getName());
        targetEnemy2.setLayoutX(270);
        targetEnemy2.setLayoutY(600);
        targetEnemy2.setPrefSize(150, 30);
        targetEnemy2.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white;");
        addHoverEffect(targetEnemy2);

        Button cancelButton = new Button("Cancel");
        cancelButton.setLayoutX(440);
        cancelButton.setLayoutY(600);
        cancelButton.setPrefSize(100, 30);
        addHoverEffect(cancelButton);

        targetEnemy1.setVisible(false);
        targetEnemy2.setVisible(false);
        cancelButton.setVisible(false);

        Runnable showTargetButtons = () -> {
            attackButton.setVisible(false);
            specialButton.setVisible(false);
            targetEnemy1.setVisible(true);
            targetEnemy2.setVisible(true);
            cancelButton.setVisible(true);
        };

        Runnable hideTargetButtons = () -> {
            targetEnemy1.setVisible(false);
            targetEnemy2.setVisible(false);
            cancelButton.setVisible(false);
            attackButton.setVisible(true);
            specialButton.setVisible(true);
        };

        attackButton.setOnAction(e -> {
            if (playerTurn) {
                specialMode = false;
                showTargetButtons.run();
            }
        });

        specialButton.setOnAction(e -> {
            if (playerTurn) {
                specialMode = true;
                showTargetButtons.run();
            }
        });

        targetEnemy1.setOnAction(e -> {
            executeAction(enemy1, selectedHealthText, enemy1HealthText, enemy2HealthText);
            hideTargetButtons.run();
            playerTurn = false;
            aiTurn(selectedHealthText, enemy1HealthText, enemy2HealthText);
        });

        targetEnemy2.setOnAction(e -> {
            executeAction(enemy2, selectedHealthText, enemy1HealthText, enemy2HealthText);
            hideTargetButtons.run();
            playerTurn = false;
            aiTurn(selectedHealthText, enemy1HealthText, enemy2HealthText);
        });

        cancelButton.setOnAction(e -> hideTargetButtons.run());

        ImageView knightImage = new ImageView(AssetManager.getImage(Constants.PATH_SOUL_KNIGHT));
        knightImage.setX(530);
        knightImage.setY(300);
        knightImage.setFitHeight(190);
        knightImage.setFitWidth(190);

        ImageView mageImage = new ImageView(AssetManager.getImage(Constants.PATH_WITCH));
        mageImage.setX(900);
        mageImage.setY(390);
        mageImage.setFitHeight(200);
        mageImage.setFitWidth(200);
        mageImage.setScaleX(-1);

        ImageView assassinImage = new ImageView(AssetManager.getImage(Constants.PATH_ASSASSIN));
        assassinImage.setX(70);
        assassinImage.setY(365);
        assassinImage.setFitHeight(200);
        assassinImage.setFitWidth(200);

        ImageView bg = new ImageView(AssetManager.getImage(Constants.PATH_BG_PLAYING));
        bg.setX(0);
        bg.setY(0);

        root.getChildren().addAll(bg, knightImage, mageImage, assassinImage, characterInfo, backButton,
                attackButton, specialButton, targetEnemy1, targetEnemy2, cancelButton);

        Scene combatScene = new Scene(root, 1200, 675);
        SceneManager.getStage().setTitle("Turn-Based Battle");
        SceneManager.getStage().setScene(combatScene);
    }

    private void executeAction(Character target, Text... healthTexts) {
        if (specialMode) {
            selectedCharacter.useSpecialAbility(target);
        } else {
            selectedCharacter.attack(target);
        }
        updateHealthTexts(healthTexts);
    }

    private void aiTurn(Text selectedHealthText, Text enemy1HealthText, Text enemy2HealthText) {
        if (enemy1.isAlive()) {
            enemy1.attack(selectedCharacter);
        }
        if (enemy2.isAlive()) {
            enemy2.attack(selectedCharacter);
        }
        updateHealthTexts(selectedHealthText, enemy1HealthText, enemy2HealthText);
        playerTurn = true;
        checkGameOver();
    }

    private void updateHealthTexts(Text... texts) {
        texts[0].setText("Health: " + selectedCharacter.health);
        texts[1].setText("Health: " + enemy1.health);
        texts[2].setText("Health: " + enemy2.health);
    }

    private void checkGameOver() {
        if (!selectedCharacter.isAlive()) {
            showGameOver();
        } else if (!enemy1.isAlive() && !enemy2.isAlive()) {
            showVictory();
        }
    }

    private void showGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("You have been defeated!");
        alert.setContentText("Better luck next time.");
        alert.showAndWait();
        resetHealth();
        showMenu();
    }

    private void showVictory() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victory!");
        alert.setHeaderText("You have defeated all enemies!");
        alert.setContentText("Congratulations on your victory!");
        alert.showAndWait();
        resetHealth();
        showMenu();
    }

    private void resetHealth() {
        selectedCharacter.health = selectedCharacter.getMaxHealth();
        enemy1.health = enemy1.getMaxHealth();
        enemy2.health = enemy2.getMaxHealth();
    }

    private Button createButton(String text, double x, double y, double w, double h, String color) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefSize(w, h);
        button.setFont(Font.font(Constants.DEFAULT_FONT_FAMILY, text.equals("Play") || text.equals("Quit") ? 22 : 18));
        button.setStyle("-fx-font-size: " + (text.equals("Back to Lobby") ? 14 : 16) + "px; -fx-background-color: " + color + "; -fx-text-fill: white;");
        addHoverEffect(button);
        return button;
    }

    private void addHoverEffect(Button button) {
        button.setFocusTraversable(false);
        ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
        st.setByX(0.1);
        st.setByY(0.1);
        button.setOnMouseEntered(e -> st.play());
        button.setOnMouseExited(e -> st.setRate(-1.0));
    }
}
