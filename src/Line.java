import java.util.List;

/**
 * Line object represents a line segment in 2D space defined by two points.
 */
public class Line {
    private final Point start;
    private final Point end;

    // Constructors

    /**
     * Builds a line from two points.
     *
     * @param start the start point of the line
     * @param end   the end point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Builds a line from four coordinates.
     *
     * @param x1 x-coordinate of start
     * @param y1 y-coordinate of start
     * @param x2 x-coordinate of end
     * @param y2 y-coordinate of end
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return the line's length
     */
    public double length() {
        return Math.sqrt(Math.pow(this.start.x() - this.end.x(), 2)
                + Math.pow(this.start.y() - this.end.y(), 2));
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the midpoint
     */
    public Point middle() {
        return new Point((this.start.x() + this.end.x()) / 2, (this.start.y() + this.end.y()) / 2);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Calculates the perpendicular distance from the line to a point.
     *
     * @param p the point
     * @return distance to the point
     */
    public double distanceTo(Point p) {
        double dx = this.end.x() - this.start.x();
        double dy = this.end.y() - this.start.y();
        double length = Math.sqrt(dx * dx + dy * dy);

        double numerator = Math.abs(dy * p.x() - dx * p.y()
                + this.end.x() * this.start.y() - this.end.y() * this.start.x());
        return numerator / length;
    }

    /**
     * Checks whether a point lies on the line segment.
     *
     * @param p the point to check
     * @return true if the point lies on the segment
     */
    public boolean isPointOnLine(Point p) {
        double lineLength = this.start.distance(this.end);
        double d1 = p.distance(this.start);
        double d2 = p.distance(this.end);
        return Math.abs((d1 + d2) - lineLength) < 1e-6;
    }

    private int orientation(Point p, Point q, Point r) {
        double val = (q.y() - p.y()) * (r.x() - q.x()) - (q.x() - p.x()) * (r.y() - q.y());
        if (val == 0) {
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }

    private boolean onSegment(Point p, Point q, Point r) {
        return q.x() >= Math.min(p.x(), r.x()) && q.x() <= Math.max(p.x(), r.x())
                && q.y() >= Math.min(p.y(), r.y()) && q.y() <= Math.max(p.y(), r.y());
    }

    /**
     * Checks if this line intersects with another line.
     *
     * @param other the other line
     * @return true if the lines intersect
     */
    public boolean isIntersecting(Line other) {
        Point p1 = this.start, q1 = this.end;
        Point p2 = other.start(), q2 = other.end();

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2
                && o3 != o4) {
            return true;
        }

        if (o1 == 0
                && onSegment(p1, p2, q1)) {
            return true;
        }

        if (o2 == 0
                && onSegment(p1, q2, q1)) {
            return true;
        }

        if (o3 == 0
                && onSegment(p2, p1, q2)) {
            return true;
        }

        if (o4 == 0 && onSegment(p2, q1, q2)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if this line intersects with either of the two specified lines.
     *
     * @param other1 the first line
     * @param other2 the second line
     * @return true if this line intersects either line
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1)
                || this.isIntersecting(other2);
    }

    /**
     * Returns the intersection point with another line, if exists.
     *
     * @param other the other line
     * @return intersection point or null
     */
    public Point intersectionWith(Line other) {
        double x1 = this.start.x(), y1 = this.start.y();
        double x2 = this.end.x(), y2 = this.end.y();
        double x3 = other.start().x(), y3 = other.start().y();
        double x4 = other.end().x(), y4 = other.end().y();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            return null;
        }

        double px = ((x1 * y2 - y1 * x2) * (x3 - x4)
                - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
        double py = ((x1 * y2 - y1 * x2) * (y3 - y4)
                - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;

        Point intersection = new Point(px, py);

        if (isPointOnSegment(intersection, this)
                && isPointOnSegment(intersection, other)) {
            return intersection;
        } else {
            return null;
        }
    }

    private boolean isPointOnSegment(Point p, Line segment) {
        double x = p.x(), y = p.y();
        double x1 = segment.start().x(), y1 = segment.start().y();
        double x2 = segment.end().x(), y2 = segment.end().y();

        return (x >= Math.min(x1, x2) && x <= Math.max(x1, x2)
                && y >= Math.min(y1, y2) && y <= Math.max(y1, y2));
    }

    /**
     * Checks if two lines are equal (same start and end points, in any order).
     *
     * @param obj the object to compare
     * @return true if the lines are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null
                || getClass() != obj.getClass()) {
            return false;
        }

        Line other = (Line) obj;

        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Placeholder method. Currently returns false.
     *
     * @param other another line
     * @return always false
     */
    public boolean intersects(Line other) {
        return false; // Placeholder
    }

    /**
     * Finds the closest intersection point between this line and a rectangle.
     *
     * @param rect the rectangle
     * @return closest intersection point to the start of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionPoints = rect.intersectionPoints(this);
        if (intersectionPoints.isEmpty()) {
            return null;
        }

        Point closest = intersectionPoints.get(0);
        double minDistance = this.start().distance(closest);

        for (Point p : intersectionPoints) {
            double distance = this.start().distance(p);
            if (distance < minDistance) {
                closest = p;
                minDistance = distance;
            }
        }

        return closest;
    }

    /**
     * Checks whether a point lies exactly on this line segment.
     *
     * @param p the point
     * @return true if on the segment
     */
    public boolean isPointOnSegment(Point p) {
        double totalDistance = this.start().distance(this.end());
        double distanceFromStart = this.start().distance(p);
        double distanceToEnd = p.distance(this.end());

        double epsilon = 0.0001;
        return Math.abs((distanceFromStart + distanceToEnd) - totalDistance) < epsilon;
    }
}

