package com.gamepoo.model;

public abstract class Character implements Attackable {
    protected String name;
    protected int health;
    protected int damage;
    protected int level;
    protected int experience;
    protected int maxHealth = 100;
    protected int x;
    protected int y;

    public Character(String name, int health, int damage, int x, int y) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.maxHealth = health;
        this.x = x;
        this.y = y;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void receiveDamage(int damage) {
        health -= damage;
        System.out.println(name + " received " + damage + " damage. HP remaining: " + health);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }
}
