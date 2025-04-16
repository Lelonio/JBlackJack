package org.jblackjack.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioManager {
    private static AudioManager instance;

    private AudioManager() {}

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void play(String soundFileName) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream("/" + soundFileName);
            if (audioSrc == null) {
                System.err.println("Could not find sound file: " + soundFileName);
                return;
            }

            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
