public class Constants {
    // Screen Dimensions
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Game Loop
    public static final int FPS_DELAY = 16; // ~60 FPS
    public static final int SCROLL_SPEED = 2; // Background scroll speed

    // Player Settings
    public static final int PLAYER_WIDTH = 60;
    public static final int PLAYER_HEIGHT = 40;
    public static final int PLAYER_SPEED = 5;
    public static final int PLAYER_START_LIVES = 3;
    public static final int MAX_LIVES = 5;
    public static final int RAPID_FIRE_DURATION = 5000; // ms

    // Enemy Settings
    public static final int ENEMY_WIDTH = 30;
    public static final int ENEMY_HEIGHT = 30;
    public static final int ENEMY_BASE_SPEED = 2;
    public static final int ENEMY_BASE_FIRE_RATE = 2000; // ms
    public static final int INITIAL_ENEMIES_COUNT = 5;

    // Difficulty
    public static final int LEVEL_SPEED_INCREASE_INTERVAL = 2; // Increase speed every 2 levels
    public static final int LEVEL_FIRE_RATE_DECREASE_INTERVAL = 3; // Shoot faster every 3 levels

    // Bullet Settings
    public static final int BULLET_WIDTH = 5;
    public static final int BULLET_HEIGHT = 10;
    public static final int BULLET_SPEED = 10;

    // Particles
    public static final int PARTICLE_COUNT = 15;
    public static final int PARTICLE_SIZE = 4;
    public static final int PARTICLE_SPEED = 3;
    public static final int PARTICLE_LIFESPAN = 40; // Frames

    // PowerUps
    public static final int POWERUP_SIZE = 20;
    public static final int POWERUP_SPEED = 2;
    public static final int POWERUP_DROP_CHANCE = 20; // 1 in 20 chance (5%)

    // Resource Paths
    public static final String IMG_BACKGROUND = "2bg.gif";
    public static final String IMG_PLAYER = "spaceship_image.jpg";
    public static final String IMG_ENEMY = "enemy_image.jpg";

    public static final String SND_BG_MUSIC = "1.wav";
    public static final String SND_PLAYER_SHOOT = "player_bullet_sound.wav";
    public static final String SND_ENEMY_SHOOT = "enemy_bullet_sound.wav";
    public static final String SND_LIFE_LOST = "life_lost_sound.wav";
    public static final String SND_ENEMY_DEATH = "enemy_life_lost.wav";
    public static final String SND_POWERUP = "enemy_life_lost.wav"; // Reuse or need new sound
}
