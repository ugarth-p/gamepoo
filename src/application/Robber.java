package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Robber {
    private ImageView imgView;

    private int targetPlayer;
    private boolean isVisible;
    private long startTime;
    private static final long VISIBLE_DURATION = 1_000_000_000L;

    private List<FireBall> fireballs = new ArrayList<>();
    private double fireballSpeed = 300;

    public Robber(double x, double y) {
        Image sprite = new Image(getClass().getResourceAsStream("/assasin.gif"));
        this.imgView = new ImageView(sprite);
        this.imgView.setLayoutX(x);
        this.imgView.setLayoutY(y);
        this.imgView.setFitHeight(150);
        this.imgView.setFitWidth(75);
        this.targetPlayer = 1;
        this.isVisible = false;
        this.imgView.setVisible(isVisible);
    }

    public ImageView getImageView() {
        return imgView;
    }

    public List<FireBall> getFireballs() {
        return fireballs;
    }

    public void showsUp(double x, int player) {
        this.isVisible = true;
        this.targetPlayer = player;
        this.imgView.setVisible(isVisible);
        this.imgView.setLayoutX(x);
        this.startTime = System.nanoTime();
        if (player == 2) {
            this.imgView.setScaleX(-1);
        } else {
            this.imgView.setScaleX(1);
        }
        throwFireball();
    }

    private void throwFireball() {
        if (fireballs.size() < 3) {
            double fireballX = imgView.getLayoutX() + 30;
            double fireballY = imgView.getLayoutY() + imgView.getFitHeight() / 2 - 10;
            FireBall fireball = new FireBall(fireballX, fireballY, fireballSpeed, targetPlayer == 1 ? 1 : 2, "/fire-fireball.gif");
            fireballs.add(fireball);
        }
    }

    public void update(double deltaTime) {
        if (isVisible && (System.nanoTime() - startTime) >= VISIBLE_DURATION) {
            isVisible = false;
            this.imgView.setVisible(isVisible);
        }

        fireballs.forEach(fireball -> fireball.update(deltaTime));
    }
}
