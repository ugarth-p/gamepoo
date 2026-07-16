package com.gamepoo.config;

public final class Constants {
    private Constants() {}

    public static final int SCENE_WIDTH = 1050;
    public static final int SCENE_HEIGHT = 530;
    public static final int LOBBY_WIDTH = 1024;
    public static final int LOBBY_HEIGHT = 575;

    public static final int GROUND_Y_NORMAL = 300;
    public static final int GROUND_Y_CROUCHING = 350;
    public static final int SCREEN_LEFT_BOUND = -20;
    public static final int SCREEN_RIGHT_BOUND = 1000;

    public static final double GRAVITY = 500;
    public static final double JUMP_VELOCITY = -350;
    public static final double CROUCH_SPEED_OFFSET = 50;

    public static final int PLAYER_HEIGHT = 150;
    public static final int PLAYER_WIDTH = 75;
    public static final int PLAYER_CROUCH_HEIGHT = 100;

    public static final int FIREBALL_HEIGHT = 40;
    public static final int FIREBALL_WIDTH = 60;
    public static final double FIREBALL_SPEED = 300;
    public static final int MAX_FIREBALLS_PER_PLAYER = 5;
    public static final double COLLISION_MARGIN = 20;

    public static final int MAGE_HEALTH = 10;
    public static final int MAGE_DAMAGE = 15;
    public static final int WARRIOR_HEALTH = 10;
    public static final int WARRIOR_DAMAGE = 10;

    public static final int DEFAULT_ROUNDS = 5;

    public static final long ROBBER_VISIBLE_DURATION_NS = 1_000_000_000L;
    public static final int ROBBER_MAX_FIREBALLS = 3;

    public static final String FONT_PATH = "/fonts/font2.ttf";
    public static final String DEFAULT_FONT_FAMILY = "Verdana";

    public static final String PATH_WITCH = "/images/witch.gif";
    public static final String PATH_SOUL_KNIGHT = "/images/soul_knight.gif";
    public static final String PATH_ASSASSIN = "/images/assasin.gif";
    public static final String PATH_FIREBALL = "/images/fire-fireball.gif";
    public static final String PATH_BG_PLAYING = "/images/playing_scene2.jpg";
    public static final String PATH_BG_WALLPAPER = "/images/wallpaper.jpg";
    public static final String PATH_BG_CHARACTER_SELECT = "/images/pixel-art-mountains-waterfall-trees-wallpaper-preview.jpg";
}
