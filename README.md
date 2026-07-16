# MergedGame

A local multiplayer game built with JavaFX featuring two game modes: **Turn-Based** and **Real-Time**.

## Prerequisites

- **Java 11+** (tested with OpenJDK 25)
- **JavaFX SDK 25** (download from [gluonhq.com](https://gluonhq.com/products/javafx/))

## Game Modes

### Turn-Based

Classic combat against 2 AI enemies. Choose a character, then take turns attacking or using your special ability. The last one standing wins.

### Real-Time

2-player local combat on one keyboard. Fight in a 5-round match where each fireball hit deals 1 damage. When a player is defeated, they respawn and the attacker scores a round. A **Robber** NPC may appear dynamically to attack the losing player.

## Controls

### Real-Time Mode

| Key | Mage (Player 1) | Warrior (Player 2) |
|-----|-----------------|---------------------|
| W / UP | Jump | Jump |
| A / LEFT | Move Left | Move Left |
| D / RIGHT | Move Right | Move Right |
| S / DOWN | Crouch | Crouch |
| TAB / SHIFT | Fireball | Fireball |

| Key | Action |
|-----|--------|
| ESC | Open pause menu / Return to lobby |
| ENTER | Resume from pause |

### Turn-Based Mode

All actions are mouse-driven (click buttons).

## Characters

### Turn-Based

| Character | Alias | HP | Attack | Special Ability | Special Damage |
|-----------|-------|----|--------|-----------------|----------------|
| Warrior | Thor | 100 | 10 | Rage Strike! | 20 (x2) |
| Mage | Gandalf | 80 | 15 | Magic Storm! | 25 (+10) |
| Thief | Loki | 70 | 12 | Quick Strike! | 36 (x3) |

The **Thief** also has a 20% chance to land a critical hit (double damage) on normal attacks.

### Real-Time

| Character | HP | Damage | Fireball Speed | Max Fireballs |
|-----------|----|--------|----------------|---------------|
| Mage (P1) | 10 | 15 | 300 | 5 |
| Warrior (P2) | 10 | 10 | 300 | 5 |

**Robber (NPC):** Appears when one player has >5 HP and the other <5 HP with 5 fireballs on screen. Deals 1 damage per hit. Disappears after 1 second.

## Game Rules

### Real-Time

- **5 rounds** to win
- Each hit reduces HP by 1
- When a player reaches 0 HP, their HP resets and the attacker scores a round
- After 5 rounds, the player with more rounds wins
- Press ENTER to restart

### Turn-Based

- Pick a character to play as; the other two become your enemies
- Each turn: choose Attack or Special Ability, then pick a target
- After your turn, both AI enemies attack you
- Defeat both enemies to win; if your HP reaches 0, you lose

## Project Structure

```
MergedGame/
├── src/
│   ├── module-info.java
│   └── application/
│       ├── Main.java              # Entry point
│       ├── Lobby.java             # Main menu
│       ├── Character.java         # Abstract base class
│       ├── Attackable.java        # Attack interface
│       ├── TurnBasedGame.java     # Turn-based controller
│       ├── TurnBasedWarrior.java  # Warrior (turn-based)
│       ├── TurnBasedMage.java     # Mage (turn-based)
│       ├── TurnBasedThief.java    # Thief (turn-based)
│       ├── RealTimeGame.java      # Real-time controller
│       ├── RealTimeGameLoop.java  # AnimationTimer game loop
│       ├── RealTimeMage.java      # Mage (real-time)
│       ├── RealTimeWarrior.java   # Warrior (real-time)
│       ├── Robber.java            # NPC enemy
│       ├── FireBall.java          # Projectile
│       ├── HealthDisplay.java     # HP label UI
│       └── ScoreLabel.java        # Score label UI
├── src/*.gif                       # Character sprites
└── src/*.jpg                       # Background images
```
