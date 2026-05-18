/**
 * Holds info about a collision: where it happened and with what.
 */
public class CollisionInfo {

    // The point where the collision happened
    private final Point collisionPoint;

    // The object that was hit
    private final Collidable  collidable;

    /**
     * Constructor.
     *
     * @param point the collision point
     * @param object the object involved in the collision
     */
    public CollisionInfo(Point point, Collidable object) {
        this.collisionPoint = point;
        this.collidable = object;
    }

    /**
     * @return the point where the collision occurred
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the object that was hit
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}

