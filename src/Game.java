import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
import biuoop.KeyboardSensor;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class is responsible for running the main game logic.
 * It handles drawing, updating, and initializing all game elements like
 * the paddle, balls, blocks, and walls.
 */
public class Game {
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final GUI gui;
    private biuoop.KeyboardSensor keyboard;
    private final List<Ball> balls = new ArrayList<>();
    private int score = 0;
    private int brickCount = 0;

    /**
     * Constructor - sets up the game environment and GUI window.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", 800, 600); // Create window
        this.keyboard = gui.getKeyboardSensor(); // For user input
    }

    /**
     * Adds a collidable object to the game environment.
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the game (things that can be drawn & updated).
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removes a block from both sprites and collidables.
     * @param block the block to remove
     */
    public void removeBlock(Block block) {
        this.sprites.removeSprite(block);
        this.environment.removeCollidable(block);
        this.brickCount--;
    }

    /**
     * Adds points to the player's score.
     * @param pts points to add
     */
    public void addScore(int pts) {
        this.score += pts;
    }

    /**
     * Removes a ball from the game.
     * @param ball the ball to remove
     */
    public void removeBall(Ball ball) {
        this.sprites.removeSprite(ball);
        this.balls.remove(ball);
    }

    /**
     * Initializes the game - adds all walls, bricks, balls, and paddle.
     */
    public void initialize() {
        initWalls();
        initBricks();
        initBalls();
        initPaddle();
    }

    /**
     * Creates the surrounding walls and adds them to the game.
     */
    private void initWalls() {
        int size = Config.WALLS_SIZE;
        double width = Config.SCREEN_WIDTH;
        int height = Config.SCREEN_HEIGHT;
        Color c = Config.WALLS_COLOR;

        Block left = new Block(new Rectangle(new Point(0, 0), size, height, c));
        Block top = new Block(new Rectangle(new Point(0, 0), width, size, c));
        Block right = new Block(new Rectangle(new Point(width - size, 0), size, height, c));

        left.addToGame(this);
        top.addToGame(this);
        right.addToGame(this);
    }

    /**
     * Adds rows of bricks in a triangle shape to the game.
     */
    private void initBricks() {
        int startX = Config.SCREEN_WIDTH - Config.WALLS_SIZE - Config.BRICKS_WIDTH;
        int startY = Config.WALLS_SIZE + 100; // moved down by 50 units

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 12 - row; col++) {
                int x = startX - col * Config.BRICKS_WIDTH;
                int y = startY + row * Config.BRICKS_HEIGHT;
                Color color = Config.COLORS[row];

                Rectangle r = new Rectangle(new Point(x, y),
                        Config.BRICKS_WIDTH,
                        Config.BRICKS_HEIGHT,
                        color);

                Block brick = new Block(r);
                brick.setStroke(Color.BLACK);
                brick.setDestructible(this, Config.HP_PER_ROW[row], Config.POINTS_PER_ROW[row]);
                brick.addToGame(this);
                brickCount++;
            }
        }
    }

    /**
     * Creates the balls and adds them to the game with velocity.
     */
    private void initBalls() {
        Ball[] balls = {
                new Ball(400, 400, Config.BALL_SIZE, Config.BALL_COLOR),
                new Ball(450, 450, Config.BALL_SIZE, Config.BALL_COLOR),
        };

        for (Ball ball : balls) {
            ball.setGameEnvironment(environment);
            ball.setVelocity(Velocity.fromAngleAndSpeed(180, Config.BALL_SPEED));
            ball.addToGame(this);
            this.balls.add(ball);
        }
    }

    /**
     * Creates the paddle and adds it to the game.
     */
    private void initPaddle() {
        Paddle paddle = new Paddle(gui.getKeyboardSensor(), Config.PADDLE_RECT, 7);
        paddle.getRectangle().setStrokeColor(Color.YELLOW);
        paddle.addToGame(this);
    }

    /**
     * Runs the game loop - updates, draws, and maintains frame rate.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;

        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = gui.getDrawSurface();
            d.setColor(Color.BLUE);
            d.fillRectangle(0, 0, 800, 600);

            this.sprites.notifyAllTimePassed();

            // Remove balls that fell past the bottom of the screen
            List<Ball> fallen = new ArrayList<>();
            for (Ball ball : this.balls) {
                if (ball.getY() > Config.SCREEN_HEIGHT) {
                    fallen.add(ball);
                }
            }
            for (Ball ball : fallen) {
                removeBall(ball);
            }

            this.sprites.drawAllOn(d);

            // Draw score
            d.setColor(Color.WHITE);
            d.drawText(10, 25, "Score: " + score, 20);

            gui.show(d);

            // Check end conditions
            if (balls.isEmpty()) {
                showEndScreen("YOU LOST!", sleeper);
                return;
            }
            if (brickCount <= 0) {
                showEndScreen("YOU WON!", sleeper);
                return;
            }

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Displays a full-screen end message, waits 3 seconds, then closes.
     * @param message the text to show (e.g. "YOU WON!" or "YOU LOST!")
     * @param sleeper the sleeper used for timing
     */
    private void showEndScreen(String message, Sleeper sleeper) {
        DrawSurface d = gui.getDrawSurface();
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        d.setColor(Color.WHITE);
        d.drawText(Config.SCREEN_WIDTH / 2 - 160, Config.SCREEN_HEIGHT / 2 - 30, message, 60);
        d.setColor(Color.YELLOW);
        d.drawText(Config.SCREEN_WIDTH / 2 - 90, Config.SCREEN_HEIGHT / 2 + 40, "Score: " + score, 36);
        gui.show(d);
        sleeper.sleepFor(3000);
        gui.close();
    }
}
