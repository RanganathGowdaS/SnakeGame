# Snake Game in Java

This is a simple Snake game built using Java Swing. The game dynamically adjusts the board size based on the screen dimensions and allows the snake to wrap around the screen when it moves beyond the boundaries.

## Features
- **Dynamic Board Size:** The game board adjusts to the window size.
- **Food Spawning Logic:** Food spawns only in locations where the snake's body isn't present.
- **Wrap-Around Movement:** The snake appears on the opposite side when it crosses the screen boundary.
- **Game Over & Restart:** The game ends when the snake collides with itself, prompting the user to restart.

## How to Play
- Use the arrow keys (`Up`, `Down`, `Left`, `Right`) to control the snake's movement.
- Eat the red food to grow longer.
- Avoid running into yourself!
- If the game is over, you will be prompted to restart.

## Requirements
- Java 8 or higher
- A system with Java installed

## How to Run
1. Clone the repository:
   ```sh
   git clone <https://github.com/RanganathGowdaS/SnakeGame.git>
   cd <SnakeGame>
   ```
2. Compile and run the game:
   ```sh
   javac SnakeGame.java
   java SnakeGame
   ```

Enjoy the game! 🎮🐍

