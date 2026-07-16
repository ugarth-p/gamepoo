package com.gamepoo.component;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhysicsComponentTest {

    @Test
    void shouldStartOnGround() {
        PhysicsComponent physics = new PhysicsComponent(250);
        assertTrue(physics.isOnGround());
        assertFalse(physics.isCrouching());
    }

    @Test
    void shouldJump() {
        PhysicsComponent physics = new PhysicsComponent(250);
        physics.jump();
        assertFalse(physics.isOnGround());
        assertTrue(physics.getVelocityY() < 0);
    }

    @Test
    void shouldNotJumpMidAir() {
        PhysicsComponent physics = new PhysicsComponent(250);
        physics.jump();
        double initialVy = physics.getVelocityY();
        physics.jump();
        assertEquals(initialVy, physics.getVelocityY(), 0.001);
    }

    @Test
    void applyGravityShouldIncreaseVelocityWhenAirborne() {
        PhysicsComponent physics = new PhysicsComponent(250);
        physics.jump();
        double vyBefore = physics.getVelocityY();
        physics.applyGravity(0.016);
        assertTrue(physics.getVelocityY() > vyBefore);
    }

    @Test
    void crouchingShouldPreventGravity() {
        PhysicsComponent physics = new PhysicsComponent(250);
        physics.jump();
        physics.setCrouching(true);
        physics.applyGravity(0.016);
        physics.applyGravity(0.016);
        assertEquals(0, physics.computeDy(0.016), 0.001);
    }
}
