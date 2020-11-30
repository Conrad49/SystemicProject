package Game.displayablesHidingPlace;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.io.Serializable;

/**
 * Everything that needs to be displayed shares a few things in common and
 * it's also nice to be able to use the same logic to display everything.
 */
public abstract class Displayable implements Serializable {
    protected double x, y;
    protected int width, height;
    protected boolean coordsAtCorner;

    public Displayable(double x, double y, int width, int height, boolean coordsAtCorner) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.coordsAtCorner = coordsAtCorner;
    }

    public abstract Image getImage();

    public abstract PixelReader getPixelReader();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean cordsAtCorner() {
        return coordsAtCorner;
    }
}
