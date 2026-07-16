# Game Loop Architecture

## Real-Time Mode

The real-time game loop uses JavaFX's `AnimationTimer` with a **fixed maximum timestep**.

### Flow

```
AnimationTimer.handle(now)
    │
    ├─ Calculate raw delta time
    ├─ Cap delta at 1/30s maximum
    │
    ├─ Check game over condition
    │
    ├─ Update Player 1 (movement, physics, projectiles)
    ├─ Update Player 2 (movement, physics, projectiles)
    │
    ├─ Check Robber spawn conditions
    ├─ Update Robber
    │
    ├─ Handle Player 1 → Player 2 collisions
    ├─ Handle Player 2 → Player 1 collisions
    ├─ Handle Robber → Player collisions
    │
    ├─ Sync new projectiles to scene graph
    │
    └─ Remove off-screen projectiles
```

### Delta Time Management

```java
double MAX_DELTA_TIME = 1.0 / 30.0;
double rawDelta = (now - lastUpdate) / 1_000_000_000.0;
double delta = Math.min(rawDelta, MAX_DELTA_TIME);
```

This prevents physics explosion when the game is tabbed out (large delta) or during GC pauses.

### Fixed Timestep vs Variable

| Aspect | Before (old code) | After (refactored) |
|--------|-------------------|--------------------|
| Delta cap | None | 1/30s max |
| Physics stability | Unstable when tabbing out | Stable |
| Frame rate | Tied to monitor refresh | Smooth with cap |

## Turn-Based Mode

No continuous game loop. The turn-based mode is entirely **event-driven**:

```
Player clicks "Attack" → Show target selection
Player clicks target  → Execute attack → Update HP texts
                     → AI turn → Both enemies attack
                     → Check win/lose
                     → Player turn again
```

## State Machine

```
┌─────────┐
│  MENU    │
└────┬────┘
     │
     ├──────────────┐
     ▼              ▼
┌──────────┐ ┌──────────────┐
│ TURN     │ │ REAL-TIME    │
│ BASED    │ │              │
└────┬─────┘ └──────┬───────┘
     │              │
     ▼              ▼
┌──────────┐ ┌──────────────┐
│ GAME OVER│ │  PLAYING     │
│ /VICTORY │ │  ┌─────┐     │
└──────────┘ │  │PAUSE│     │
             │  └─────┘     │
             └──────────────┘
```
