package com.gamepoo.event;

public record DamageEvent(String target, int damage, int remainingHealth) {}
