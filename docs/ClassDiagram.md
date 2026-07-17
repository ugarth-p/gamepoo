# Class Diagram

```
┌─────────────────────────────────────────────────────┐
│                  Attackable <<interface>>             │
│  ┌──────────────────────────────────────────────────┐│
│  │ + attack(Character target)                       ││
│  │ + useSpecialAbility(Character target)            ││
│  └──────────────────────────────────────────────────┘│
└───────────────────────┬─────────────────────────────┘
                        │ implements
┌──────────────────────────────────────────────────────┐
│                   Character (abstract)                 │
│  ┌──────────────────────────────────────────────────┐│
│  │ # name: String                                   ││
│  │ # health: int                                    ││
│  │ # damage: int                                    ││
│  │ # maxHealth: int                                 ││
│  └──────────────────────────────────────────────────┘│
└───┬──────────────────┬───────────────────┬───────────┘
    │                  │                   │
    ▼                  ▼                   ▼
┌────────────┐ ┌────────────┐ ┌──────────────┐
│TurnBased   │ │TurnBased   │ │TurnBased     │
│Warrior     │ │Mage        │ │Thief         │
│ (Thor)     │ │ (Gandalf)  │ │ (Loki)       │
│ Atk: 10    │ │ Atk: 15    │ │ Atk: 12      │
│ Sp: x2     │ │ Sp: +10    │ │ Sp: x3       │
│ HP: 100    │ │ HP: 80     │ │ HP: 70       │
└────────────┘ └────────────┘ └──────────────┘

┌──────────────────────────────────────────────────────┐
│                   RealTimePlayer                       │
│  ┌──────────────────────────────────────────────────┐│
│  │ - imageView: ImageView                           ││
│  │ - physics: PhysicsComponent                      ││
│  │ - health: HealthComponent                        ││
│  │ - healthDisplay: HealthDisplay                   ││
│  │ - scoreDisplay: ScoreDisplay                     ││
│  │ - projectiles: List<Projectile>                  ││
│  │ + update(deltaTime)                              ││
│  │ + throwProjectile()                              ││
│  └──────────────────────────────────────────────────┘│
└──────────────────────────────────────────────────────┘

┌──────────────────────────┐  ┌──────────────────────────┐
│    HealthComponent       │  │    PhysicsComponent      │
│  ┌──────────────────────┐│  │  ┌──────────────────────┐│
│  │ + currentHealth: int ││  │  │ + velocityY: double  ││
│  │ + maxHealth: int     ││  │  │ + onGround: boolean  ││
│  │ + reduce(amount)     ││  │  │ + jump()             ││
│  │ + isDead(): boolean  ││  │  │ + applyGravity()     ││
│  └──────────────────────┘│  │  │ + computeDx/Dy()     ││
└──────────────────────────┘  │  └──────────────────────┘│
                              └──────────────────────────┘

┌──────────────────────────┐  ┌──────────────────────────┐
│       Projectile          │  │         Robber            │
│  ┌──────────────────────┐│  │  ┌──────────────────────┐│
│  │ + imageView: IV      ││  │  │ + targetPlayer: int  ││
│  │ + speed: double      ││  │  │ + showsUp(x, player) ││
│  │ + update(delta)      ││  │  │ + update(delta)      ││
│  │ + isOffScreen()      ││  │  └──────────────────────┘│
│  + checkCollision()     ││  └──────────────────────────┘
│  └──────────────────────┘│
└──────────────────────────┘

┌──────────────────────────┐  ┌──────────────────────────┐
│       GameLoop            │  │      SceneManager         │
│  ┌──────────────────────┐│  │  ┌──────────────────────┐│
│  │ - timer: AnimTimer   ││  │  │ + initialize(Stage)  ││
│  │ - player1, player2   ││  │  │ + setScene(root, w,h)││
│  │ - robber: Robber     ││  │  │ + getStage(): Stage  ││
│  │ + start()/stop()     ││  │  └──────────────────────┘│
│  │ - update(delta)      ││  └──────────────────────────┘
│  └──────────────────────┘│
└──────────────────────────┘

┌──────────────────────────┐  ┌──────────────────────────┐
│    AssetManager           │  │      GameEventBus         │
│  ┌──────────────────────┐│  │  ┌──────────────────────┐│
│  │ + getImage(path): Img││  │  │ + subscribe(type, fn)││
│  │ + getFont(path, sz)  ││  │  │ + publish(event)     ││
│  │ + clearCache()       ││  │  │ + clear()            ││
│  └──────────────────────┘│  │  └──────────────────────┘│
└──────────────────────────┘  └──────────────────────────┘
```
