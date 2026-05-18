import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * The SpriteCollection class holds a list of Sprite objects.
 * It allows adding new sprites, notifying all sprites that time has passed,
 * and drawing all sprites on a given DrawSurface.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructs a new, empty SpriteCollection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the Sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Calls timePassed() on all sprites in the collection.
     * This is typically used to notify all sprites that a unit of time has passed,
     * so they can update their state accordingly.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(sprites);
        for (Sprite sprite : copy) {
            sprite.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the given DrawSurface.
     *
     * @param d the DrawSurface to draw the sprites on
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> copy = new ArrayList<>(sprites);
        for (Sprite sprite : copy) {
            sprite.drawOn(d);
        }
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the Sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }
}

