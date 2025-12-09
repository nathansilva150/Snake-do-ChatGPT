package service;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController {

	private SnakeGame game;
	private GamePanel panel;
	private JFrame frame;

	private SoundPlayer sound = new SoundPlayer();
	private ScoreManager scoreManager = new ScoreManager();
	private SoundService soundService = new SoundService();

	public GameController() {
		game = new SnakeGame();
		panel = new GamePanel(game);

		frame = new JFrame("Snake MVC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					game.changeDirection(Direction.UP);
					break;
				case KeyEvent.VK_DOWN:
					game.changeDirection(Direction.DOWN);
					break;
				case KeyEvent.VK_LEFT:
					game.changeDirection(Direction.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					game.changeDirection(Direction.RIGHT);
					break;
				case KeyEvent.VK_R:
					new RankingView(scoreManager);
					break;
				}
			}
		});

		startGameLoop();
	}

	private Timer timer;
	private boolean gameOverExecuted = false; // impede repetir ações do game over

	private void startGameLoop() {

	    timer = new Timer(100, e -> {

	        int oldScore = game.getScore();

	        game.update();

	        // Som de comer
	        if (game.getScore() > oldScore) {
	        	soundService.playEatSound();
	        }

	        // Game over
	        if (game.isGameOver() && !gameOverExecuted) {
	            gameOverExecuted = true; // impede repetir
	            timer.stop(); // PARA o loop do jogo

	            soundService.playGameOverSound();
	            String name = JOptionPane.showInputDialog(frame, "Digite seu nome:", "Novo Recorde", JOptionPane.PLAIN_MESSAGE);

	         // se cancelar ou deixar vazio → usa "Jogador"
	         if (name == null || name.trim().isEmpty())
	             name = "Jogador";

	         // salvar nome + pontos
	         scoreManager.saveScore(name, game.getScore());

	         // mostrar ranking
	         new RankingView(scoreManager);
	        }

	        panel.repaint();
	    });

	    timer.start();
	}

}
