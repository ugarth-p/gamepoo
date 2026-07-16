package com.gamepoo.assets;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

public final class AssetManager {
    private static final Map<String, Image> imageCache = new HashMap<>();
    private static final Map<String, Font> fontCache = new HashMap<>();

    private AssetManager() {}

    public static Image getImage(String path) {
        return imageCache.computeIfAbsent(path, p -> {
            var stream = AssetManager.class.getResourceAsStream(p);
            if (stream == null) {
                System.err.println("Warning: Image not found: " + p);
                return new Image(path);
            }
            return new Image(stream);
        });
    }

    public static Font getFont(String path, double size) {
        String key = path + "@" + size;
        return fontCache.computeIfAbsent(key, k -> {
            var stream = AssetManager.class.getResourceAsStream(path);
            if (stream == null) {
                return null;
            }
            return Font.loadFont(stream, size);
        });
    }

    public static Font getFontOrFallback(String path, double size, String fallbackFamily) {
        Font font = getFont(path, size);
        return font != null ? font : Font.font(fallbackFamily, size);
    }

    public static void clearCache() {
        imageCache.clear();
        fontCache.clear();
    }
}
