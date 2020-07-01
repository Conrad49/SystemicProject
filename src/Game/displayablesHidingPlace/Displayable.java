package Game.displayablesHidingPlace;

import javafx.scene.image.Image;

/**
 * Everything that needs to be displayed shares a few things in common and
 * it's also nice to be able to use the same logic to display everything.
 */
public abstract class Displayable {
    protected double x, y;
    protected int width, height;
    protected boolean cordsAtCorner;

    public Displayable(double x, double y, int width, int height, boolean cordsAtCorner) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cordsAtCorner = cordsAtCorner;
    }

    public abstract Image getImage();

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
        return cordsAtCorner;
    }
}
