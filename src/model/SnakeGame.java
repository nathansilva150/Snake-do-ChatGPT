package model;

import java.util.LinkedList;
import java.util.Random;

public class SnakeGame {

    private final int rows = 25;
    private final int cols = 25;

    private LinkedList<int[]> snake = new LinkedList<>();
    private Direction direction = Direction.RIGHT;
    private Direction nextDirection = null;

    private boolean directionLocked = false;   // <-- impede 2 inputs no mesmo frame

    private int[] fruit;
    private boolean gameOver = false;
    private int score = 0;

    private Random random = new Random();

    public SnakeGame() {
        snake.add(new int[]{12, 12});
        spawnFruit();
    }

    // ============================
    //      DIREÇÃO DO JOGADOR
    // ============================

    public void changeDirection(Direction newDir) {

        // impede 2 mudanças de direção no mesmo frame
        if (directionLocked) return;

        Direction base = (nextDirection != null) ? nextDirection : direction;

        // evita reversões diretas
        if ((base == Direction.UP    && newDir == Direction.DOWN) ||
            (base == Direction.DOWN  && newDir == Direction.UP) ||
            (base == Direction.LEFT  && newDir == Direction.RIGHT) ||
            (base == Direction.RIGHT && newDir == Direction.LEFT)) {
            return;
        }

        nextDirection = newDir;
        directionLocked = true; // <-- trava até o próximo update()
    }

    // ============================
    //            UPDATE
    // ============================

    public void update() {
        if (gameOver) return;

        // libera o input novamente (1 por frame)
        directionLocked = false;

        // aplica a direção pendente
        if (nextDirection != null) {
            direction = nextDirection;
            nextDirection = null;
        }

        // posição atual da cabeça
        int[] head = snake.getFirst();
        int x = head[0];
        int y = head[1];

        switch (direction) {
            case UP:    x--; break;
            case DOWN:  x++; break;
            case LEFT:  y--; break;
            case RIGHT: y++; break;
        }

        // nova posição da cabeça
        int[] newHead = new int[]{x, y};

        // ============================
        //       COLISÃO COM PAREDE
        // ============================
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            gameOver = true;
            return;
        }

        // ============================
        //     COLISÃO COMPRÓPRIO CORPO
        // ============================
        for (int i = 0; i < snake.size(); i++) {
            int[] part = snake.get(i);
            if (part[0] == newHead[0] && part[1] == newHead[1]) {
                gameOver = true;
                return;
            }
        }

        // ============================
        //           MOVER
        // ============================
        snake.addFirst(newHead);

        // ============================
        //          COMER FRUTA
        // ============================
        if (newHead[0] == fruit[0] && newHead[1] == fruit[1]) {
            score++;
            spawnFruit();
        } else {
            snake.removeLast(); // movimento normal
        }
    }

    // ============================
    //       FRUTA SEGURA
    // ============================

    private void spawnFruit() {
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
                fruit = new int[]{fx, fy};
                return;
            }
        }
    }

    // ============================
    //           GETTERS
    // ============================

    public LinkedList<int[]> getSnake() { return snake; }
    public int[] getFruit() { return fruit; }
    public boolean isGameOver() { return gameOver; }
    public int getScore() { return score; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}