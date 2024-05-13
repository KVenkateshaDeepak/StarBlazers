import java.awt.*;

public class Bullet extends Entity {
    private boolean isEnemy;

    public Bullet(int x, int y, boolean isEnemy, Image image) {
        super(x, y, Constants.BULLET_WIDTH, Constants.BULLET_HEIGHT, image);
        this.isEnemy = isEnemy;
    }

    @Override
    public void update() {
        if (isEnemy) {
            setY(getY() + Constants.BULLET_SPEED);
        } else {
            setY(getY() - Constants.BULLET_SPEED);
        }

        if (getY() < -50 || getY() > Constants.SCREEN_HEIGHT + 50) {
            setMarkedForRemoval(true);
        }
    }

    public boolean isEnemyBullet() {
        return isEnemy;
    }
}
