// Velocity specifies the change in position on the `x` and the `y` axes.
/**
 * Represents a velocity with horizontal (dx) and vertical (dy) components.
 * The velocity is used to change the position of a point over time.
 */
public class Velocity {

    // Final fields for horizontal (dx) and vertical (dy) velocity components
    private final double dx;
    private final double dy;

    /**
     * Constructs a new Velocity object with specified horizontal and vertical components.
     *
     * @param dx The horizontal component of the velocity.
     * @param dy The vertical component of the velocity.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * Gets the horizontal component of the velocity.
     *
     * @return The horizontal (dx) component of the velocity.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Gets the vertical component of the velocity.
     *
     * @return The vertical (dy) component of the velocity.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Creates a velocity object based on an angle and speed.
     *
     * @param angle The angle in degrees, where 0 is along the positive x-axis.
     * @param speed The speed (magnitude) of the velocity.
     * @return A new Velocity object calculated from the given angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Convert angle to radians once
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        double dy = -speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }
    /**
     * @return the total speed (calculated from dx and dy)
     */
    public double getSpeed() {
        return Math.hypot(this.dx, this.dy);
    }
}

