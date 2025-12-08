package view;

import model.SnakeGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private SnakeGame game;

    public GamePanel(SnakeGame game) {
        this.game = game;
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellWidth = getWidth() / game.getCols();
        int cellHeight = getHeight() / game.getRows();

        // fruta
        g.setColor(Color.RED);
        int[] fruit = game.getFruit();
        g.fillOval(fruit[1] * cellWidth, fruit[0] * cellHeight, cellWidth, cellHeight);

        // cobra
        g.setColor(Color.GREEN);
        game.getSnake().forEach(p ->
                g.fillRect(p[1] * cellWidth, p[0] * cellHeight, cellWidth, cellHeight));

        // placar
        g.setColor(Color.WHITE);
        g.drawString("Score: " + game.getScore(), 10, 20);

        if (game.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", getWidth()/2 - 130, getHeight()/2);
        }
    }
}
