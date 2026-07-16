package com.gamepoo.save;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class SaveManager {
    private static final Path SAVE_DIR = Paths.get(System.getProperty("user.home"), ".gamepoo");
    private static final Path SETTINGS_FILE = SAVE_DIR.resolve("settings.properties");
    private static final Properties settings = new Properties();

    private SaveManager() {}

    public static void initialize() {
        try {
            Files.createDirectories(SAVE_DIR);
            if (Files.exists(SETTINGS_FILE)) {
                try (var input = Files.newInputStream(SETTINGS_FILE)) {
                    settings.load(input);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize save directory: " + e.getMessage());
        }
    }

    public static String getSetting(String key, String defaultValue) {
        return settings.getProperty(key, defaultValue);
    }

    public static void setSetting(String key, String value) {
        settings.setProperty(key, value);
    }

    public static void saveSettings() {
        try (var output = Files.newOutputStream(SETTINGS_FILE)) {
            settings.store(output, "GamePoo Settings");
        } catch (IOException e) {
            System.err.println("Failed to save settings: " + e.getMessage());
        }
    }

    public static int getIntSetting(String key, int defaultValue) {
        try {
            return Integer.parseInt(settings.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double getDoubleSetting(String key, double defaultValue) {
        try {
            return Double.parseDouble(settings.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
