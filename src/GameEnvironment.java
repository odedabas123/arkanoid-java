import java.util.ArrayList;
import java.util.List;
/**
 * This class holds all the things a ball can collide with.
 * It can check what the closest collision is on a given path.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Constructor – creates an empty list of collidables.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<>();
    }

    /**
     * Adds a new collidable object (like a block) to the game.
     *
     * @param c the object to add
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Removes a collidable object from the game.
     *
     * @param c the object to remove
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }
    /**
     * Given a line (a ball's path), finds the closest object it would hit.
     *
     * @param trajectory the line representing the ball's path
     * @return info about the closest collision (or null if none)
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        CollisionInfo closestCollidable = null;
        double minDistance = Double.MAX_VALUE;

        for (Collidable collidable : collidables) {
            Rectangle rectangle = collidable.getCollisionRectangle();
            List<Point> points = rectangle.intersectionPoints(trajectory);

            for (Point point : points) {
                double dist = trajectory.start().distance(point);
                if (dist < minDistance) {
                    minDistance = dist;
                    closestCollidable = new CollisionInfo(point, collidable);
                }
            }
        }

        return closestCollidable;
    }
}
