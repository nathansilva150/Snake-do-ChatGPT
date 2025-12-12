package model;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGame {

    private final int rows = 25;
    private final int cols = 25;

    private LinkedList<int[]> snake = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = null;
    private boolean poisonMode = false;
    
    private boolean directionLocked = false;

    private int[] fruit;            // fruta normal
    private int[] poisonFruit;      // fruta envenenada

    private boolean gameOver = false;
    private int score = 0;

    private Random random = new Random();

    public SnakeGame() {
        snake.add(new int[]{12, 12});
        spawnFruit();

        if (poisonMode) {
            spawnPoisonFruit();
        }
    }
    
    public void setPoisonMode(boolean enabled) {
        this.poisonMode = enabled;
    }

    public boolean isPoisonMode() {
        return poisonMode;
    }

    // ============================
    // DIREÇÃO
    // ============================

    public void changeDirection(Direction newDir) {
        if (directionLocked) return;

        Direction base = (nextDirection != null) ? nextDirection : direction;

        if ((base == Direction.UP && newDir == Direction.DOWN) ||
            (base == Direction.DOWN && newDir == Direction.UP) ||
            (base == Direction.LEFT && newDir == Direction.RIGHT) ||
            (base == Direction.RIGHT && newDir == Direction.LEFT)) {
            return;
        }

        nextDirection = newDir;
        directionLocked = true;
    }

    // ============================
    // UPDATE
    // ============================

    public void update() {
        if (gameOver) return;

        directionLocked = false;

        if (nextDirection != null) {
            direction = nextDirection;
            nextDirection = null;
        }

        int[] head = snake.getFirst();
        int x = head[0];
        int y = head[1];

        switch (direction) {
            case UP:    x--; break;
            case DOWN:  x++; break;
            case LEFT:  y--; break;
            case RIGHT: y++; break;
        }

        int[] newHead = new int[]{x, y};

        // colisão parede
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            gameOver = true;
            return;
        }

        // colisão corpo
        for (int[] part : snake) {
            if (part[0] == newHead[0] && part[1] == newHead[1]) {
                gameOver = true;
                return;
            }
        }

        snake.addFirst(newHead);

     // COME FRUTA NORMAL
        if (newHead[0] == fruit[0] && newHead[1] == fruit[1]) {
            score++;
            spawnFruit();

            if (poisonMode) {
                spawnPoisonFruit();
            }
            return;
        }

        // ========================
        // COME FRUTA ENVENENADA
        // ========================
     // Come fruta venenosa (somente se modo estiver ativo)
        if (poisonMode && poisonFruit != null &&
            newHead[0] == poisonFruit[0] &&
            newHead[1] == poisonFruit[1]) {

            gameOver = true;
            return;
        }

        // movimento normal
        snake.removeLast();
    }

    // ============================
    // FRUTAS
    // ============================

    private void spawnFruit() {
        fruit = generateFreePosition();
    }

    private void spawnPoisonFruit() {
        poisonFruit = generateFreePosition();
    }

    private int[] generateFreePosition() {
        while (true) {
            int fx = random.nextInt(rows);
            int fy = random.nextInt(cols);

            boolean insideSnake = false;
            for (int[] part : snake) {
                if (part[0] == fx && part[1] == fy) {
                    insideSnake = true;
                    break;
                }
            }

            if (!insideSnake) {
                // não deixa cair em cima da fruta normal (apenas por segurança)
                if (fruit != null && fruit[0] == fx && fruit[1] == fy)
                    continue;

                return new int[]{fx, fy};
            }
        }
    }

    // ============================
    // GETTERS
    // ============================

    public LinkedList<int[]> getSnake() { return snake; }
    public int[] getFruit() { return fruit; }
    public int[] getPoisonFruit() { return poisonFruit; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}