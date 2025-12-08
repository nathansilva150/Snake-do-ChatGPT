package view;

import service.JogoService;
import model.Cobra;
import model.Fruta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JogoPainel extends JPanel implements ActionListener, KeyListener {

    private JogoService service;
    Timer timer;

    public JogoPainel(JogoService service) {
        this.service = service;
        this.setPreferredSize(new Dimension(service.getLargura(), service.getAltura()));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenhar(g);
    }

    public void desenhar(Graphics g) {

        Cobra cobra = service.getCobra();
        Fruta fruta = service.getFruta();

        if (service.isRodando()) {

            g.setColor(Color.RED);
            g.fillOval(fruta.x, fruta.y, service.getTamanho(), service.getTamanho());

            for (int i = 0; i < cobra.corpo; i++) {
                if (i == 0)
                    g.setColor(Color.GREEN);
                else
                    g.setColor(new Color(45, 180, 0));

                g.fillRect(cobra.x[i], cobra.y[i], service.getTamanho(), service.getTamanho());
            }

            g.setColor(Color.WHITE);
            g.drawString("Pontos: " + service.getPontos(), 10, 20);

        } else {
            fim(g);
        }
    }

    public void fim(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("GAME OVER", 150, 250);

        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.WHITE);
        g.drawString("Pontuação: " + service.getPontos(), 190, 300);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Pressione ENTER para reiniciar", 170, 350);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (service.isRodando()) {
            service.mover();
            service.pegarFruta();
            service.checarColisoes();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> service.setDirecao('L');
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> service.setDirecao('R');
            case KeyEvent.VK_UP, KeyEvent.VK_W -> service.setDirecao('U');
            case KeyEvent.VK_DOWN, KeyEvent.VK_S -> service.setDirecao('D');
            case KeyEvent.VK_ENTER -> {
                if (!service.isRodando()) service.reiniciar();
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}
