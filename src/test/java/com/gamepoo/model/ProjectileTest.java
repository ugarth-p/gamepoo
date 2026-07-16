package com.gamepoo.model;

import com.gamepoo.config.Constants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {

    @Test
    void projectileShouldMoveRightForPlayer1() {
        Projectile p = new Projectile(100, 100, 300, 1, Constants.PATH_FIREBALL);
        p.update(1.0);
        assertTrue(p.getImageView().getLayoutX() > 100);
    }

    @Test
    void projectileShouldMoveLeftForPlayer2() {
        Projectile p = new Projectile(500, 100, 300, 2, Constants.PATH_FIREBALL);
        p.update(1.0);
        assertTrue(p.getImageView().getLayoutX() < 500);
    }

    @Test
    void projectileShouldBeOffScreenPlayer1() {
        Projectile p = new Projectile(2000, 100, 300, 1, Constants.PATH_FIREBALL);
        assertTrue(p.isOffScreen());
    }

    @Test
    void projectileShouldBeOffScreenPlayer2() {
        Projectile p = new Projectile(-100, 100, 300, 2, Constants.PATH_FIREBALL);
        assertTrue(p.isOffScreen());
    }

    @Test
    void projectileShouldNotBeOffScreenWhenInBounds() {
        Projectile p = new Projectile(500, 100, 300, 1, Constants.PATH_FIREBALL);
        assertFalse(p.isOffScreen());
    }
}
