package service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class SoundService {

	private final List<String> eatSounds = List.of(
			"resources/sounds/eats/eatBone.wav",
			"resources/sounds/eats/eatMunch.wav",
			"resources/sounds/eats/eatMinecraft.wav",
			"resources/sounds/eats/eatRoblox.wav",
			"resources/sounds/eats/eatYEHAH.wav",
			"resources/sounds/eats/eatMiBombo.wav",
			"resources/sounds/eats/eatMickey.wav",
			"resources/sounds/eats/eatVineBoom.wav",
			"resources/sounds/eats/eatRapaz.wav",
			"resources/sounds/eats/eatHeavyTf2.wav");
	
	private final List<String> gameOverSounds = List.of(
			"resources/sounds/trilhaSonora/telefonezinho.wav",
			"resources/sounds/trilhaSonora/welcomeToTheMato.wav",
			"resources/sounds/trilhaSonora/apanharCalado.wav",
			"resources/sounds/trilhaSonora/peidaoLongo.wav",
			"resources/sounds/trilhaSonora/naoChoraxx.wav",
			"resources/sounds/trilhaSonora/flamengoFiuFiu.wav",
			"resources/sounds/trilhaSonora/xenogenesis.wav",
			"resources/sounds/trilhaSonora/zoio-duvido.wav",
			"resources/sounds/trilhaSonora/chegouMensagem.wav"
		);

	private final Random random = new Random();

	public void playEatSound() {
		String path = eatSounds.get(random.nextInt(eatSounds.size()));
		playSound(path);
	}
	
	public void playGameOverSound() {
	    String file = gameOverSounds.get(random.nextInt(gameOverSounds.size()));
	    playSound(file);
	}

	private void playSound(String filePath) {
		try {
			File soundFile = new File(filePath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
