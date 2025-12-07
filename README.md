# Star Blazers - Java Arcade Game

**Star Blazers** is a object-oriented 2D game built from scratch using **Java** and **Swing**. This project demonstrates core software engineering principles including Game Loop architecture, Entity-Component design patterns, and efficient resource management.

![Game Banner](Trailer.gif)

## Technical Architecture

The project is architected with a focus on **Modularity**, **Encapsulation**, and **Performance**.

### 1. Object-Oriented Design (OOD)
*   **Inheritance & Polymorphism**: A robust `Entity` base class manages common properties (position, bounds, rendering), extended by `Player`, `Enemy`, `Bullet`, and `Particle`. This allows for polymorphic processing in the game loop.
*   **Encapsulation**: Strict access controls protect internal state, using getters/setters and separate Managers for subsystems (`SoundManager`, `ResourceManager`).
*   **Interfaces**: Implements `KeyListener` and `ActionListener` for decoupled input handling and game loop timing.

### 2. Design Patterns
*   **Game Loop Pattern**: Implements a fixed time-step game loop (60 FPS) driving update and render cycles independently.
*   **State Pattern**: Uses a finite state machine (via `State` enum) to manage game flow (`MENU`, `PLAYING`, `PAUSED`, `GAME_OVER`) deterministically.
*   **Flyweight / Resource Management**: The `ResourceManager` and `SoundManager` classes implement caching strategies to load heavy assets (images, audio) only once, preventing memory bloat and improving runtime performance.

### 3. Algorithmic Optimizations
*   **Allocation-Free Collision Detection**: The collision system is optimized to minimize garbage collection pressure by avoiding the creation of temporary objects within the hot loop.
*   **Efficient Collection Handling**: Uses `Iterator` safe removal to manage dynamic entity lifecycles (spawning/despawning) without `ConcurrentModificationException`.
*   **Swing Rendering Pipeline**: Leverages `JComponent`'s double-buffering via `paintComponent` override to ensure tear-free, smooth rendering.

## Tech Stack

*   **Core**: Java (JDK 8+)
*   **GUI Framework**: Swing / AWT (Custom Graphics2D rendering)
*   **Audio API**: Java Sound API (`javax.sound.sampled`)
*   **IO**: Java Standard I/O (File persistence for High Scores)

---

## Getting Started

### Prerequisites
*   **Java Development Kit (JDK)**: Version 8 or higher is required.
    *   Verify installation: `java -version`

### Installation
1.  **Clone the Repository**

    ```bash
    git clone https://github.com/KVenkateshaDeepak/StarBlazers.git
    cd StarBlazers
    ```
2.  **Verify Assets**
    Ensure availability of resource files (`*.jpg`, `*.wav`, `*.gif`) in the root directory.

### How to Run

**1. Compilation**
Compile all source files from the project root:
```bash
javac StarBlazersGame.java GamePanel.java Entity.java Player.java Enemy.java Bullet.java SoundManager.java Constants.java ResourceManager.java HighScoreManager.java Particle.java PowerUp.java
```

**2. Execution**
Launch the game engine:
```bash
java StarBlazersGame
```

## Controls

| Key | Action |
| :--- | :--- |
| **Arrow Keys** | Navigation |
| **Space** | Primary Fire |
| **S** | Start Game |
| **ESC** | Pause / Resume |
| **Q** | Quit Application |

oops project
---

