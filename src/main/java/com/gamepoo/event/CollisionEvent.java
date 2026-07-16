package com.gamepoo.event;

import com.gamepoo.model.Projectile;

public record CollisionEvent(Projectile projectile, String targetName) {}
