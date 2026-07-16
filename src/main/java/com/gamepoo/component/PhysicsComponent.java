package com.gamepoo.component;

import com.gamepoo.config.Constants;

public class PhysicsComponent {
    private double velocityX;
    private double velocityY;
    private boolean onGround;
    private boolean crouching;
    private final double speed;
    private final double jumpVelocity;

    public PhysicsComponent(double speed) {
        this.speed = speed;
        this.jumpVelocity = Constants.JUMP_VELOCITY;
        this.velocityY = 0;
        this.onGround = true;
    }

    public void applyGravity(double deltaTime) {
        if (!onGround && !crouching) {
            velocityY += Constants.GRAVITY * deltaTime;
        }
    }

    public double computeDx(boolean moveLeft, boolean moveRight, double deltaTime) {
        velocityX = 0;
        if (moveLeft) velocityX -= speed;
        if (moveRight) velocityX += speed;
        return velocityX * deltaTime;
    }

    public double computeDy(double deltaTime) {
        double dy = velocityY * deltaTime;
        if (crouching && !onGround) {
            dy = 0;
            dy += (speed - Constants.CROUCH_SPEED_OFFSET) * deltaTime;
        }
        return dy;
    }

    public void jump() {
        if (onGround) {
            velocityY = jumpVelocity;
            onGround = false;
        }
    }

    public void setCrouching(boolean crouching) {
        this.crouching = crouching;
    }

    public boolean isCrouching() {
        return crouching;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void resetVerticalVelocity() {
        velocityY = 0;
    }

    public double getVelocityY() {
        return velocityY;
    }
}
