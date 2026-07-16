package com.gamepoo.model;

import com.gamepoo.assets.AssetManager;
import com.gamepoo.config.Constants;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Robber {
    private final ImageView imageView;
    private int targetPlayer;
    private boolean isVisible;
    private long startTime;
    private final List<Projectile> projectiles = new ArrayList<>();

    public Robber(double x, double y) {
        this.imageView = new ImageView(AssetManager.getImage(Constants.PATH_ASSASSIN));
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
        this.imageView.setFitHeight(Constants.PLAYER_HEIGHT);
        this.imageView.setFitWidth(Constants.PLAYER_WIDTH);
        this.targetPlayer = 1;
        this.isVisible = false;
        this.imageView.setVisible(false);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public void showsUp(double x, int player) {
        this.isVisible = true;
        this.targetPlayer = player;
        this.imageView.setVisible(true);
        this.imageView.setLayoutX(x);
        this.startTime = System.nanoTime();
        if (player == 2) {
            this.imageView.setScaleX(-1);
        } else {
            this.imageView.setScaleX(1);
        }
        throwProjectile();
    }

    private void throwProjectile() {
        if (projectiles.size() < Constants.ROBBER_MAX_FIREBALLS) {
            double projectileX = imageView.getLayoutX() + 30;
            double projectileY = imageView.getLayoutY() + imageView.getFitHeight() / 2 - 10;
            Projectile projectile = new Projectile(projectileX, projectileY, Constants.FIREBALL_SPEED,
                    targetPlayer == 1 ? 1 : 2, Constants.PATH_FIREBALL);
            projectiles.add(projectile);
        }
    }

    public void update(double deltaTime) {
        if (isVisible && (System.nanoTime() - startTime) >= Constants.ROBBER_VISIBLE_DURATION_NS) {
            isVisible = false;
            imageView.setVisible(false);
        }
        projectiles.forEach(projectile -> projectile.update(deltaTime));
    }
}
