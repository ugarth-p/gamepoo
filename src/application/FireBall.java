package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FireBall {
    private ImageView imgView;
    private double speed;
    private int playerBall;

    public FireBall(double x, double y, double speed, int player, String url) {
        this.playerBall = player;
        this.imgView = new ImageView(new Image(getClass().getResourceAsStream(url)));
        this.imgView.setLayoutX(x);
        this.imgView.setLayoutY(y);
        this.imgView.setFitHeight(40);
        this.imgView.setFitWidth(60);
        this.speed = speed;
    }

    public ImageView getImageView() {
        return imgView;
    }

    public void update(double deltaTime) {
        if (this.playerBall == 1) {
            imgView.setLayoutX(imgView.getLayoutX() + speed * deltaTime);
        } else {
            imgView.setLayoutX(imgView.getLayoutX() - speed * deltaTime);
        }
    }

    public boolean isOffScreen() {
        if (this.playerBall == 1) {
            return imgView.getLayoutX() - imgView.getFitWidth() > 1000;
        } else {
            return imgView.getLayoutX() + imgView.getFitWidth() < 0;
        }
    }

    public boolean checkCollisionWithOpponent(ImageView opponent) {
        double margin = 20;
        return (imgView.getLayoutX() + imgView.getFitWidth() - margin >= opponent.getLayoutX()) &&
               (imgView.getLayoutX() + margin <= opponent.getLayoutX() + opponent.getFitWidth()) &&
               (imgView.getLayoutY() + imgView.getFitHeight() - margin >= opponent.getLayoutY()) &&
               (imgView.getLayoutY() + margin <= opponent.getLayoutY() + opponent.getFitHeight());
    }
}
