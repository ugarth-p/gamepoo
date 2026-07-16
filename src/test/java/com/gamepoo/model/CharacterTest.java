package com.gamepoo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void characterShouldTakeDamage() {
        Character c = new TurnBasedWarrior("Test", 0, 0);
        c.receiveDamage(30);
        assertEquals(70, c.getHealth());
        assertTrue(c.isAlive());
    }

    @Test
    void characterShouldDie() {
        Character c = new TurnBasedWarrior("Test", 0, 0);
        c.receiveDamage(100);
        assertEquals(0, c.getHealth());
        assertFalse(c.isAlive());
    }

    @Test
    void warriorStatsShouldBeCorrect() {
        Character c = new TurnBasedWarrior("Thor", 0, 0);
        assertEquals(100, c.getMaxHealth());
        assertEquals(10, c.getDamage());
    }

    @Test
    void mageStatsShouldBeCorrect() {
        Character c = new TurnBasedMage("Gandalf", 0, 0);
        assertEquals(80, c.getMaxHealth());
        assertEquals(15, c.getDamage());
    }

    @Test
    void thiefStatsShouldBeCorrect() {
        Character c = new TurnBasedThief("Loki", 0, 0);
        assertEquals(70, c.getMaxHealth());
        assertEquals(12, c.getDamage());
    }

    @Test
    void thiefShouldHaveCriticalHitChance() {
        TurnBasedThief thief = new TurnBasedThief("Loki", 0, 0);
        TurnBasedWarrior target = new TurnBasedWarrior("Target", 0, 0);
        int initialHealth = target.getHealth();
        thief.attack(target);
        int damageTaken = initialHealth - target.getHealth();
        assertTrue(damageTaken == 12 || damageTaken == 24,
                "Thief should deal 12 or 24 damage, but dealt " + damageTaken);
    }

    @Test
    void warriorSpecialShouldDoubleDamage() {
        TurnBasedWarrior warrior = new TurnBasedWarrior("Thor", 0, 0);
        TurnBasedMage target = new TurnBasedMage("Target", 0, 0);
        warrior.useSpecialAbility(target);
        assertEquals(80 - 20, target.getHealth());
    }

    @Test
    void mageSpecialShouldAddTenDamage() {
        TurnBasedMage mage = new TurnBasedMage("Gandalf", 0, 0);
        TurnBasedWarrior target = new TurnBasedWarrior("Target", 0, 0);
        mage.useSpecialAbility(target);
        assertEquals(100 - 25, target.getHealth());
    }

    @Test
    void thiefSpecialShouldTripleDamage() {
        TurnBasedThief thief = new TurnBasedThief("Loki", 0, 0);
        TurnBasedWarrior target = new TurnBasedWarrior("Target", 0, 0);
        thief.useSpecialAbility(target);
        assertEquals(100 - 36, target.getHealth());
    }
}
