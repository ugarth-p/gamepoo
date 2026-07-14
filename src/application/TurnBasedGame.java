package application;

import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TurnBasedGame {

    private Character selectedCharacter;
    private Character warrior = new TurnBasedWarrior("djamal", 530, 300);
    private Character mage = new TurnBasedMage("sara", 900, 390);
    private Character thief = new TurnBasedThief("salah", 70, 365);
    private Character enemy1;
    private Character enemy2;
    private boolean playerTurn = true;
    private boolean specialMode = false;

    private Stage stage;

    public TurnBasedGame(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        stage.setTitle("Game Lobby");

        Pane root = new Pane();
        root.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button playButton = new Button("Play");
        playButton.setLayoutX(450);
        playButton.setLayoutY(250);
        playButton.setPrefWidth(120);
        playButton.setPrefHeight(50);
        playButton.setFont(Font.font("Verdana", 22));
        playButton.setStyle("-fx-font-size: 16px; -fx-background-color: #28C59F; -fx-text-fill: white;");
        playButton.setOnAction(e -> {
            System.out.println("Play button clicked");
            showCharacterSelectionScreen();
        });
        addHoverEffect(playButton);

        Button quitButton = new Button("Quit");
        quitButton.setLayoutX(460);
        quitButton.setLayoutY(350);
        quitButton.setPrefWidth(100);
        quitButton.setPrefHeight(50);
        quitButton.setFont(Font.font("Verdana", 22));
        quitButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FF5252; -fx-text-fill: white;");
        quitButton.setOnAction(e -> {
            System.out.println("Quit button clicked");
            stage.close();
        });
        addHoverEffect(quitButton);

        Button backToLobbyButton = new Button("Back to Lobby");
        backToLobbyButton.setLayoutX(440);
        backToLobbyButton.setLayoutY(440);
        backToLobbyButton.setPrefWidth(140);
        backToLobbyButton.setPrefHeight(50);
        backToLobbyButton.setFont(Font.font("Verdana", 18));
        backToLobbyButton.setStyle("-fx-font-size: 14px; -fx-background-color: #757575; -fx-text-fill: white;");
        backToLobbyButton.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.start();
        });
        addHoverEffect(backToLobbyButton);

        Image imagesrc = new Image("/wallpaper.jpg");
        ImageView image = new ImageView(imagesrc);
        image.setX(0);
        image.setY(0);
        image.setFitWidth(1024);
        image.setFitHeight(575);

        root.getChildren().addAll(image, playButton, quitButton, backToLobbyButton);

        Scene menuScene = new Scene(root, 1024, 575);
        stage.setScene(menuScene);
        stage.show();
    }

    private void showCharacterSelectionScreen() {
        Image selectSceneSrc = new Image("/pixel-art-mountains-waterfall-trees-wallpaper-preview.jpg");
        ImageView selectScene = new ImageView(selectSceneSrc);
        selectScene.setY(0);
        selectScene.setX(0);

        Image knightsrc = new Image("/soul_knight.gif");
        ImageView knightImage = new ImageView(knightsrc);
        knightImage.setX(50);
        knightImage.setY(230);
        knightImage.setFitHeight(160);
        knightImage.setFitWidth(160);

        Pane root = new Pane();
        root.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button warriorButton = new Button("Choose Warrior (Thor):");
        warriorButton.setLayoutX(70);
        warriorButton.setLayoutY(240);
        warriorButton.setOnAction(e -> {
            selectedCharacter = warrior;
            enemy1 = mage;
            enemy2 = thief;
            showCombatScreen();
        });
        addHoverEffect(warriorButton);

        Image magesrc = new Image("/witch.gif");
        ImageView mageImage = new ImageView(magesrc);
        mageImage.setX(285);
        mageImage.setY(237);
        mageImage.setFitHeight(160);
        mageImage.setFitWidth(160);

        Button mageButton = new Button("Choose Mage (Gandalf)");
        mageButton.setLayoutX(290);
        mageButton.setLayoutY(240);
        mageButton.setOnAction(e -> {
            selectedCharacter = mage;
            enemy1 = warrior;
            enemy2 = thief;
            showCombatScreen();
        });
        addHoverEffect(mageButton);

        Image assasinSrc = new Image("/assasin.gif");
        ImageView assasinImage = new ImageView(assasinSrc);
        assasinImage.setX(470);
        assasinImage.setY(230);
        assasinImage.setFitHeight(160);
        assasinImage.setFitWidth(160);

        Button thiefButton = new Button("Choose Thief (Loki)");
        thiefButton.setLayoutX(490);
        thiefButton.setLayoutY(240);
        thiefButton.setOnAction(e -> {
            selectedCharacter = thief;
            enemy1 = warrior;
            enemy2 = mage;
            showCombatScreen();
        });
        addHoverEffect(thiefButton);

        Button backToLobbyButton = new Button("Back");
        backToLobbyButton.setLayoutX(10);
        backToLobbyButton.setLayoutY(10);
        backToLobbyButton.setPrefWidth(80);
        backToLobbyButton.setPrefHeight(40);
        backToLobbyButton.setStyle("-fx-background-color: #757575; -fx-text-fill: white; -fx-font-size: 14px;");
        backToLobbyButton.setOnAction(e -> {
            Lobby lobby = new Lobby(stage);
            lobby.start();
        });
        addHoverEffect(backToLobbyButton);

        root.getChildren().addAll(selectScene, knightImage, mageImage, assasinImage, warriorButton, mageButton, thiefButton, backToLobbyButton);

        Scene characterSelectionScene = new Scene(root, 728, 409);
        stage.setScene(characterSelectionScene);
    }

    private void showCombatScreen() {
        VBox combatRoot = new VBox(10);

        HBox characterInfo = new HBox(15);
        Text selectedCharacterHealth = new Text("Health: " + selectedCharacter.health);
        Text enemy1Health = new Text("Health: " + enemy1.health);
        Text enemy2Health = new Text("Health: " + enemy2.health);

        characterInfo.getChildren().addAll(
                new Text(selectedCharacter.getName() + " - "), selectedCharacterHealth,
                new Text(enemy1.getName() + " - "), enemy1Health,
                new Text(enemy2.getName() + " - "), enemy2Health);

        Pane root = new Pane();

        Button attackButton = new Button("Attack");
        attackButton.setLayoutX(100);
        attackButton.setLayoutY(600);
        attackButton.setPrefWidth(130);
        addHoverEffect(attackButton);

        Button specialButton = new Button("Use Special Ability");
        specialButton.setLayoutX(250);
        specialButton.setLayoutY(600);
        specialButton.setPrefWidth(160);
        addHoverEffect(specialButton);

        Button backButton = new Button("Back to Menu");
        backButton.setLayoutX(500);
        backButton.setLayoutY(600);
        addHoverEffect(backButton);
        backButton.setOnAction(e -> start());

        Button targetEnemy1 = new Button("Target " + enemy1.getName());
        targetEnemy1.setLayoutX(100);
        targetEnemy1.setLayoutY(600);
        targetEnemy1.setPrefWidth(150);
        targetEnemy1.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white;");
        addHoverEffect(targetEnemy1);

        Button targetEnemy2 = new Button("Target " + enemy2.getName());
        targetEnemy2.setLayoutX(270);
        targetEnemy2.setLayoutY(600);
        targetEnemy2.setPrefWidth(150);
        targetEnemy2.setStyle("-fx-background-color: #FF5252; -fx-text-fill: white;");
        addHoverEffect(targetEnemy2);

        Button cancelButton = new Button("Cancel");
        cancelButton.setLayoutX(440);
        cancelButton.setLayoutY(600);
        cancelButton.setPrefWidth(100);
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
            if (specialMode) {
                performSpecialAbility(selectedCharacter, enemy1, selectedCharacterHealth, enemy1Health, enemy2Health);
            } else {
                performAttack(selectedCharacter, enemy1, selectedCharacterHealth, enemy1Health, enemy2Health);
            }
            hideTargetButtons.run();
            playerTurn = false;
            aiTurn(selectedCharacterHealth, enemy1Health, enemy2Health);
        });

        targetEnemy2.setOnAction(e -> {
            if (specialMode) {
                performSpecialAbility(selectedCharacter, enemy2, selectedCharacterHealth, enemy1Health, enemy2Health);
            } else {
                performAttack(selectedCharacter, enemy2, selectedCharacterHealth, enemy1Health, enemy2Health);
            }
            hideTargetButtons.run();
            playerTurn = false;
            aiTurn(selectedCharacterHealth, enemy1Health, enemy2Health);
        });

        cancelButton.setOnAction(e -> hideTargetButtons.run());

        Image knightsrc = new Image("/soul_knight.gif");
        ImageView knightImage = new ImageView(knightsrc);
        knightImage.setX(530);
        knightImage.setY(300);
        knightImage.setFitHeight(190);
        knightImage.setFitWidth(190);

        Image magesrc = new Image("/witch.gif");
        ImageView mageImage = new ImageView(magesrc);
        mageImage.setX(900);
        mageImage.setY(390);
        mageImage.setFitHeight(200);
        mageImage.setFitWidth(200);
        mageImage.setScaleX(-1);

        Image assasinsrc = new Image("/assasin.gif");
        ImageView assassinImage = new ImageView(assasinsrc);
        assassinImage.setX(70);
        assassinImage.setY(365);
        assassinImage.setFitHeight(200);
        assassinImage.setFitWidth(200);

        Image imagesrc = new Image("/playing_scene2.jpg");
        ImageView background = new ImageView(imagesrc);
        background.setX(0);
        background.setY(0);

        root.getChildren().addAll(background, knightImage, mageImage, assassinImage, characterInfo, backButton, attackButton, specialButton, targetEnemy1, targetEnemy2, cancelButton);

        Scene combatScene = new Scene(root, 1200, 675);
        stage.setTitle("Turn-Based Battle");
        stage.setScene(combatScene);
        stage.setResizable(false);
        stage.show();
    }

    private void performAttack(Character attacker, Character target, Text selectedCharacterHealth, Text enemy1Health, Text enemy2Health) {
        attacker.attack(target);
        System.out.println(attacker.getName() + " attacks " + target.getName());
        updateHealth(selectedCharacterHealth, enemy1Health, enemy2Health);
    }

    private void performSpecialAbility(Character attacker, Character target, Text selectedCharacterHealth, Text enemy1Health, Text enemy2Health) {
        attacker.useSpecialAbility(target);
        System.out.println(attacker.getName() + " uses special ability on " + target.getName());
        updateHealth(selectedCharacterHealth, enemy1Health, enemy2Health);
    }

    private void aiTurn(Text selectedCharacterHealth, Text enemy1Health, Text enemy2Health) {
        if (enemy1.isAlive()) {
            enemy1.attack(selectedCharacter);
            System.out.println(enemy1.getName() + " attacks " + selectedCharacter.getName());
        }

        if (enemy2.isAlive()) {
            enemy2.attack(selectedCharacter);
            System.out.println(enemy2.getName() + " attacks " + selectedCharacter.getName());
        }

        updateHealth(selectedCharacterHealth, enemy1Health, enemy2Health);

        playerTurn = true;

        checkGameOver();
    }

    private void updateHealth(Text selectedCharacterHealth, Text enemy1Health, Text enemy2Health) {
        selectedCharacterHealth.setText("Health: " + selectedCharacter.health);
        enemy1Health.setText("Health: " + enemy1.health);
        enemy2Health.setText("Health: " + enemy2.health);
    }

    private void checkGameOver() {
        if (!selectedCharacter.isAlive()) {
            System.out.println(selectedCharacter.getName() + " has been defeated!");
            showGameOverScreen();
        } else if (!enemy1.isAlive() && !enemy2.isAlive()) {
            System.out.println("You win!");
            showVictoryScreen();
        }
    }

    private void showGameOverScreen() {
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText("You have been defeated!");
        gameOverAlert.setContentText("Better luck next time.");
        gameOverAlert.showAndWait();

        resetHealth();
        start();
    }

    private void showVictoryScreen() {
        Alert victoryAlert = new Alert(Alert.AlertType.INFORMATION);
        victoryAlert.setTitle("Victory!");
        victoryAlert.setHeaderText("You have defeated all enemies!");
        victoryAlert.setContentText("Congratulations on your victory!");
        victoryAlert.showAndWait();

        resetHealth();
        start();
    }

    private void resetHealth() {
        selectedCharacter.health = selectedCharacter.getMaxHealth();
        enemy1.health = enemy1.getMaxHealth();
        enemy2.health = enemy2.getMaxHealth();
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
