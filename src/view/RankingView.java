package view;

import model.ScoreEntry;
import model.ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RankingView extends JFrame {

    public RankingView(ScoreManager scoreManager) {

        setTitle("Ranking de Pontuação");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<ScoreEntry> ranking = scoreManager.getRanking();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Ranking", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(20));

        if (ranking.isEmpty()) {
            JLabel label = new JLabel("Sem pontuações ainda!", SwingConstants.CENTER);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);
        } else {
            int pos = 1;
            for (ScoreEntry entry : ranking) {
                JLabel label = new JLabel(
                    pos + "º - " + entry.getName() + " : " + entry.getScore() + " pts"
                );
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(label);
                pos++;
            }
        }

        add(panel);
        setVisible(true);
    }
}
