import biuoop.DrawSurface;

/**
 * The Sprite interface represents a game object that can be drawn on the screen
 * and updated as time passes.
 */
public interface Sprite {

    /**
     * Draws the sprite on the given DrawSurface.
     *
     * @param d the surface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed, so it can update its state.
     * This method is typically called once per frame.
     */
    void timePassed();
}
