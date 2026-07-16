package com.gamepoo.component;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthComponentTest {

    @Test
    void shouldStartAtFullHealth() {
        HealthComponent health = new HealthComponent(100);
        assertEquals(100, health.getCurrentHealth());
        assertEquals(100, health.getMaxHealth());
        assertFalse(health.isDead());
    }

    @Test
    void shouldReduceHealth() {
        HealthComponent health = new HealthComponent(100);
        health.reduce(30);
        assertEquals(70, health.getCurrentHealth());
        assertFalse(health.isDead());
    }

    @Test
    void shouldNotGoBelowZero() {
        HealthComponent health = new HealthComponent(100);
        health.reduce(200);
        assertEquals(0, health.getCurrentHealth());
        assertTrue(health.isDead());
    }

    @Test
    void shouldResetToFull() {
        HealthComponent health = new HealthComponent(100);
        health.reduce(40);
        health.setToFull();
        assertEquals(100, health.getCurrentHealth());
    }

    @Test
    void shouldReportDeadWhenZero() {
        HealthComponent health = new HealthComponent(1);
        assertFalse(health.isDead());
        health.reduce(1);
        assertTrue(health.isDead());
    }

    @Test
    void healthRatioShouldBeCorrect() {
        HealthComponent health = new HealthComponent(100);
        health.reduce(25);
        assertEquals(0.75, health.getHealthRatio(), 0.001);
    }
}
