
/**
 * class point with x and y coordinate.
 */

/**
 * builder.
 *
 * @param x the x cord of new point.
 * @param y the y cord of new point.
 */

public record Point(double x, double y) {

    /**
     * builder.
     *
     * @param x the x cord of new point.
     * @param y the y cord of new point.
     */
    public Point(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    // distance -- return the distance of this point to the other point

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other The point to which the distance is calculated.
     * @return The calculated distance.
     */

    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    // equals -- return true is the points are equal, false otherwise

    /**
     * cheek if other point is the same.
     *
     * @param other is point we cheek it with this point.
     * @return ture or false.
     */
    public boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

    // Return the x and y values of this point

    /**
     * return x.
     *
     * @return give x.
     */

    public double getX() {
        return this.x;
    }

    /**
     * return y.
     *
     * @return give y.
     */

    public double getY() {
        return this.y;
    }


}
