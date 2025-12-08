package model;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGame {
    private final int rows = 25;
    private final int cols = 25;

    private LinkedList<int[]> snake = new LinkedList<>();
    private int[] fruit;
    private Direction direction = Direction.RIGHT;
    private boolean gameOver = false;
    private int score = 0;

    private Random random = new Random();

    public SnakeGame() {
        snake.add(new int[]{12, 12});
        spawnFruit();
    }

    public void changeDirection(Direction newDir) {
        if ((newDir == Direction.UP    && direction != Direction.DOWN) ||
            (newDir == Direction.DOWN  && direction != Direction.UP) ||
            (newDir == Direction.LEFT  && direction != Direction.RIGHT) ||
            (newDir == Direction.RIGHT && direction != Direction.LEFT)) {
            direction = newDir;
        }
    }

    public void update() {
        if (gameOver) return;

        int[] head = snake.getFirst();
        int x = head[0];
        int y = head[1];

        switch (direction) {
            case UP:    x--; break;
            case DOWN:  x++; break;
            case LEFT:  y--; break;
            case RIGHT: y++; break;
        }

        // colisão parede
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            gameOver = true;
            return;
        }

        // colisão com o próprio corpo
        for (int[] p : snake) {
            if (p[0] == x && p[1] == y) {
                gameOver = true;
                return;
            }
        }

        // mover
        snake.addFirst(new int[]{x, y});

        // comer fruta
        if (x == fruit[0] && y == fruit[1]) {
            score += 10;
            spawnFruit();
        } else {
            snake.removeLast();
        }
    }

    private void spawnFruit() {
        fruit = new int[]{random.nextInt(rows), random.nextInt(cols)};
    }

    // GETTERS
    public LinkedList<int[]> getSnake() { return snake; }
    public int[] getFruit() { return fruit; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}
