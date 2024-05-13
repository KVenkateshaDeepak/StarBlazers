import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {
    private int dx = 0;
    private long rapidFireEndTime = 0;

    public Player(int x, int y, Image image) {
        super(x, y, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, image);
    }

    @Override
    public void update() {
        setX(getX() + dx);

        // Boundary checks
        if (getX() < 0)
            setX(0);
        if (getX() > Constants.SCREEN_WIDTH - getWidth())
            setX(Constants.SCREEN_WIDTH - getWidth());
    }

    public void activateRapidFire() {
        rapidFireEndTime = System.currentTimeMillis() + Constants.RAPID_FIRE_DURATION;
    }

    public boolean isRapidFireActive() {
        return System.currentTimeMillis() < rapidFireEndTime;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -Constants.PLAYER_SPEED;
        } else if (key == KeyEvent.VK_RIGHT) {
            dx = Constants.PLAYER_SPEED;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
