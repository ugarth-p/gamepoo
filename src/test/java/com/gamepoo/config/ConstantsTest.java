package com.gamepoo.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void constantsShouldBePositive() {
        assertTrue(Constants.SCENE_WIDTH > 0);
        assertTrue(Constants.SCENE_HEIGHT > 0);
        assertTrue(Constants.GRAVITY > 0);
        assertTrue(Constants.JUMP_VELOCITY < 0);
    }

    @Test
    void playerDimensionsShouldBeReasonable() {
        assertTrue(Constants.PLAYER_WIDTH > 0);
        assertTrue(Constants.PLAYER_HEIGHT > Constants.PLAYER_CROUCH_HEIGHT);
    }

    @Test
    void fireballLimitsShouldBePositive() {
        assertTrue(Constants.MAX_FIREBALLS_PER_PLAYER > 0);
        assertTrue(Constants.ROBBER_MAX_FIREBALLS > 0);
        assertTrue(Constants.FIREBALL_SPEED > 0);
    }

    @Test
    void roundDefaultsShouldBePositive() {
        assertTrue(Constants.DEFAULT_ROUNDS > 0);
    }
}
