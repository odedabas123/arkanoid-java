
import biuoop.DrawSurface;
import java.awt.Color;
/**
 *class Ball.
 */
public class   Ball implements Sprite  {
    // constructor
    private Point center;
    private final double r;
    private final java.awt.Color color;
    private Velocity v;
    private GameEnvironment  environment;

    /**
     * constructor.
     *
     * @param center point
     * @param r      radios.
     * @param color  color
     */
    public Ball(Point center, double r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    public Ball(Point center, double r, java.awt.Color color, Velocity v, GameEnvironment environment) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.v = v;
        this.environment = environment;
    }

    /**
     * constructor with int and not point.
     *
     * @param x     x for center
     * @param y     y for center
     * @param r     radios
     * @param color color
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = (new Point(x, y));
        this.r = r;
        this.color = color;

    }

    /**
     * constructor with speed.
     *
     * @param x  for point
     * @param y  for point
     * @param r  radios
     * @param vx speed
     * @param vy speed
     */
    public Ball(int x, int y, int r, int vx, int vy) {
        this.center = new Point(x, y);
        this.r = r;
        this.v = Velocity.fromAngleAndSpeed(vx, vy);
        this.color = Color.BLACK;
    }

    /**
     * Adds the current ball (this) to the specified game as a sprite.
     * The ball will be included in the game's sprite collection for drawing and updates.
     *
     * @param g the game to which the ball will be added
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * get X.
     *
     * @return x of center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * get y.
     *
     * @return y of center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * get radios.
     *
     * @return radios of ball.
     */
    public double getSize() {
        return this.r;
    }

    /**
     * Retrieves the center of the ball.
     *
     * @return the center point of the ball.
     */
    public Point getCenter() {
        return this.center;
    }


    /**
     * get color.
     *
     * @return color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;

    }

    /**
     * Draws the ball on the provided drawing surface.
     * The ball is drawn as a filled circle in the specified color and position.
     *
     * @param d the drawing surface on which the ball is drawn
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle((int) this.center.getX(), (int) this.center.getY(), (int) this.r);
    }

    /**
     * Updates the ball's state as time progresses.
     * This method triggers the ball to move by calling the `moveOneStep` method,
     * updating its position based on its current velocity and checking for collisions
     * in the game environment.
     */
    public void timePassed() {
        moveOneStep();
    }

    /**
     * change ball speed.
     *
     * @param v the speed.
     */
    public void setVelocity(Velocity v) {
        this.v = v;
    }

    /**
     * set the speed with dx and dy.
     *
     * @param dx speed
     * @param dy speed
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * get speed.
     *
     * @return speed.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * move the ball.
     */
    public void moveOneStep() {
        // Calculate the trajectory line from current position to the next position
        Point nextCenter = new Point(this.center.getX() + v.getDx(), this.center.getY() + v.getDy());

        // Extend the trajectory line by the radius of the ball
        nextCenter = extendLine(this.center, nextCenter, this.r);
        Line trajectory = new Line(this.center, nextCenter);

        // Ask the environment for the closest collision along the trajectory
        CollisionInfo collision = this.environment.getClosestCollision(trajectory);

        if (collision != null) {
            Collidable obj = collision.collisionObject();
            Point collisionPoint = collision.collisionPoint();

            if (obj.isPaddle()) {
                Rectangle rect = obj.getCollisionRectangle();
                double left = rect.getUpperLeft().getX();
                double right = left + rect.getWidth();

                // Check if hit from the side
                if (Math.abs(collisionPoint.getX() - left) < 1.0
                        || Math.abs(collisionPoint.getX() - right) < 1.0) {
                    this.v = new Velocity(-this.v.getDx(), this.v.getDy());
                } else {
                    this.v = obj.hit(collisionPoint, this.v);
                }
            } else {
                this.v = obj.hit(collisionPoint, this.v);
            }

            // Move a bit in the new direction to avoid getting stuck
            this.center = new Point(
                    this.center.getX() + this.v.getDx() * 0.5,
                    this.center.getY() + this.v.getDy() * 0.5
            );
        } else {
            // No collision: move normally
            this.center = new Point(
                    this.center.getX() + v.getDx(),
                    this.center.getY() + v.getDy()
            );
        }
    }

    /**
     *extendLine.
     * @param start start point.
     * @param end end point.
     * @param distance the len.
     * @return the point of co.
     */
    private Point extendLine(Point start, Point end, double distance) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double length = Math.sqrt(dx * dx + dy * dy);  // The length of the line

        // Normalize the vector
        double unitDx = dx / length;
        double unitDy = dy / length;

        // Extend the line by the specified distance
        double extendedX = end.getX() + unitDx * distance;
        double extendedY = end.getY() + unitDy * distance;

        return new Point(extendedX, extendedY);
    }

    /**
     *set the game.
     * @param environment the environment.
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }
    /**
     * give the max radios the ball can be.
     *
     * @param x    point
     * @param y    point
     * @param rect it is inside.
     * @return the max.
     */
    private static int maxRadiusToFitInside(double x, double y, Rectangle rect) {
        if (rect == null) {
            return 0;
        }
        Point upperLeft = rect.getUpperLeft();
        double leftX = upperLeft.getX();
        double topY = upperLeft.getY();
        double rightX = leftX + rect.getWidth();
        double bottomY = topY + rect.getHeight();

        int maxR = (int) Math.min(Math.min(x - leftX, rightX - x), Math.min(y - topY, bottomY - y));
        return Math.max(1, maxR - 1);
    }


    // For yellow balls — force outside gray rectangle
    private static int maxRadiusOutsideGray(double x, double y, Rectangle rect,
                                            int maxW, int maxH, int minW, int minH) {
        int maxR = (int) Math.min(Math.min(x - minW, maxW - x), Math.min(y - minH, maxH - y));
        if (rect != null) {
            Point upperLeft = rect.getUpperLeft();
            double rectLeft = upperLeft.getX();
            double rectTop = upperLeft.getY();
            double rectRight = rectLeft + rect.getWidth();
            double rectBottom = rectTop + rect.getHeight();

            if (x > rectLeft && x < rectRight && y > rectTop && y < rectBottom) {
                return 0; // completely inside — not allowed
            }

            double distToLeft = Math.abs(x - rectLeft);
            double distToRight = Math.abs(x - rectRight);
            double distToTop = Math.abs(y - rectTop);
            double distToBottom = Math.abs(y - rectBottom);

            double minDist = Math.min(Math.min(distToLeft, distToRight), Math.min(distToTop, distToBottom));
            maxR = (int) Math.min(maxR, minDist);
        }
        return Math.max(1, maxR - 1);
    }
    /**
     * check if inside.
     *
     * @param x    point of ball
     * @param y    point of ball
     * @param r    radios of ball.
     * @param rect the rectangle.
     * @return true or false.
     */
    private static boolean isInsideRectangle(double x, double y, double r, Rectangle rect) {
        if (rect == null) {
            return false;
        }

        Point upperLeft = rect.getUpperLeft();
        double leftX = upperLeft.getX();
        double topY = upperLeft.getY();
        double rightX = leftX + rect.getWidth();
        double bottomY = topY + rect.getHeight();

        return (x + r > leftX && x - r < rightX && y + r > topY && y - r < bottomY);
    }

    /**
     * Predicts the next position of the ball based on its current center and velocity.
     * Combines the current position of the ball with its velocity to calculate
     * the new position.
     *
     * @return A Point representing the next predicted position of the ball.
     */
    public Point predictNextPosition() {
        return new Point(this.center.x() + this.v.getDx(),
                this.center.y() + this.v.getDy());
    }


}








