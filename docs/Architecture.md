# GamePoo Architecture

## Overview

GamePoo is a local multiplayer JavaFX game with two modes: **Turn-Based** (player vs 2 AI) and **Real-Time** (2-player local PvP). The architecture follows Clean Architecture principles with clear separation of concerns.

## Architecture Principles

- **Separation of Concerns**: Each package has a single responsibility
- **Composition over Inheritance**: Game entities use component-based design
- **Dependency Inversion**: High-level modules don't depend on low-level implementations
- **Single Responsibility**: Every class has one reason to change
- **Open for Extension**: New features can be added without modifying existing code

## Layer Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Presentation Layer                       │
│  launcher/GameLauncher  scene/  ui/                         │
├─────────────────────────────────────────────────────────────┤
│                     Application Layer                        │
│  core/GameLoop  core/GameState                              │
├─────────────────────────────────────────────────────────────┤
│                     Game Logic Layer                         │
│  game/  system/  ai/  event/                                │
├─────────────────────────────────────────────────────────────┤
│                     Domain Layer                             │
│  model/  component/                                         │
├─────────────────────────────────────────────────────────────┤
│                     Infrastructure Layer                     │
│  assets/  audio/  save/  config/  input/  render/  physics/ │
└─────────────────────────────────────────────────────────────┘
```

## Package Responsibilities

| Package | Responsibility |
|---------|---------------|
| `launcher` | Application entry point |
| `scene` | Scene management, menu screens, game screens |
| `core` | Game loop, state management |
| `model` | Domain entities (Character, Player, Projectile) |
| `component` | Composable behaviors (Health, Physics) |
| `ui` | HUD elements (HealthDisplay, ScoreDisplay) |
| `assets` | Centralized resource loading and caching |
| `audio` | Sound effect playback |
| `save` | Settings persistence |
| `config` | Constants and game configuration |
| `event` | Event bus for decoupled communication |
| `ai` | AI behavior strategies |

## Key Design Decisions

1. **AssetManager**: Centralizes all resource loading with caching to prevent duplicate loads
2. **PhysicsComponent**: Extracts physics logic from characters into reusable component
3. **HealthComponent**: Separates health tracking from display logic
4. **GameEventBus**: Decouples game systems via publish/subscribe
5. **Fixed Timestep Game Loop**: Uses `MIN(delta, 1/30)` cap to prevent physics instability
6. **SceneManager**: Centralizes scene lifecycle and Stage management
