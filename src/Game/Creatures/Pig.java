package Game.Creatures;

import Game.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Lauren Sattesahn
 */
public class Pig extends Entity{
    public Pig(Color backColor, int x, int y) {
        super(backColor, new Rectangle(x, y, 128, 64));
        width = (int) boundsBox.getWidth();
        height = (int) boundsBox.getHeight();
        

    }


}
