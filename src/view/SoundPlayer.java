package view;

import javax.sound.sampled.*;
import java.io.File;

public class SoundPlayer {

    public void play(String filePath) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir som: " + e.getMessage());
        }
    }
}
