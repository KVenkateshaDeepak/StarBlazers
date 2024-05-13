import java.awt.*;

public class PowerUp extends Entity {
    public enum Type {
        HEAL, RAPID_FIRE
    }

    private Type type;

    public PowerUp(int x, int y, Type type) {
        super(x, y, Constants.POWERUP_SIZE, Constants.POWERUP_SIZE, null);
        this.type = type;
    }

    @Override
    public void update() {
        setY(getY() + Constants.POWERUP_SPEED);
        if (getY() > Constants.SCREEN_HEIGHT) {
            setMarkedForRemoval(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        if (type == Type.HEAL) {
            g.setColor(Color.GREEN);
            g.drawString("♥", getX(), getY() + 15);
        } else {
            g.setColor(Color.CYAN);
            g.drawString("⚡", getX(), getY() + 15);
        }
        g.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    public Type getType() {
        return type;
    }
}
