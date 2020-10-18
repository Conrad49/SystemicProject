package Game.plants;

import Game.Chunk;
import Game.Tiles.GrassTile;
import Game.Tiles.Tile;
import Game.World;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * This is a single blade of tall grass. If 3 blades of tall grass are
 * nearby they will combine into a normal tall grass. When that happens
 * the sum of the individual plants stats will be used.
 */
public class SingleTallGrass extends Plant{
    private static ArrayList<String> growsOn = new ArrayList<>();
    private static ArrayList<String> images = new ArrayList<>();
    private static boolean firstPlant = true;
    private static final double joinWidth = 32;//8 in game pixels
    public static final int maxHealth = 20, maxEnergy = 10;

    /**
     * Constructor for making grass at any part of its life
     * (probably never going to be used unless someone makes a magic spell or something
     * (well I guess technically it is used every time someone else used the other
     * constructor but that's an unnecessary detail))
     */
    public SingleTallGrass(int health, double energy, int x, int y) {
        super(health, maxHealth, energy, maxEnergy, 2, x, y, 0.5, "grass", 16, 64);

        if(firstPlant){
            growsOn.add(GrassTile.tileCode);
            for (int i = 1; i <= 5; i++) {
                images.add("/res/plants/TallGrass/SingleTallGrass" + i + ".png");
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

        if(firstPlant){
            growsOn.add(GrassTile.tileCode);
            for (int i = 1; i <= 5; i++) {
                images.add("/res/plants/TallGrass/SingleTallGrass" + i + ".png");
            }

            firstPlant = false;
        }

        // health / maxHealth returns 0.05-1 and the desired range is 0-4
        // * 5  changes the range to 0.25-5 and after the int it's 0 1 2 3 4 or 5
        // the -1 on health takes away the one special case of 1 * 5 and replaces it with
        // a simple 0 * 5 so we are now going 0 1 2 3 4
        int i = (int)(((double)(health - 1) / maxHealth) * 5);
        return new Image(images.get(i));
    }

    @Override
    protected void extraTick() {
        combineGrass();
    }

    /**
     * checks for nearby grasses. If two other grasses are found they will combine into
     * a new tall grass and remove the old grasses from existence.
     */
    private void combineGrass(){
        ArrayList<SingleTallGrass> nearby = new ArrayList<>();
        nearby.add(this);
        // check all grasses withing a 3 by 3 area surrounding the tile this grass occupies
        ArrayList<SingleTallGrass> grasses = getGrass(World.getTile( (int)y / Tile.getTileWidth(), (int)x / Tile.getTileWidth()));
        grasses.remove(this);

        for (int i = 0; i < grasses.size(); i++) {
            SingleTallGrass g = grasses.get(i);
            // if any grass is withing the join width it will be added to a list
            if(g.getX() <= x + joinWidth && g.getX() >= x - joinWidth &&
                    g.getY() <= y + joinWidth && g.getY() >= y - joinWidth){
                nearby.add(g);
                // if 2 are found plus this one makes 3 total which is enough for a tallgrass
                if (nearby.size() == 3){
                    join(nearby);

                    // remove the old singles so there will not be an infinite amount of new grasses
                    for (int j = 0; j < nearby.size(); j++) {
                        SingleTallGrass toRemove = nearby.get(j);
                        int x = ((int)toRemove.getX()) / Tile.getTileWidth();
                        int y = ((int)toRemove.getY()) / Tile.getTileWidth();
                        World.getTile(y, x).removePlant(toRemove);
                    }
                    // there is no point looking for more grasses since this grass does not exist anymore
                    return;
                }
            }
        }
    }

    /**
     * Returns an arrayList of all the single tall grasses in touching tiles
     */
    private ArrayList<SingleTallGrass> getGrass(Tile t){
        ArrayList<SingleTallGrass> grass = new ArrayList<>();

        // -1 makes it the top left corner of the 3 by 3 square
        int x = (int)t.getX() / Tile.getTileWidth() - 1;
        int y = (int)t.getY() / Tile.getTileWidth() - 1;
        if(x < 0){
            x = 0;
        }
        if(y < 0){
            y = 0;
        }


        // loop through all the tiles
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // loop through their plants

                if(x + j < World.getWorldChunkWidth() * Chunk.getSize() && y + i < World.getWorldChunkHeight() * Chunk.getSize()) {
                    for (Plant p : World.getTile(x + j, y + i).getPlants()) {
                        //check if they are grass
                        if (p instanceof SingleTallGrass) {
                            SingleTallGrass g = (SingleTallGrass) p;
                            grass.add(g);
                        }
                    }
                }
            }
        }

        return grass;
    }

    /**
     * 3 single tall grasses are added together to make a tall grass at the average of
     * their coordinates. The new tall grass will have the totals of all their healths
     * and energies not the averages since the tall grass is thicker and tougher than
     * a single blade of tall grass.
     */
    private void join(ArrayList<SingleTallGrass> toJoin){
        int health = 0;
        double energy = 0, x = 0, y = 0;

        // Health and energy become a mass but coordinates are averaged.
        for(SingleTallGrass g : toJoin){
            health += g.getHealth();
            energy += g.getEnergy();
            x += g.getX() / 3.0;
            y += g.getY() / 3.0;
        }

        int xx = (int)Math.round(x), yy = (int)Math.round(y);
        World.getTile(yy / Tile.getTileWidth(), xx / Tile.getTileWidth()).addPlant(new TallGrass(health, energy, xx, yy));
    }

    public static int getMaxHealth() {
        return maxHealth;
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }
}
