package com.gamepoo.model;

import com.gamepoo.assets.AssetManager;
import com.gamepoo.component.HealthComponent;
import com.gamepoo.component.PhysicsComponent;
import com.gamepoo.config.Constants;
import com.gamepoo.ui.HealthDisplay;
import com.gamepoo.ui.ScoreDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class RealTimePlayer extends Character {
    private final ImageView imageView;
    private final PhysicsComponent physics;
    private final HealthComponent health;
    private final HealthDisplay healthDisplay;
    private final ScoreDisplay scoreDisplay;
    private final List<Projectile> projectiles = new ArrayList<>();
    private final int playerNumber;
    private final double fireballSpeed = Constants.FIREBALL_SPEED;

    private boolean movingLeft;
    private boolean movingRight;

    public RealTimePlayer(String name, int healthAmount, int damage, double x, double y,
                          double speed, int playerNumber, String spritePath, Pane root) {
        super(name, healthAmount, damage, (int) x, (int) y);
        this.playerNumber = playerNumber;

        this.imageView = new ImageView(AssetManager.getImage(spritePath));
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
        this.imageView.setFitHeight(Constants.PLAYER_HEIGHT);
        this.imageView.setFitWidth(Constants.PLAYER_WIDTH);
        if (playerNumber == 2) {
            this.imageView.setScaleX(-1);
        }

        this.physics = new PhysicsComponent(speed);
        this.health = new HealthComponent(healthAmount);
        this.healthDisplay = new HealthDisplay(name, healthAmount, playerNumber == 1 ? 10 : 640, 10);
        this.scoreDisplay = new ScoreDisplay(playerNumber == 1 ? 12 : 870, 50);

        root.getChildren().addAll(healthDisplay.getLabel(), scoreDisplay.getLabel());
    }

    public ImageView getImageView() {
        return imageView;
    }

    public HealthDisplay getHealthDisplay() {
        return healthDisplay;
    }

    public ScoreDisplay getScoreDisplay() {
        return scoreDisplay;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public PhysicsComponent getPhysics() {
        return physics;
    }

    public int getScore() {
        return scoreDisplay.getScore();
    }

    public void resetScore() {
        scoreDisplay.setScore(0);
    }

    public void addToScore() {
        scoreDisplay.addToScore();
    }

    public void setMovingLeft(boolean moving) {
        this.movingLeft = moving;
    }

    public void setMovingRight(boolean moving) {
        this.movingRight = moving;
    }

    public void throwProjectile() {
        if (projectiles.size() < Constants.MAX_FIREBALLS_PER_PLAYER) {
            double projectileX = imageView.getLayoutX() + 30;
            double projectileY = imageView.getLayoutY() + imageView.getFitHeight() / 2 - 10;
            Projectile projectile = new Projectile(projectileX, projectileY, fireballSpeed, playerNumber, Constants.PATH_FIREBALL);
            projectiles.add(projectile);
        }
    }

    public void update(double deltaTime) {
        double dx = physics.computeDx(movingLeft, movingRight, deltaTime);
        double currentX = imageView.getLayoutX();
        if (currentX + dx > Constants.SCREEN_LEFT_BOUND && currentX + dx < Constants.SCREEN_RIGHT_BOUND) {
            imageView.setLayoutX(currentX + dx);
        }

        physics.applyGravity(deltaTime);
        double dy = physics.computeDy(deltaTime);
        imageView.setLayoutY(imageView.getLayoutY() + dy);

        int groundY = physics.isCrouching() ? Constants.GROUND_Y_CROUCHING : Constants.GROUND_Y_NORMAL;
        if (imageView.getLayoutY() >= groundY) {
            imageView.setLayoutY(groundY);
            physics.resetVerticalVelocity();
            physics.setOnGround(true);
        } else {
            physics.setOnGround(false);
        }

        projectiles.forEach(projectile -> projectile.update(deltaTime));
    }

    @Override
    public void attack(Character target) {
        System.out.println(name + " attacks " + target.getName() + "!");
        target.receiveDamage(damage);
    }

    @Override
    public void useSpecialAbility(Character target) {
        System.out.println(name + " uses special ability!");
        target.receiveDamage(damage * 2);
    }
}
