import java.awt.Color;

/**
 * Configuration constants for the Arkanoid game.
 * Adjust imports or package declarations based on where your Point and Rectangle classes are located.
 */
public class Config {
    // Window size
    public static final int SCREEN_WIDTH  = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Walls configuration
    public static final int WALLS_SIZE    = 10;
    public static final Color WALLS_COLOR = Color.GRAY;

    // Bricks configuration
    public static final int BRICKS_WIDTH  = 50;
    public static final int BRICKS_HEIGHT = 20;
    public static final Color[] COLORS    = {
            Color.GRAY,
            Color.RED,
            Color.YELLOW,
            Color.BLUE,
            Color.PINK,
            Color.GREEN,
            Color.BLUE
    };
    // HP per brick row (index = row number, top to bottom)
    public static final int[] HP_PER_ROW     = {1, 2, 1, 3, 2, 1};
    // Points awarded when a brick is destroyed
    public static final int[] POINTS_PER_ROW = {100, 150, 100, 200, 150, 100};

    // Ball configuration
    public static final int   BALL_SIZE   = 5;
    public static final Color BALL_COLOR  = Color.WHITE;
    public static final double BALL_SPEED = 3.0;

    // Paddle configuration
    public static final Color PADDLE_COLOR = Color.YELLOW;
    public static final int   PADDLE_SPEED = 10;
    // Ensure Point and Rectangle refer to your own classes (no import needed if in default package)
    public static final Rectangle PADDLE_RECT =
            new Rectangle(
                    new Point(350, 575),  // upper-left corner
                    75,                  // width
                    10,                   // height
                    PADDLE_COLOR          // color
            );
}
