package com.gamepoo.model;

import com.gamepoo.assets.AssetManager;
import javafx.scene.image.ImageView;

public class Projectile {
    private ImageView imageView;
    private double speed;
    private int ownerPlayer;

    public Projectile(double x, double y, double speed, int player, String imagePath) {
        this.ownerPlayer = player;
        this.imageView = new ImageView(AssetManager.getImage(imagePath));
        this.imageView.setLayoutX(x);
        this.imageView.setLayoutY(y);
        this.imageView.setFitHeight(40);
        this.imageView.setFitWidth(60);
        this.speed = speed;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void update(double deltaTime) {
        if (ownerPlayer == 1) {
            imageView.setLayoutX(imageView.getLayoutX() + speed * deltaTime);
        } else {
            imageView.setLayoutX(imageView.getLayoutX() - speed * deltaTime);
        }
    }

    public boolean isOffScreen() {
        if (ownerPlayer == 1) {
            return imageView.getLayoutX() - imageView.getFitWidth() > 1000;
        } else {
            return imageView.getLayoutX() + imageView.getFitWidth() < 0;
        }
    }

    public boolean checkCollisionWithOpponent(ImageView opponent) {
        double margin = 20;
        return (imageView.getLayoutX() + imageView.getFitWidth() - margin >= opponent.getLayoutX()) &&
               (imageView.getLayoutX() + margin <= opponent.getLayoutX() + opponent.getFitWidth()) &&
               (imageView.getLayoutY() + imageView.getFitHeight() - margin >= opponent.getLayoutY()) &&
               (imageView.getLayoutY() + margin <= opponent.getLayoutY() + opponent.getFitHeight());
    }
}
