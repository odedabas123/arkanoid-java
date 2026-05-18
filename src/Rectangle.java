import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class represents a rectangle defined by its upper-left corner,
 * width, height, and fill/stroke colors. It provides geometric calculations
 * and drawing capabilities.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Color fillColor;    // Color used to fill the rectangle
    private Color strokeColor;  // Color used to draw the border of the rectangle

    /**
     * Constructs a rectangle with fill color (border color is the same as fill).
     *
     * @param upperLeft  the upper-left corner of the rectangle
     * @param width      the width of the rectangle
     * @param height     the height of the rectangle
     * @param fillColor  the color used to fill the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height, Color fillColor) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.strokeColor = fillColor;
    }

    /**
     * Constructs a rectangle with both fill and stroke colors.
     *
     * @param upperLeft    the upper-left corner of the rectangle
     * @param width        the width of the rectangle
     * @param height       the height of the rectangle
     * @param fillColor    the color used to fill the rectangle
     * @param strokeColor  the color used to draw the border
     */
    public Rectangle(Point upperLeft, double width, double height, Color fillColor, Color strokeColor) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    // Getters

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return the upper-left point of the rectangle
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * @return the fill color of the rectangle
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @return the stroke (border) color of the rectangle
     */
    public Color getStrokeColor() {
        return strokeColor;
    }

    // Setters

    /**
     * Sets the fill color of the rectangle.
     *
     * @param fillColor the new fill color
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Sets the stroke (border) color of the rectangle.
     *
     * @param strokeColor the new stroke color
     */
    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    // Corner points

    /**
     * @return the upper-right point of the rectangle
     */
    public Point getUpperRight() {
        return new Point(upperLeft.getX() + width, upperLeft.getY());
    }

    /**
     * @return the lower-left point of the rectangle
     */
    public Point getLowerLeft() {
        return new Point(upperLeft.getX(), upperLeft.getY() + height);
    }

    /**
     * @return the lower-right point of the rectangle
     */
    public Point getLowerRight() {
        return new Point(upperLeft.getX() + width, upperLeft.getY() + height);
    }

    // Sides as lines

    /**
     * @return the top edge of the rectangle as a line
     */
    public Line getTop() {
        return new Line(getUpperLeft(), getUpperRight());
    }

    /**
     * @return the bottom edge of the rectangle as a line
     */
    public Line getBottom() {
        return new Line(getLowerLeft(), getLowerRight());
    }

    /**
     * @return the left edge of the rectangle as a line
     */
    public Line getLeft() {
        return new Line(getUpperLeft(), getLowerLeft());
    }

    /**
     * @return the right edge of the rectangle as a line
     */
    public Line getRight() {
        return new Line(getUpperRight(), getLowerRight());
    }

    /**
     * Returns a list of intersection points between the rectangle and a given line.
     *
     * @param line the line to check intersections with
     * @return a list of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<>();
        for (Line edge : List.of(getTop(), getBottom(), getLeft(), getRight())) {
            Point p = line.intersectionWith(edge);
            if (p != null && edge.isPointOnSegment(p)) {
                points.add(p);
            }
        }
        return points;
    }

    /**
     * Draws the rectangle on the given drawing surface.
     *
     * @param d the drawing surface to draw on
     */
    public void drawOn(DrawSurface d) {
        // Fill
        d.setColor(fillColor);
        d.fillRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);

        // Stroke
        d.setColor(strokeColor);
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), (int) width, (int) height);
    }
}


