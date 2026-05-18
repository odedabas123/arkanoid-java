# 🎮 Arkanoid Game — Java

A fully playable Arkanoid (Breakout) clone built in Java from scratch as part of an Object-Oriented Programming course at Bar-Ilan University.

---

## 🕹️ How to Play

- Move the **paddle** left and right using the **arrow keys**
- Bounce the ball to destroy all the **bricks**
- Don't let the ball fall off the bottom — you lose when all balls are gone
- Destroy bricks to earn points — different rows award different scores
- **Win** by clearing all bricks | **Lose** if all balls fall

---

## 🎯 Features

- **Triangle brick layout** — 6 rows, each row shorter than the last (12 down to 7 bricks)
- **Multi-HP bricks** — some bricks require multiple hits; they get darker with each hit
- **Scoring system** — each brick row has a different point value (100–200 pts per brick)
- **Physics-based paddle** — the paddle is divided into 5 regions, each deflecting the ball at a different angle
- **Side-wrapping paddle** — paddle wraps around the screen edges
- **2 balls** — starts with two balls in play
- **Win/Lose screen** — displays result and final score, then closes after 3 seconds
- **60 FPS game loop** with frame timing

---

## 🛠️ Tech Stack

| | |
|---|---|
| Language | Java |
| GUI Library | biuoop-1.4.jar (Bar-Ilan University OOP course library) |
| Build Tool | Apache Ant (`build.xml`) |
| IDE | IntelliJ IDEA |

---

## 🏗️ Architecture

The project is built around two core interfaces:

- **`Sprite`** — anything that can be drawn and updated each frame (`Ball`, `Block`, `Paddle`)
- **`Collidable`** — anything the ball can collide with (`Block`, `Paddle`, walls)

### Class Breakdown

| Class | Role |
|---|---|
| `Ass3Game` | Entry point — creates and runs the game |
| `Game` | Main game loop, initialization, score tracking, win/lose logic |
| `GameEnvironment` | Holds all collidables, finds closest collision on a trajectory |
| `SpriteCollection` | Holds all sprites, calls `drawOn` and `timePassed` each frame |
| `Ball` | Moves each frame, detects collisions via trajectory line, updates velocity |
| `Paddle` | Player-controlled, reads keyboard input, 5-region hit logic |
| `Block` | Bricks and walls — destructible blocks track HP and award points |
| `Velocity` | dx/dy model, supports angle+speed construction |
| `Line` | Line segment with intersection math |
| `Point` | 2D coordinate (implemented as a Java record) |
| `Rectangle` | Geometry + drawing, exposes 4 edges as `Line` objects for collision |
| `CollisionInfo` | Pairs a collision point with the object that was hit |
| `Config` | Central constants — screen size, colors, ball speed, brick HP/points |

### Collision Detection Flow

Each frame, the ball computes a **trajectory line** from its current position to its next position (extended by its radius). It asks `GameEnvironment` for the closest `Collidable` that intersects this line. If a collision is found, it calls `hit()` on the object, which returns the new velocity. The ball then moves a half-step in the new direction to avoid getting stuck inside the object.

---

## 🚀 How to Run

### Prerequisites
- Java JDK 8 or higher
- Apache Ant

### Build & Run

```bash
git clone https://github.com/odedabas123/arkanoid-java.git
cd arkanoid-java

ant compile
ant run
```

Or compile manually:

```bash
javac -cp biuoop-1.4.jar src/*.java -d out/
java -cp biuoop-1.4.jar:out Ass3Game
```

> **Windows:** replace `:` with `;` in the classpath

---

## 📐 Game Configuration

All game settings are in `Config.java`:

| Setting | Value |
|---|---|
| Screen | 800 × 600 |
| Ball speed | 3.0 px/frame |
| Ball size | 5px radius |
| Paddle speed | 10 px/frame |
| Paddle size | 75 × 10 px |
| Brick size | 50 × 20 px |
| Rows | 6 (12 → 7 bricks per row) |
| HP per row | 1, 2, 1, 3, 2, 1 |
| Points per brick | 100, 150, 100, 200, 150, 100 |

---

## 👨‍💻 Author

**Oded Abas** — Bar-Ilan University, Object-Oriented Programming Course
