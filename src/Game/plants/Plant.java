package Game.plants;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Plants are meant to be in an instance of a tile which is the tile it grows on.
 * This allows for easy access of plants in an area and it's easy to find out what a
 * plant is growing on.
 */
public abstract class Plant {
    protected int health; // health is essentially how much plant there is, if health goes down some of the plant has been cut off.
    protected double energy;
    protected final int maxHealth, maxEnergy;
    protected int x, y;
    protected final int collectionRate;
    protected final double growthRate;
    protected final String type;
    protected final int width, height;

    public Plant(int health, int maxHealth, int energy, int maxEnergy, int collectionRate, int x, int y, double growthRate, String type, int width, int height) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.collectionRate = collectionRate;
        this.x = x;
        this.y = y;
        this.growthRate = growthRate;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns whether or not this plant can grow on the input tile.
     *
     * This method must be defined on each subclass plant.
     */
    public abstract boolean canGrowOn(String tileCode);

    /**
     * Returns a list of all tiles that this plant can grow on.
     *
     * This method must be defined on each subclass plant.
     */
    public abstract ArrayList<String> growsOn();

    /**
     * Turns energy into health
     */
    private void grow(){
        int growth = (int) (growthRate * energy);
        health += growth;
        energy -= growth;
        if(health > maxHealth){
            int excess = health - maxHealth;
            health -= excess;
            energy += excess;// excess will always be <= growth which is taken off energy
        }
    }


    /**
     * Generates energy based on how big the plant is and it's collection rate.
     */
    private void collect(){
        if(energy != maxEnergy) {
            // Find a percentage of health or leaves that is what percentage of the plant
            // is there which can get you what percentage of its energy can be made.
            energy += ((double) health / maxHealth) * collectionRate;
            if (energy > maxEnergy) {
                energy = maxEnergy;
            }
        }
    }

    /**
     * Does everything that normally happens over time.
     */
    public void tick(){
        grow();
        collect();
        reproduce();
    }

    /**
     * Finds out if a plant can / should reproduce at this second then does if it can.
     */
    abstract void reproduce();

    /**
     * Reduces health and energy based on damage dealt to leaves.
     * Depending on certain conditions if the health is brought
     * low enough or started at a high enough level or was hit
     * by a certain type of damage it may result in an item drop
     */
    public abstract void takeDamage(int damage, String type);

    /**
     * Gets the current image to be displayed based on health or size of the plant.
     *
     * Returns a static image variable that will be made and stored on each subclass.
     */
    public abstract Image getImage();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
