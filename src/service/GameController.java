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

		frame = new JFrame("Snake MVC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boolean poison = showModeMenu();

		game = new SnakeGame();
		game.setPoisonMode(poison);

		panel = new GamePanel(game);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		setupKeyListener();

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
				String name = JOptionPane.showInputDialog(frame, "Digite seu nome:", "Novo Recorde",
						JOptionPane.PLAIN_MESSAGE);

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

	private boolean showModeMenu() {
		String[] options = { "Modo Padrão", "Modo Envenenado" };

		int choice = JOptionPane.showOptionDialog(frame, "Escolha o modo de jogo:", "Menu Inicial",
				JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		return choice == 1; // true se escolheu "Modo Envenenado"
	}

	private void setupKeyListener() {
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				// REINICIAR / SAIR
				if (game.isGameOver()) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						restartGame();
					}
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
						System.exit(0);
					}
					return;
				}

				// CONTROLES NORMAIS DO JOGO
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
	}

	private void restartGame() {
		timer.stop();
		frame.remove(panel);

		boolean poison = showModeMenu();

		game = new SnakeGame();
		game.setPoisonMode(poison);

		panel = new GamePanel(game);
		frame.add(panel);
		frame.revalidate();
		frame.repaint();

		startGameLoop();
	}

}
