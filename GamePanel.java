import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private List<Particle> particles;
    private List<PowerUp> powerUps;

    private int score = 0;
    private int highScore = 0;
    private int level = 1;
    private int lives = Constants.PLAYER_START_LIVES;
    private int enemiesToDestroy = Constants.INITIAL_ENEMIES_COUNT;

    // Animation
    private float pulseAlpha = 1.0f;
    private boolean pulseGrowing = false;

    // Game States
    private enum State {
        MENU, PLAYING, PAUSED, GAME_OVER
    }

    private State gameState = State.MENU;

    public GamePanel() {
        setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        highScore = HighScoreManager.loadHighScore();
        loadResources();
        initGame();

        timer = new Timer(Constants.FPS_DELAY, this);
        timer.start();
    }

    private void loadResources() {
        SoundManager.loadSound("bg_music", Constants.SND_BG_MUSIC);
        SoundManager.loadSound("player_shoot", Constants.SND_PLAYER_SHOOT);
        SoundManager.loadSound("enemy_shoot", Constants.SND_ENEMY_SHOOT);
        SoundManager.loadSound("life_lost", Constants.SND_LIFE_LOST);
        SoundManager.loadSound("enemy_death", Constants.SND_ENEMY_DEATH);
        SoundManager.loadSound("powerup", Constants.SND_POWERUP);

        SoundManager.playSound("bg_music", true);
    }

    private void initGame() {
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        particles = new ArrayList<>();
        powerUps = new ArrayList<>();

        Image playerImg = ResourceManager.loadImage(Constants.IMG_PLAYER);
        player = new Player(
                Constants.SCREEN_WIDTH / 2 - Constants.PLAYER_WIDTH / 2,
                Constants.SCREEN_HEIGHT - 80,
                playerImg);

        spawnEnemies();
    }

    private void spawnEnemies() {
        enemies.clear();
        Random rand = new Random();
        Image enemyImg = ResourceManager.loadImage(Constants.IMG_ENEMY);

        // Calculate Difficulty
        int currentSpeed = Constants.ENEMY_BASE_SPEED + (level / Constants.LEVEL_SPEED_INCREASE_INTERVAL);
        int currentFireRate = Math.max(500,
                Constants.ENEMY_BASE_FIRE_RATE - (level / Constants.LEVEL_FIRE_RATE_DECREASE_INTERVAL) * 200);

        for (int i = 0; i < enemiesToDestroy; i++) {
            int x = rand.nextInt(Constants.SCREEN_WIDTH - Constants.ENEMY_WIDTH);
            int y = rand.nextInt(200);
            enemies.add(new Enemy(x, y, enemyImg, currentSpeed, currentFireRate));
        }
    }

    private void resetGame() {
        if (score > highScore) {
            highScore = score;
            HighScoreManager.saveHighScore(highScore);
        }

        score = 0;
        level = 1;
        lives = Constants.PLAYER_START_LIVES;
        enemiesToDestroy = Constants.INITIAL_ENEMIES_COUNT;

        initGame();
        gameState = State.PLAYING;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Background
        drawBackground(g);

        switch (gameState) {
            case MENU:
                drawMenu(g);
                break;
            case PLAYING:
                drawGame(g);
                break;
            case PAUSED:
                drawGame(g);
                drawPauseMenu(g);
                break;
            case GAME_OVER:
                drawGame(g);
                drawGameOver(g);
                break;
        }
    }

    private void drawBackground(Graphics g) {
        Image bg = ResourceManager.loadImage(Constants.IMG_BACKGROUND);
        if (bg != null) {
            // Draw static background
            g.drawImage(bg, 0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, this);
        }
    }

    // Helper to draw text with shadow
    private void drawStyledString(Graphics g, String text, int x, int y, int size, Color color) {
        g.setFont(new Font("Verdana", Font.BOLD, size));
        g.setColor(Color.BLACK);
        g.drawString(text, x + 3, y + 3); // Shadow
        g.setColor(color);
        g.drawString(text, x, y);
    }

    private void drawCenteredStyledString(Graphics g, String text, int y, int size, Color color) {
        g.setFont(new Font("Verdana", Font.BOLD, size));
        FontMetrics metrics = g.getFontMetrics();
        int x = (Constants.SCREEN_WIDTH - metrics.stringWidth(text)) / 2;

        g.setColor(Color.BLACK);
        g.drawString(text, x + 3, y + 3); // Shadow
        g.setColor(color);
        g.drawString(text, x, y);
    }

    private void drawMenu(Graphics g) {
        drawCenteredStyledString(g, "STAR BLAZERS", Constants.SCREEN_HEIGHT / 3, 60, Color.YELLOW);

        // Pulsing Start Text
        Color pulseColor = new Color(1f, 1f, 1f, pulseAlpha);
        g.setFont(new Font("Verdana", Font.BOLD, 30));
        FontMetrics metrics = g.getFontMetrics();
        String startText = "Press S to Start";
        int x = (Constants.SCREEN_WIDTH - metrics.stringWidth(startText)) / 2;
        int y = Constants.SCREEN_HEIGHT / 2 + 50;

        g.setColor(Color.BLACK);
        g.drawString(startText, x + 2, y + 2);
        g.setColor(pulseColor);
        g.drawString(startText, x, y);

        drawCenteredStyledString(g, "High Score: " + highScore, Constants.SCREEN_HEIGHT / 2 + 100, 20, Color.CYAN);
        drawCenteredStyledString(g, "Press Q to Quit", Constants.SCREEN_HEIGHT / 2 + 140, 20, Color.GRAY);
    }

    private void drawGame(Graphics g) {
        if (player != null)
            player.draw(g);

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        for (Particle p : particles) {
            p.draw(g);
        }

        for (PowerUp p : powerUps) {
            p.draw(g);
        }

        // HUD Background Bar
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, 40);
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, Constants.SCREEN_WIDTH - 1, 40);

        // HUD Text
        g.setFont(new Font("Verdana", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 20, 26);
        g.drawString("Level: " + level, 200, 26);
        g.drawString("High Score: " + Math.max(score, highScore), 400, 26);

        if (player.isRapidFireActive()) {
            g.setColor(Color.CYAN);
            g.drawString("RAPID FIRE!", Constants.SCREEN_WIDTH / 2 - 60, 80);
        }

        // Draw Lives (Hearts)
        int heartX = Constants.SCREEN_WIDTH - 150;
        g.setColor(Color.RED);
        for (int i = 0; i < lives; i++) {
            g.setFont(new Font("SansSerif", Font.PLAIN, 20)); // Use accessible font for symbol
            g.drawString("♥", heartX + (i * 25), 26);
        }
    }

    private void drawPauseMenu(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        drawCenteredStyledString(g, "PAUSED", Constants.SCREEN_HEIGHT / 2 - 50, 50, Color.WHITE);
        drawCenteredStyledString(g, "Press ESC to Resume", Constants.SCREEN_HEIGHT / 2 + 20, 20, Color.LIGHT_GRAY);
        drawCenteredStyledString(g, "Press M for Main Menu", Constants.SCREEN_HEIGHT / 2 + 50, 20, Color.LIGHT_GRAY);
        drawCenteredStyledString(g, "Press Q to Quit", Constants.SCREEN_HEIGHT / 2 + 80, 20, Color.LIGHT_GRAY);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        drawCenteredStyledString(g, "GAME OVER", Constants.SCREEN_HEIGHT / 2 - 50, 60, Color.RED);
        drawCenteredStyledString(g, "Final Score: " + score, Constants.SCREEN_HEIGHT / 2 + 20, 30, Color.WHITE);

        if (score > highScore && score > 0) {
            drawCenteredStyledString(g, "NEW HIGH SCORE!", Constants.SCREEN_HEIGHT / 2 + 60, 25, Color.YELLOW);
        }

        drawCenteredStyledString(g, "Press R to Restart", Constants.SCREEN_HEIGHT / 2 + 110, 20, Color.LIGHT_GRAY);
        drawCenteredStyledString(g, "Press M for Main Menu", Constants.SCREEN_HEIGHT / 2 + 140, 20, Color.LIGHT_GRAY);
        drawCenteredStyledString(g, "Press Q to Quit", Constants.SCREEN_HEIGHT / 2 + 170, 20, Color.LIGHT_GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState == State.PLAYING) {
            updateGame();
        }

        // Pulse logic
        if (pulseGrowing) {
            pulseAlpha += 0.05f;
            if (pulseAlpha >= 1f) {
                pulseAlpha = 1f;
                pulseGrowing = false;
            }
        } else {
            pulseAlpha -= 0.05f;
            if (pulseAlpha <= 0.3f) {
                pulseAlpha = 0.3f;
                pulseGrowing = true;
            }
        }

        repaint();
    }

    private void updateGame() {
        player.update();

        // Update Enemies
        for (Enemy enemy : enemies) {
            enemy.update();
            if (enemy.canFire()) {
                bullets.add(new Bullet(enemy.getX() + 15, enemy.getY() + 30, true, null));
                SoundManager.playSound("enemy_shoot", false);
            }
        }

        // Update Bullets
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update();
            if (b.isMarkedForRemoval()) {
                it.remove();
            }
        }

        // Update Particles
        Iterator<Particle> pit = particles.iterator();
        while (pit.hasNext()) {
            Particle p = pit.next();
            p.update();
            if (p.isMarkedForRemoval())
                pit.remove();
        }

        // Update PowerUps
        Iterator<PowerUp> puit = powerUps.iterator();
        while (puit.hasNext()) {
            PowerUp p = puit.next();
            p.update();
            if (p.isMarkedForRemoval())
                puit.remove();
        }

        checkCollisions();
        checkLevel();
    }

    private void spawnParticles(int x, int y, Color color) {
        for (int i = 0; i < Constants.PARTICLE_COUNT; i++) {
            particles.add(new Particle(x, y, color));
        }
    }

    private void spawnPowerUp(int x, int y) {
        Random rand = new Random();
        if (rand.nextInt(Constants.POWERUP_DROP_CHANCE) == 0) {
            PowerUp.Type type = rand.nextBoolean() ? PowerUp.Type.HEAL : PowerUp.Type.RAPID_FIRE;
            powerUps.add(new PowerUp(x, y, type));
        }
    }

    private void checkCollisions() {
        // Optimized Collision Detection

        // 1. Check Player Bullets -> Enemies
        for (Bullet b : bullets) {
            if (b.isEnemyBullet() || b.isMarkedForRemoval())
                continue;

            for (Enemy e : enemies) {
                if (e.isMarkedForRemoval())
                    continue;

                if (b.intersects(e)) {
                    b.setMarkedForRemoval(true);
                    e.setMarkedForRemoval(true);
                    score += 100;
                    SoundManager.playSound("enemy_death", false);
                    spawnParticles(e.getX() + e.getWidth() / 2, e.getY() + e.getHeight() / 2, Color.ORANGE);
                    spawnPowerUp(e.getX(), e.getY());
                }
            }
        }

        // 2. Check Enemy Hit -> Player
        // 3. Check Enemy Bullet -> Player
        Rectangle playerBounds = player.getBounds();

        for (Enemy e : enemies) {
            if (!e.isMarkedForRemoval() && e.getBounds().intersects(playerBounds)) {
                e.setMarkedForRemoval(true);
                playerHit();
            }
        }

        for (Bullet b : bullets) {
            if (b.isEnemyBullet() && !b.isMarkedForRemoval() && b.getBounds().intersects(playerBounds)) {
                b.setMarkedForRemoval(true);
                playerHit();
            }
        }

        // 4. PowerUps -> Player
        for (PowerUp p : powerUps) {
            if (!p.isMarkedForRemoval() && p.getBounds().intersects(playerBounds)) {
                p.setMarkedForRemoval(true);
                collectPowerUp(p);
            }
        }

        // Cleanup
        bullets.removeIf(Entity::isMarkedForRemoval);
        enemies.removeIf(Entity::isMarkedForRemoval);
    }

    private void collectPowerUp(PowerUp p) {
        SoundManager.playSound("powerup", false);
        if (p.getType() == PowerUp.Type.HEAL) {
            if (lives < Constants.MAX_LIVES)
                lives++;
        } else if (p.getType() == PowerUp.Type.RAPID_FIRE) {
            player.activateRapidFire();
        }
    }

    private void playerHit() {
        lives--;
        SoundManager.playSound("life_lost", false);
        spawnParticles(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, Color.RED);

        if (lives <= 0) {
            if (score > highScore) {
                highScore = score;
                HighScoreManager.saveHighScore(highScore);
            }
            gameState = State.GAME_OVER;
        }
    }

    private void checkLevel() {
        if (enemies.isEmpty()) {
            level++;
            enemiesToDestroy += 2;
            spawnEnemies();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (gameState == State.MENU) {
            if (key == KeyEvent.VK_S) {
                resetGame(); // Start fresh
            } else if (key == KeyEvent.VK_Q) {
                System.exit(0);
            }
        } else if (gameState == State.PLAYING) {
            if (key == KeyEvent.VK_ESCAPE) {
                gameState = State.PAUSED;
            } else if (key == KeyEvent.VK_SPACE) {
                bullets.add(new Bullet(player.getX() + 25, player.getY(), false, null));
                SoundManager.playSound("player_shoot", false);

                // Rapid Fire: Add side bullets
                if (player.isRapidFireActive()) {
                    bullets.add(new Bullet(player.getX(), player.getY() + 10, false, null));
                    bullets.add(new Bullet(player.getX() + 50, player.getY() + 10, false, null));
                }
            } else {
                player.keyPressed(e);
            }
        } else if (gameState == State.PAUSED) {
            if (key == KeyEvent.VK_ESCAPE) {
                gameState = State.PLAYING;
            } else if (key == KeyEvent.VK_M) {
                gameState = State.MENU;
            } else if (key == KeyEvent.VK_Q) {
                System.exit(0);
            }
        } else if (gameState == State.GAME_OVER) {
            if (key == KeyEvent.VK_R) {
                resetGame();
            } else if (key == KeyEvent.VK_M) {
                gameState = State.MENU;
            } else if (key == KeyEvent.VK_Q) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameState == State.PLAYING) {
            player.keyReleased(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
