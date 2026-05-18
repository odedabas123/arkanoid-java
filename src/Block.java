import biuoop.DrawSurface;
import java.awt.Color;

/**
 * This class represents a block (brick or wall) in the game.
 * It knows how to draw itself and respond to collisions.
 */
public class Block implements Collidable, Sprite {
    final Rectangle  rectangle;
    private Game game;
    private boolean destructible;
    private int hp;
    private int points;

    /**
     * Creates a block using the given rectangle.
     * @param rectangle the rectangle that defines position, size, and color
     */
    public Block(Rectangle rectangle) {
        this.rectangle = rectangle;
        this.destructible = false;
        this.hp = 1;
        this.points = 0;
    }

    /**
     * Marks this block as destructible with HP and point value.
     * @param g      the game instance
     * @param hp     hits required to destroy
     * @param points score awarded on destruction
     */
    public void setDestructible(Game g, int hp, int points) {
        this.game = g;
        this.destructible = true;
        this.hp = hp;
        this.points = points;
    }

    /**
     * Nothing happens over time for a block, but needed for interface.
     */
    @Override
    public void timePassed() {
        // Nothing to do for now
    }

    /**
     * Returns the rectangle that defines this block's shape.
     * @return the collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Calculates the new velocity after a collision with the block.
     * Flips direction based on which side was hit.
     * @param collisionPoint where the ball hit the block
     * @param currentVelocity the ball's velocity before hitting
     * @return the new velocity after the hit
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        final double EPSILON = 1e-6;

        double blockLeftX = rectangle.getUpperLeft().getX();
        double blockRightX = blockLeftX + rectangle.getWidth();
        double blockTopY = rectangle.getUpperLeft().getY();
        double blockBottomY = blockTopY + rectangle.getHeight();

        // Check if hit the left or right sides
        if (Math.abs(collisionPoint.getX() - blockLeftX) < EPSILON
                || Math.abs(collisionPoint.getX() - blockRightX) < EPSILON) {
            dx = -dx;
        }


        // Check if hit the top or bottom
        if (Math.abs(collisionPoint.getY() - blockTopY) < EPSILON
                || Math.abs(collisionPoint.getY() - blockBottomY) < EPSILON) {
            dy = -dy;
        }

        if (destructible && game != null) {
            hp--;
            if (hp <= 0) {
                game.addScore(points);
                game.removeBlock(this);
            } else {
                rectangle.setFillColor(rectangle.getFillColor().darker());
            }
        }

        return new Velocity(dx, dy);
    }
    @Override
    public boolean isPaddle() {
        return false;
    }

    /**
     * Sets the stroke color (border) of the block.
     * @param color the color to use for the border
     */
    public void setStroke(Color color) {
        this.rectangle.setStrokeColor(color);
    }

    /**
     * Draws the block on the screen using its rectangle.
     * @param d the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        rectangle.drawOn(d);  // Let Rectangle handle the drawing (fill + stroke)
    }

    /**
     * Adds this block to the game — as both a sprite and a collectable.
     * @param g the game to add to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}

