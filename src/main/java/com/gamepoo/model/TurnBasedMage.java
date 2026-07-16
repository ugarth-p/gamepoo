package com.gamepoo.model;

public class TurnBasedMage extends Character {
    public TurnBasedMage(String name, int x, int y) {
        super(name, 80, 15, x, y);
    }

    @Override
    public void attack(Character target) {
        System.out.println(name + " casts a spell on " + target.getName() + "!");
        target.receiveDamage(damage);
    }

    @Override
    public void useSpecialAbility(Character target) {
        System.out.println(name + " summons a Magic Storm!");
        target.receiveDamage(damage + 10);
    }
}
