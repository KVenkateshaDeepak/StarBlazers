import java.io.*;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscore.dat";

    public static int loadHighScore() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(HIGH_SCORE_FILE))) {
            return dis.readInt();
        } catch (IOException e) {
            return 0; // Default to 0 if file not found or error
        }
    }

    public static void saveHighScore(int score) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            dos.writeInt(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
