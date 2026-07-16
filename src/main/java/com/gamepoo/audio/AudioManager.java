package com.gamepoo.audio;

import java.util.HashMap;
import java.util.Map;

public final class AudioManager {
    private static final Map<String, AudioClip> soundCache = new HashMap<>();
    private static double volume = 1.0;
    private static boolean muted;

    private AudioManager() {}

    public static void playSound(String path) {
        if (muted) return;
        soundCache.computeIfAbsent(path, p -> {
            var url = AudioManager.class.getResource(p);
            if (url == null) {
                System.err.println("Warning: Sound not found: " + p);
                return null;
            }
            return new AudioClip(url.toString());
        });
        AudioClip clip = soundCache.get(path);
        if (clip != null) {
            clip.play(volume);
        }
    }

    public static void setVolume(double volume) {
        AudioManager.volume = Math.max(0, Math.min(1, volume));
    }

    public static double getVolume() {
        return volume;
    }

    public static void setMuted(boolean muted) {
        AudioManager.muted = muted;
    }

    public static boolean isMuted() {
        return muted;
    }

    public static void clearCache() {
        soundCache.clear();
    }

    private static class AudioClip {
        private final String url;
        private javafx.scene.media.AudioClip clip;

        AudioClip(String url) {
            this.url = url;
        }

        void play(double volume) {
            if (clip == null) {
                clip = new javafx.scene.media.AudioClip(url);
            }
            clip.setVolume(volume);
            clip.play();
        }
    }
}
