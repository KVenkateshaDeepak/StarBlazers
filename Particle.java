import java.awt.*;
import java.util.Random;

public class Particle extends Entity {
    private double dx, dy;
    private int life;
    private Color color;

    public Particle(int x, int y, Color color) {
        super(x, y, Constants.PARTICLE_SIZE, Constants.PARTICLE_SIZE, null);
        this.color = color;
        this.life = Constants.PARTICLE_LIFESPAN;

        Random rand = new Random();
        double angle = rand.nextDouble() * 2 * Math.PI;
        double speed = rand.nextDouble() * Constants.PARTICLE_SPEED;
        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed;
    }

    @Override
    public void update() {
        setX((int) (getX() + dx));
        setY((int) (getY() + dy));
        life--;
        if (life <= 0) {
            setMarkedForRemoval(true);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        float alpha = Math.max(0, (float) life / Constants.PARTICLE_LIFESPAN);
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255)));
        g2d.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
