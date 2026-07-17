# Refactoring Plan

## Completed Steps

### Step 1: Package Structure (DONE)
- Created proper package hierarchy under `com.gamepoo.*`
- Moved assets to `src/main/resources/images/`
- Created `launcher`, `scene`, `core`, `model`, `component`, `ui`, `assets`, `config`, `event`, `audio`, `save` packages

### Step 2: Asset Manager (DONE)
- Created `AssetManager` with image and font caching
- Eliminates duplicate resource loading (fonts were loaded 6+ times)
- Provides fallback mechanism for missing fonts

### Step 3: Constants Extraction (DONE)
- Extracted all magic numbers into `Constants.java`
- Every hardcoded value now has a named constant

### Step 4: Fixed Timestep Game Loop (DONE)
- `GameLoop` caps delta time at 1/30s to prevent physics instability
- Clean separation from scene management

### Step 5: Component-Based Architecture (DONE)
- `PhysicsComponent`: Unified physics logic (was duplicated in Mage/Warrior)
- `HealthComponent`: Separated health tracking from display

### Step 6: Merge Duplicate Real-Time Characters (DONE)
- `RealTimePlayer` replaces both `RealTimeMage` and `RealTimeWarrior`
- Input handling unified in `RealTimeGameScene`
- Reduces code duplication by ~60%

### Step 7: Scene Management (DONE)
- `SceneManager` centralizes Stage/scene lifecycle
- Clean navigation between Lobby, Turn-Based, Real-Time scenes

### Step 8: Event System (DONE)
- `GameEventBus` for decoupled communication
- Typed event records (`CollisionEvent`, `ScoreEvent`, `DamageEvent`)

### Step 9: Audio System (DONE)
- `AudioManager` with sound caching and volume control
- Ready for .wav/.mp3 assets

### Step 10: Save System (DONE)
- `SaveManager` persists settings to `~/.gamepoo/settings.properties`
- Supports string, int, double settings

### Step 11: Build System (DONE)
- Added `pom.xml` for Maven with automatic JavaFX dependency resolution
- Updated `build.sh` and `run.sh` for new structure

### Step 12: Unit Tests (DONE)
- Tests for `Constants`, `HealthComponent`, `PhysicsComponent`, `Character`, `Projectile`

## Planned Steps

### Step 13: Input Manager (MEDIUM)
- Extract input handling from `RealTimeGameScene` into dedicated `InputManager`
- Add key rebinding support via `SaveManager`
- Add input buffering for turn-based mode

### Step 14: Collision System (MEDIUM)
- Extract collision detection from `GameLoop` into `CollisionSystem`
- Add spatial partitioning for performance
- Add collision layers (player projectiles vs enemy projectiles)

### Step 15: AI System (MEDIUM)
- Create `AIStrategy` interface
- Implement multiple strategies (aggressive, defensive, random)
- Replace hardcoded AI in `TurnBasedGameScene`

### Step 16: UI Polish (LOW)
- Replace inline styles with CSS
- Add observable properties for automatic UI updates
- Create reusable UI components (MenuButton, styled panels)

### Step 17: Rendering Layer (LOW)
- Extract rendering from entity update methods
- Add camera/viewport capability
- Implement render layers (background, entities, UI)

### Step 18: Audio Assets (LOW)
- Add .wav/.mp3 files for combat sounds, UI clicks, background music
- Integrate with event bus

### Step 19: Localization (LOW)
- Extract all strings to resource bundles
- Support English + French

### Step 20: Accessibility (LOW)
- Keyboard navigation indicators
- High contrast mode
- Screen reader friendly labels
