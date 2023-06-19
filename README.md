# snake-game

A simple snake game, made as one of the projects, assigned in the first-year of IT studies.
## 1.  Game Rules

Snake is a simple but addictive game where the goal is to control a snake, collect as much food as possible, and avoid colliding itself or the walls of the board. Here is a detailed-description of the rules of the snake game:
1. Board: The game is played on a rectangular grid-based board. The board can have different sizes, but it is most commonly square-shaped. The snake can move on the grid cells of the board.
2. Snake: The player controls a snake composed of consecutive segments. The snake starts out short with only one segment but grows longer as it collects food, ad-ding more segments. The head of the snake is represented by a segment that looks different or has a different color.
3. Food: Pieces of food are scattered on the board, often represented as filled circles. The game objective is to eat as much food as possible.
4. Movement: The snake moves on the board by moving its head in one of the four basic directions: up, down, left or right. The snake cannot move diagonally.
5. Snake movement rules:
   * When the player chooses a direction, the snake moves one cell in that direction.
   * Each segment of the snake follows the head, causing the entire snake to move as a single continuous body.
   * The snake cannot move backward, i.e., it cannot turn around and move in the opposite direction.
   * The player can change the snake’s direction of movement at any time, but cannot stop it from moving.
6. Collisions:
   * If the snake’s head collides with one of its own segments or hits the board’sedge, the game ends.
   * When the snake eats a piece of food, its length increases by adding a new segment to the end of the snake.
7. Scoring: The player earns points for collecting food. Usually, the number of points is equal to the number of food pieces eaten. Other scoring mechanisms can also be used, such as increasing gains with the snake’s length.
8. Game Objectives: The main objective of the snake game is to achieve the highest possible score by collecting food and avoiding collisions with itself and the board’s walls.

## 2. Project Description

Implement your own version of the Snake game, following these assumptions:
* The game board will be stored in a two-dimensional array of integers with a size of25×16.
* The actual gameplay will happen in a separate thread independent of the user interface thread.
* The snake’s movement direction will be controlled using the arrow keys on the keyboard.
* All game elements, such as the game tick, food consumption, collision with walls or own segment, will generate an event (NameOfEvent) that will be received by appropriate listeners.
* Use a JTable component as the main visualization panel.
* Use a JPanel that utilizes the paintComponent(...) method to draw the score.

Additionally, after the game ends, display a list of the top 10 players along with their scores on the screen. This information should be saved and stored in a binary file according to the following scheme:
* A field LEN of size 1 byte, containing the number of characters describing the length of player’s name.
* A sequence of 2∗LEN bytes, containing the characters that make up the player’s name.
* 4 bytes describing the number of points obtained.
  
The read and write operations should be implemented based on the FileInputStream or FileOutputStream stream without encapsulating it in any other class.