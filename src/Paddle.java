import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Paddle class represents the player's paddle in the game.
 * It implements both Sprite and Collidable interfaces, meaning it can be drawn and interact with balls.
 */
public class Paddle implements Sprite, Collidable {
    private Rectangle rectangle; // The paddle's shape
    private biuoop.KeyboardSensor keyboard; // For detecting left/right movement
    private int speed; // The number of pixels the paddle moves per update

    /**
     * Constructs a Paddle with the given keyboard sensor, rectangle, and movement speed.
     *
     * @param keyboard  the keyboard sensor for detecting input
     * @param rectangle the shape and position of the paddle
     * @param speed     the movement speed of the paddle
     */
    public Paddle(KeyboardSensor keyboard, Rectangle rectangle, int speed) {
        this.keyboard = keyboard;
        this.rectangle = rectangle;
        this.speed = speed;
    }

    /**
     * @return the rectangle representing the paddle's current position and size
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     * Moves the paddle to the left, wrapping around the screen if necessary.
     */
    public void moveLeft() {
        double newX = this.rectangle.getUpperLeft().getX() - speed;
        double screenWidth = 800; // Screen width assumed to be 800

        if (newX + this.rectangle.getWidth() <= 0) {
            newX = screenWidth;
        }

        this.rectangle = new Rectangle(
                new Point(newX, this.rectangle.getUpperLeft().getY()),
                this.rectangle.getWidth(),
                this.rectangle.getHeight(),
                this.rectangle.getStrokeColor()
        );
    }

    /**
     * Moves the paddle to the right, wrapping around the screen if necessary.
     */
    public void moveRight() {
        double newX = this.rectangle.getUpperLeft().getX() + speed;
        double screenWidth = 800;

        if (newX >= screenWidth) {
            newX = -this.rectangle.getWidth();
        }

        this.rectangle = new Rectangle(
                new Point(newX, this.rectangle.getUpperLeft().getY()),
                this.rectangle.getWidth(),
                this.rectangle.getHeight(),
                this.rectangle.getStrokeColor()
        );
    }

    /**
     * Called every frame to update the paddle's position based on user input.
     */
    @Override
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the screen.
     *
     * @param d the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        this.rectangle.drawOn(d);
    }

    /**
     * @return the rectangle used for collision detection
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Adds this paddle to the game as both a sprite and a collidable object.
     *
     * @param g the game instance to add the paddle to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Used by the game logic to identify this object as a paddle (without using instanceof).
     *
     * @return true, indicating this object is a paddle
     */
    @Override
    public boolean isPaddle() {
        return true;
    }

    /**
     * Notifies the paddle of a collision with a ball and calculates the new velocity.
     *
     * @param collisionPoint   the point where the ball hit the paddle
     * @param currentVelocity  the ball's current velocity
     * @return the new velocity after the hit
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Rectangle rect = this.getCollisionRectangle();
        double rectTop = rect.getUpperLeft().getY();
        double rectBottom = rectTop + rect.getHeight();
        double rectLeft = rect.getUpperLeft().getX();
        double rectRight = rectLeft + rect.getWidth();
        double speed = currentVelocity.getSpeed();
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();

        // Hit from the bottom
        if (y >= rectBottom - 1e-6) {
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy()));
        }

        // Hit from left or right side
        if (x <= rectLeft + 1e-6) {
            return new Velocity(-Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }
        if (x >= rectRight - 1e-6) {
            return new Velocity(Math.abs(currentVelocity.getDx()), currentVelocity.getDy());
        }

        // Hit from top: divide into 5 regions
        double width = rect.getWidth();
        double regionWidth = width / 5;

        if (x < rectLeft + regionWidth) {
            return Velocity.fromAngleAndSpeed(300, speed); // sharp left
        } else if (x < rectLeft + 2 * regionWidth) {
            return Velocity.fromAngleAndSpeed(330, speed); // soft left
        } else if (x < rectLeft + 3 * regionWidth) {
            return new Velocity(currentVelocity.getDx(), -Math.abs(currentVelocity.getDy())); // straight up
        } else if (x < rectLeft + 4 * regionWidth) {
            return Velocity.fromAngleAndSpeed(30, speed); // soft right
        } else {
            return Velocity.fromAngleAndSpeed(60, speed); // sharp right
        }
    }
}






