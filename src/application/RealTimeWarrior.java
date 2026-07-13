package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class RealTimeWarrior extends Character implements Attackable {
    private ImageView imgView;
    private double speed;

    private double gravity = 500;
    private double velocityY = 0;
    private boolean onGround = false;

    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean shifting = false;

    private List<FireBall> fireballs = new ArrayList<>();
    private double fireballSpeed = 300;

    private HealthDisplay healthDisplay;
    private ScoreLabel scoreLabel;

    public RealTimeWarrior(double x, double y, double speed, Pane root) {
        super("Warrior", 10, 10, (int) x, (int) y);
        this.scoreLabel = new ScoreLabel(870, 50);

        Image sprite = new Image(getClass().getResourceAsStream("/soul_knight.gif"));
        this.imgView = new ImageView(sprite);
        this.imgView.setLayoutX(x);
        this.imgView.setLayoutY(y);
        this.imgView.setFitHeight(150);
        this.imgView.setFitWidth(75);
        this.speed = speed;
        this.health = 10;

        this.healthDisplay = new HealthDisplay("Warrior", 10, 640, 10);
        root.getChildren().add(this.healthDisplay.getLabel());
        root.getChildren().add(this.scoreLabel.getLabel());
    }

    @Override
    public void attack(Character target) {
        System.out.println(name + " attacks " + target.getName() + "!");
        target.receiveDamage(damage);
    }

    @Override
    public void useSpecialAbility(Character target) {
        System.out.println(name + " uses special ability: Rage Strike!");
        target.receiveDamage(damage * 2);
    }

    public int getScore() {
        return scoreLabel.getScore();
    }

    public void resetScore() {
        this.scoreLabel.setScore(0);
    }

    public void addToScore() {
        this.scoreLabel.addToScore();
    }

    public ImageView getImageView() {
        return imgView;
    }

    public List<FireBall> getFireballs() {
        return fireballs;
    }

    public HealthDisplay getHealthDisplay() {
        return healthDisplay;
    }

    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            shifting = true;
            this.imgView.setFitHeight(100);
        }

        if (event.getCode() == KeyCode.LEFT) movingLeft = true;
        if (event.getCode() == KeyCode.RIGHT) movingRight = true;

        if (event.getCode() == KeyCode.UP && onGround) {
            velocityY = -350;
            onGround = false;
        }

        if (event.getCode() == KeyCode.SHIFT && !shifting) {
            throwFireball();
        }
    }

    public void onKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            shifting = false;
            this.imgView.setFitHeight(150);
        }

        if (event.getCode() == KeyCode.LEFT) movingLeft = false;
        if (event.getCode() == KeyCode.RIGHT) movingRight = false;
    }

    public void update(double deltaTime) {
        double dx = 0;
        if (imgView.getLayoutX() > -20) {
            if (movingLeft) dx -= speed * deltaTime;
        }
        if (imgView.getLayoutX() < 1000) {
            if (movingRight) dx += speed * deltaTime;
        }

        if (!onGround && !shifting) {
            velocityY += gravity * deltaTime;
        }
        double dy = velocityY * deltaTime;

        if (shifting && !onGround) {
            dy = 0;
            dy += (speed - 50) * deltaTime;
        }

        imgView.setLayoutX(imgView.getLayoutX() + dx);
        imgView.setLayoutY(imgView.getLayoutY() + dy);

        if (!shifting) {
            if (imgView.getLayoutY() >= 300) {
                imgView.setLayoutY(300);
                velocityY = 0;
                onGround = true;
            } else {
                onGround = false;
            }
        } else {
            if (imgView.getLayoutY() >= 350) {
                imgView.setLayoutY(350);
                velocityY = 0;
                onGround = true;
            } else {
                onGround = false;
            }
        }

        fireballs.forEach(fireball -> fireball.update(deltaTime));
    }

    private void throwFireball() {
        if (fireballs.size() < 5) {
            double fireballX = imgView.getLayoutX();
            double fireballY = imgView.getLayoutY() + imgView.getFitHeight() / 2 - 10;
            FireBall fireball = new FireBall(fireballX, fireballY, fireballSpeed, 2, "/fire-fireball.gif");
            fireballs.add(fireball);
        }
    }
}
