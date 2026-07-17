# Developer Guide

## Setup

### Prerequisites
- JDK 21+ (Temurin recommended)
- Maven 3.9+ (for automated builds) OR JavaFX SDK 25 (for manual builds)

### Quick Start (Maven)
```bash
git clone <repo>
cd GamePoo
mvn clean compile
mvn javafx:run
```

### Quick Start (Manual)
```bash
./build.sh   # Compiles and packages
./run.sh     # Compiles and runs
```

### Run Tests
```bash
mvn test
```

## Architecture Overview

See `Architecture.md` and `FolderStructure.md` for detailed layout.

## Adding a New Character

### Real-Time Mode
1. No changes needed — use `RealTimePlayer` with different parameters
2. Add sprite to `src/main/resources/images/`
3. Pass sprite path to `RealTimePlayer` constructor

### Turn-Based Mode
1. Create class extending `Character` in `model/` package
2. Implement `attack()` and `useSpecialAbility()`
3. Add instance to `TurnBasedGameScene` character options

## Adding a New Scene

1. Create class in `scene/` package
2. Call `SceneManager.setScene(root, width, height, title)` to display
3. Use `new Lobby().start()` to return to main menu

## Event System

```java
// Publish an event
GameEventBus.publish(new ScoreEvent("Mage", 3));

// Subscribe to events
GameEventBus.subscribe(ScoreEvent.class, event -> {
    System.out.println(event.scorerName() + " scored!");
});
```

## Asset Loading

```java
// Images (cached)
Image img = AssetManager.getImage("/images/sprite.gif");

// Fonts (cached, with fallback)
Font f = AssetManager.getFontOrFallback("/fonts/custom.ttf", 24, "Verdana");
```

## Coding Conventions

- Package: `com.gamepoo.<layer>`
- Classes: PascalCase
- Methods: camelCase
- Constants: UPPER_SNAKE_CASE
- No magic numbers — use `Constants.java`
- No duplicate code — extract to shared methods/components
- Favor composition over inheritance
- Keep methods under 30 lines
- Keep classes under 300 lines

## Testing

- JUnit 5 for unit tests
- Test files mirror source structure under `src/test/java/`
- Focus on: model logic, components, collision, physics, constants

## Common Tasks

### Add a Sound Effect
1. Place .wav file in `src/main/resources/audio/`
2. Call `AudioManager.playSound("/audio/effect.wav")`

### Change Game Speed
- Modify `MAX_DELTA_TIME` in `GameLoop.java` (lower = slower)
- Modify `Constants.GRAVITY` for physics speed
- Modify individual character `speed` parameter

### Change Round Count
- Modify `Constants.DEFAULT_ROUNDS`
