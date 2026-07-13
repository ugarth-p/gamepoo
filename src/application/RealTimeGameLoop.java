package application;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.Iterator;

public class RealTimeGameLoop {
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    private RealTimeMage player1;
    private RealTimeWarrior player2;
    private Pane root;
    private RealTimeGame game;
    private Robber robber;

    public RealTimeGameLoop(RealTimeMage player1, RealTimeWarrior player2, Pane root, Robber robber, RealTimeGame game) {
        this.player1 = player1;
        this.player2 = player2;
        this.root = root;
        this.robber = robber;
        this.game = game;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }

                double deltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                update(deltaTime, root);
            }
        };
    }

    public void start() {
        gameLoop.start();
    }

    public void stop() {
        gameLoop.stop();
    }

    private void update(double deltaTime, Pane root) {
        if (game.getCurrentRound() == game.getRounds()) gameLoop.stop();

        player1.update(deltaTime);
        player2.update(deltaTime);

        if (player1.getHealthDisplay().getCurrentHealth() > 5 && player1.getFireballs().size() == 5 && player2.getHealthDisplay().getCurrentHealth() < 5) {
            robber.showsUp(player1.getImageView().getLayoutX() - 150, 1);
        }
        if (player2.getHealthDisplay().getCurrentHealth() > 5 && player2.getFireballs().size() == 5 && player1.getHealthDisplay().getCurrentHealth() < 5) {
            robber.showsUp(player2.getImageView().getLayoutX() + 150, 2);
        }
        robber.update(deltaTime);

        // Check collisions for player1's fireballs hitting player2
        Iterator<FireBall> iterator1 = player1.getFireballs().iterator();
        while (iterator1.hasNext()) {
            FireBall fireball = iterator1.next();
            if (fireball.checkCollisionWithOpponent(player2.getImageView())) {
                handleCollision(fireball, player2.getHealthDisplay(), "Mage");
                iterator1.remove();
            }
            if (fireball.isOffScreen()) {
                root.getChildren().remove(fireball.getImageView());
                iterator1.remove();
            }
        }

        // Check collisions for player2's fireballs hitting player1
        Iterator<FireBall> iterator2 = player2.getFireballs().iterator();
        while (iterator2.hasNext()) {
            FireBall fireball = iterator2.next();
            if (fireball.checkCollisionWithOpponent(player1.getImageView())) {
                handleCollision(fireball, player1.getHealthDisplay(), "Warrior");
                iterator2.remove();
            }
            if (fireball.isOffScreen()) {
                root.getChildren().remove(fireball.getImageView());
                iterator2.remove();
            }
        }

        // Check collisions for robber's fireballs
        Iterator<FireBall> iterator3 = robber.getFireballs().iterator();
        while (iterator3.hasNext()) {
            FireBall fireball = iterator3.next();
            if (fireball.checkCollisionWithOpponent(player1.getImageView())) {
                handleCollision(fireball, player1.getHealthDisplay(), "Robber");
                iterator3.remove();
            } else if (fireball.checkCollisionWithOpponent(player2.getImageView())) {
                handleCollision(fireball, player2.getHealthDisplay(), "Robber");
                iterator3.remove();
            }
            if (fireball.isOffScreen()) {
                root.getChildren().remove(fireball.getImageView());
                iterator3.remove();
            }
        }

        // Add new fireballs to the scene
        player1.getFireballs().forEach(fireball -> {
            if (!root.getChildren().contains(fireball.getImageView())) {
                root.getChildren().add(fireball.getImageView());
            }
        });
        player2.getFireballs().forEach(fireball -> {
            if (!root.getChildren().contains(fireball.getImageView())) {
                root.getChildren().add(fireball.getImageView());
            }
        });
        robber.getFireballs().forEach(fireball -> {
            if (!root.getChildren().contains(fireball.getImageView())) {
                root.getChildren().add(fireball.getImageView());
            }
        });
    }

    private void handleCollision(FireBall fireball, HealthDisplay targetHealth, String attackerName) {
        root.getChildren().remove(fireball.getImageView());
        if (targetHealth.getCurrentHealth() > 0) {
            targetHealth.reduceHealth();
        }
        if (targetHealth.isDead()) {
            targetHealth.setToFull();
            game.addRounds(attackerName);
        }
    }

    public void resetPlayerScore() {
        player1.resetScore();
        player2.resetScore();
    }

    public void restartGame() {
        player1.getHealthDisplay().setToFull();
        player1.resetScore();
        player2.getHealthDisplay().setToFull();
        player2.resetScore();
    }
}
