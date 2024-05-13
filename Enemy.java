import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {
    private long lastFireTime;
    private int speed;
    private int fireRate;

    public Enemy(int x, int y, Image image, int speed, int fireRate) {
        super(x, y, Constants.ENEMY_WIDTH, Constants.ENEMY_HEIGHT, image);
        this.speed = speed;
        this.fireRate = fireRate;
        this.lastFireTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        setY(getY() + speed);
        if (getY() > Constants.SCREEN_HEIGHT) {
            setY(-getHeight());
            setX(new Random().nextInt(Constants.SCREEN_WIDTH - getWidth()));
        }
    }

    public boolean canFire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFireTime > fireRate) {
            lastFireTime = currentTime;
            return true;
        }
        return false;
    }
}
