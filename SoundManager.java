import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final Map<String, Clip> soundCache = new HashMap<>();

    public static void loadSound(String name, String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            soundCache.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String name, boolean loop) {
        Clip clip = soundCache.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // Stop if currently playing to restart
            }
            clip.setFramePosition(0); // Rewind
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } else {
            System.err.println("Sound not loaded: " + name);
        }
    }

    public static void stopSound(String name) {
        Clip clip = soundCache.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
