package Game.plants;

import Game.Tiles.DirtTile;
import Game.Tiles.GrassTile;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * This is a single blade of tall grass. If 3 blades of tall grass are
 * nearby they will combine into a normal tall grass. When that happens
 * the sum of the individual plants stats will be used.
 */
public class SingleTallGrass extends Plant{
    private static ArrayList<String> growsOn = new ArrayList<>();
    private static ArrayList<Image> images = new ArrayList<>();
    private static boolean firstPlant = true;

    /**
     * Constructor for making grass at any part of its life
     * (probably never going to be used unless someone makes a magic spell or something
     * (well I guess technically it is used every time someone else used the other
     * constructor but that's an unnecessary detail))
     */
    public SingleTallGrass(int health, int energy, int x, int y) {
        super(health, 20, energy, 10, 2, x, y, 0.5, "grass", 16, 64);

        if(firstPlant){
            growsOn.add(GrassTile.tileCode);
            for (int i = 1; i <= 5; i++) {
                images.add(new Image("/res/plants/TallGrass/SingleTallGrass" + i + ".png"));
            }

            firstPlant = false;
        }
    }

    public SingleTallGrass(int x, int y) {
        this(2, 0, x, y);
    }

    @Override
    public boolean canGrowOn(String tileCode) {
        for (int i = 0; i < growsOn.size(); i++) {
            if(growsOn.get(i).equals(tileCode)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<String> growsOn() {
        return growsOn;
    }

    @Override
    void reproduce() {

    }

    @Override
    public void takeDamage(int damage, String type) {
        if(type.equals("sharp")){
            health -= damage;
        }

        if(health <= 0){
            //determine how to remove a plant from existence
        }
    }

    @Override
    public Image getImage() {
        // health / maxHealth returns 0.05-1 and the desired range is 0-4
        // * 5  changes the range to 0.25-5 and after the int it's 0 1 2 3 4 or 5
        // the -1 on health takes away the one special case of 1 * 5 and replaces it with
        // a simple 0 * 5 so we are now going 0 1 2 3 4
        int i = (int)(((double)(health - 1) / maxHealth) * 5);
        return images.get(i);
    }
}
