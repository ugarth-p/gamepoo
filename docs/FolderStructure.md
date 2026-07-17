# Folder Structure

```
GamePoo/
├── pom.xml                          # Maven build (auto-downloads JavaFX)
├── build.sh                         # Linux build script (requires JavaFX SDK)
├── run.sh                           # Linux run script
├── README.md
├── Architecture.md
├── FolderStructure.md
├── RefactoringPlan.md
├── ClassDiagram.md
├── GameLoop.md
├── RenderingPipeline.md
├── AssetPipeline.md
├── DeveloperGuide.md
├── .gitignore
├── src/
│   ├── module-info.java
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── gamepoo/
│   │   │           ├── launcher/
│   │   │           │   └── GameLauncher.java
│   │   │           ├── scene/
│   │   │           │   ├── SceneManager.java
│   │   │           │   ├── Lobby.java
│   │   │           │   ├── RealTimeGameScene.java
│   │   │           │   └── TurnBasedGameScene.java
│   │   │           ├── core/
│   │   │           │   ├── GameLoop.java
│   │   │           │   └── GameState.java
│   │   │           ├── model/
│   │   │           │   ├── Attackable.java
│   │   │           │   ├── Character.java
│   │   │           │   ├── RealTimePlayer.java
│   │   │           │   ├── Robber.java
│   │   │           │   ├── Projectile.java
│   │   │           │   ├── TurnBasedWarrior.java
│   │   │           │   ├── TurnBasedMage.java
│   │   │           │   └── TurnBasedThief.java
│   │   │           ├── component/
│   │   │           │   ├── HealthComponent.java
│   │   │           │   └── PhysicsComponent.java
│   │   │           ├── ui/
│   │   │           │   ├── HealthDisplay.java
│   │   │           │   └── ScoreDisplay.java
│   │   │           ├── assets/
│   │   │           │   └── AssetManager.java
│   │   │           ├── audio/
│   │   │           │   └── AudioManager.java
│   │   │           ├── save/
│   │   │           │   └── SaveManager.java
│   │   │           ├── config/
│   │   │           │   └── Constants.java
│   │   │           ├── event/
│   │   │           │   ├── GameEventBus.java
│   │   │           │   ├── CollisionEvent.java
│   │   │           │   ├── ScoreEvent.java
│   │   │           │   └── DamageEvent.java
│   │   │           ├── input/
│   │   │           │   (planned)
│   │   │           ├── physics/
│   │   │           │   (planned)
│   │   │           ├── render/
│   │   │           │   (planned)
│   │   │           ├── game/
│   │   │           │   (planned - game rules, round management)
│   │   │           └── ai/
│   │   │               (planned - AI strategies)
│   │   └── resources/
│   │       ├── images/
│   │       │   ├── witch.gif
│   │       │   ├── soul_knight.gif
│   │       │   ├── assasin.gif
│   │       │   ├── fire-fireball.gif
│   │       │   ├── playing_scene2.jpg
│   │       │   ├── wallpaper.jpg
│   │       │   └── pixel-art-mountains-waterfall-trees-wallpaper-preview.jpg
│   │       └── fonts/
│   │           └── (place font2.ttf here)
│   └── test/
│       └── java/
│           └── com/
│               └── gamepoo/
│                   ├── config/
│                   │   └── ConstantsTest.java
│                   ├── component/
│                   │   ├── HealthComponentTest.java
│                   │   └── PhysicsComponentTest.java
│                   └── model/
│                       ├── CharacterTest.java
│                       └── ProjectileTest.java
└── bin/                             # Compiled classes (after build)
```

## Migration Guide

| Old Class | New Location |
|-----------|-------------|
| `Main.java` | `launcher/GameLauncher.java` |
| `Lobby.java` | `scene/Lobby.java` |
| `Character.java` | `model/Character.java` |
| `Attackable.java` | `model/Attackable.java` |
| `TurnBasedGame.java` | `scene/TurnBasedGameScene.java` |
| `TurnBasedWarrior.java` | `model/TurnBasedWarrior.java` |
| `TurnBasedMage.java` | `model/TurnBasedMage.java` |
| `TurnBasedThief.java` | `model/TurnBasedThief.java` |
| `RealTimeGame.java` | `scene/RealTimeGameScene.java` |
| `RealTimeGameLoop.java` | `core/GameLoop.java` |
| `RealTimeMage.java` | Merged into `model/RealTimePlayer.java` |
| `RealTimeWarrior.java` | Merged into `model/RealTimePlayer.java` |
| `Robber.java` | `model/Robber.java` |
| `FireBall.java` | `model/Projectile.java` |
| `HealthDisplay.java` | `ui/HealthDisplay.java` |
| `ScoreLabel.java` | `ui/ScoreDisplay.java` |
