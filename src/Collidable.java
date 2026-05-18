/**
 * Something that can be hit (like blocks, paddle, etc.).
 * Has a shape and knows what to do when a collision happens.
 */
public interface Collidable {
    /**
     * @return the shape of the object (used for collision checks)
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that it was hit.
     * It can change the ball's velocity accordingly.
     *
     * @param collisionPoint the point where the collision occurred
     * @param currentVelocity the ball's velocity before the hit
     * @return the new velocity after the hit
     */
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
    boolean isPaddle();
}




