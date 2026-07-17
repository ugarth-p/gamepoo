# Asset Pipeline

## Asset Storage

All assets are stored under `src/main/resources/` in subdirectories by type:

```
src/main/resources/
├── images/        # GIF sprites, JPG backgrounds
│   ├── witch.gif
│   ├── soul_knight.gif
│   ├── assasin.gif
│   ├── fire-fireball.gif
│   ├── playing_scene2.jpg
│   ├── wallpaper.jpg
│   └── pixel-art-mountains-waterfall-trees-wallpaper-preview.jpg
├── fonts/         # TTF font files
│   └── (place font2.ttf here)
└── audio/         # WAV/MP3 sound effects
    └── (place .wav files here)
```

## Loading Pipeline

```
getClass().getResourceAsStream("/images/sprite.gif")
    │
    ▼
AssetManager.getImage(path)
    │
    ├─ Check cache (HashMap<path, Image>)
    │   ├─ Hit → return cached Image
    │   └─ Miss → load from classpath, cache, return
    │
    ▼
ImageView construction
    │
    ▼
Add to Scene Graph
```

## Caching Strategy

| Asset Type | Cache Key | Eviction |
|-----------|-----------|----------|
| Images | Resource path (`/images/foo.gif`) | `AssetManager.clearCache()` |
| Fonts | Path + size (`/fonts/foo.ttf@24`) | `AssetManager.clearCache()` |
| Audio | Resource path | `AudioManager.clearCache()` |

## Adding New Assets

1. Place file in appropriate `src/main/resources/<type>/` directory
2. Add constant path to `Constants.java`
3. Load via `AssetManager.getImage(path)` or `AssetManager.getFontOrFallback(path, size, fallback)`

## Asset Resolution

Resources are loaded from the classpath root. The Maven build copies `src/main/resources/` contents to the classpath root automatically. For manual builds (`build.sh`/`run.sh`), resources are copied explicitly.

## Font Loading

```java
// Preferred: with automatic fallback
Font f = AssetManager.getFontOrFallback("/fonts/font2.ttf", 24, "Verdana");

// Direct loading
Font f = AssetManager.getFont("/fonts/font2.ttf", 24);
if (f == null) f = Font.font("Verdana", 24);
```
