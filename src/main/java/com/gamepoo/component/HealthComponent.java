package com.gamepoo.component;

public class HealthComponent {
    private int currentHealth;
    private final int maxHealth;

    public HealthComponent(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void reduce(int amount) {
        currentHealth = Math.max(0, currentHealth - amount);
    }

    public void setToFull() {
        currentHealth = maxHealth;
    }

    public boolean isDead() {
        return currentHealth <= 0;
    }

    public double getHealthRatio() {
        return (double) currentHealth / maxHealth;
    }
}
