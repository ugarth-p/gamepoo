package com.gamepoo.core;

import com.gamepoo.model.Projectile;
import com.gamepoo.model.RealTimePlayer;
import com.gamepoo.model.Robber;
import com.gamepoo.scene.RealTimeGameScene;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.Iterator;

public class GameLoop {
    private final AnimationTimer timer;
    private long lastUpdate;
    private final RealTimePlayer player1;
    private final RealTimePlayer player2;
    private final Pane root;
    private final RealTimeGameScene gameScene;
    private final Robber robber;
    private boolean running;

    private static final double MAX_DELTA_TIME = 1.0 / 30.0;

    public GameLoop(RealTimePlayer player1, RealTimePlayer player2, Pane root,
                    Robber robber, RealTimeGameScene gameScene) {
        this.player1 = player1;
        this.player2 = player2;
        this.root = root;
        this.robber = robber;
        this.gameScene = gameScene;
        this.running = false;

        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!running) return;
                if (lastUpdate == 0) {
                    lastUpdate = now;
                    return;
                }
                double rawDeltaTime = (now - lastUpdate) / 1_000_000_000.0;
                lastUpdate = now;
                double deltaTime = Math.min(rawDeltaTime, MAX_DELTA_TIME);
                update(deltaTime);
            }
        };
    }

    public void start() {
        lastUpdate = 0;
        running = true;
        timer.start();
    }

    public void stop() {
        running = false;
        timer.stop();
    }

    public boolean isRunning() {
        return running;
    }

    private void update(double deltaTime) {
        if (gameScene.isGameOver()) {
            stop();
            return;
        }

        player1.update(deltaTime);
        player2.update(deltaTime);

        handleRobberAppearance();
        robber.update(deltaTime);

        handleProjectileCollisions(player1, player2);
        handleProjectileCollisions(player2, player1);
        handleRobberProjectileCollisions();

        syncProjectilesToScene(player1);
        syncProjectilesToScene(player2);
        syncProjectilesToScene(robber);
    }

    private void handleRobberAppearance() {
        if (player1.getHealthDisplay().getCurrentHealth() > 5
                && player1.getProjectiles().size() == 5
                && player2.getHealthDisplay().getCurrentHealth() < 5) {
            robber.showsUp(player1.getImageView().getLayoutX() - 150, 1);
        }
        if (player2.getHealthDisplay().getCurrentHealth() > 5
                && player2.getProjectiles().size() == 5
                && player1.getHealthDisplay().getCurrentHealth() < 5) {
            robber.showsUp(player2.getImageView().getLayoutX() + 150, 2);
        }
    }

    private void handleProjectileCollisions(RealTimePlayer attacker, RealTimePlayer defender) {
        Iterator<Projectile> iterator = attacker.getProjectiles().iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            if (projectile.checkCollisionWithOpponent(defender.getImageView())) {
                root.getChildren().remove(projectile.getImageView());
                if (defender.getHealthDisplay().getCurrentHealth() > 0) {
                    defender.getHealthDisplay().reduceHealth();
                }
                if (defender.getHealthDisplay().isDead()) {
                    defender.getHealthDisplay().setToFull();
                    gameScene.onPlayerScored(attacker.getName());
                }
                iterator.remove();
            } else if (projectile.isOffScreen()) {
                root.getChildren().remove(projectile.getImageView());
                iterator.remove();
            }
        }
    }

    private void handleRobberProjectileCollisions() {
        Iterator<Projectile> iterator = robber.getProjectiles().iterator();
        while (iterator.hasNext()) {
            Projectile projectile = iterator.next();
            if (projectile.checkCollisionWithOpponent(player1.getImageView())) {
                root.getChildren().remove(projectile.getImageView());
                if (player1.getHealthDisplay().getCurrentHealth() > 0) {
                    player1.getHealthDisplay().reduceHealth();
                }
                if (player1.getHealthDisplay().isDead()) {
                    player1.getHealthDisplay().setToFull();
                    gameScene.onPlayerScored("Robber");
                }
                iterator.remove();
            } else if (projectile.checkCollisionWithOpponent(player2.getImageView())) {
                root.getChildren().remove(projectile.getImageView());
                if (player2.getHealthDisplay().getCurrentHealth() > 0) {
                    player2.getHealthDisplay().reduceHealth();
                }
                if (player2.getHealthDisplay().isDead()) {
                    player2.getHealthDisplay().setToFull();
                    gameScene.onPlayerScored("Robber");
                }
                iterator.remove();
            } else if (projectile.isOffScreen()) {
                root.getChildren().remove(projectile.getImageView());
                iterator.remove();
            }
        }
    }

    private void syncProjectilesToScene(RealTimePlayer player) {
        player.getProjectiles().forEach(projectile -> {
            if (!root.getChildren().contains(projectile.getImageView())) {
                root.getChildren().add(projectile.getImageView());
            }
        });
    }

    private void syncProjectilesToScene(Robber robber) {
        robber.getProjectiles().forEach(projectile -> {
            if (!root.getChildren().contains(projectile.getImageView())) {
                root.getChildren().add(projectile.getImageView());
            }
        });
    }

    public void resetScores() {
        player1.resetScore();
        player2.resetScore();
    }

    public void restart() {
        player1.getHealthDisplay().setToFull();
        player1.resetScore();
        player2.getHealthDisplay().setToFull();
        player2.resetScore();
    }
}
