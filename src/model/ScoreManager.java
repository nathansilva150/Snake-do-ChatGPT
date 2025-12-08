package model;

import java.io.*;
import java.util.*;

public class ScoreManager {

    private final String FILE_PATH = "ranking.txt";

    public ScoreManager() {
        // garante que o arquivo existe
        try {
            File f = new File(FILE_PATH);
            if (!f.exists()) f.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ScoreEntry> getRanking() {
        List<ScoreEntry> ranking = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {

                if (!line.contains(";")) continue; // evita linhas quebradas

                String[] parts = line.split(";");
                if (parts.length < 2) continue;

                String name = parts[0].trim();
                int score = Integer.parseInt(parts[1].trim());

                ranking.add(new ScoreEntry(name, score));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ranking.sort((a, b) -> b.getScore() - a.getScore());
        return ranking;
    }

    public void saveScore(String name, int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(name + ";" + score);
            bw.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
