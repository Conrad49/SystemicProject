package Game.Entities;

import Game.Animation;
import Game.Chunk;
import Game.Entities.Player;
import Game.Tiles.Tile;
import Game.testing.Vector;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class Entity {

    double xSpeed;
    double ySpeed;
    Image texture;
    Color backColor;
    protected Rectangle boundsBox;
    int tileX;
    int tileY;
    double posX;
    double posY;
    boolean isVisible = false;
    protected int width;
    protected int height;
    private Animation currentAnimation;
    Vec2d direction;
    private Vector position = new Vector();

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setCurrentAnimation(Animation currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public Entity(Color backColor, Rectangle boundsBox) {
        this.backColor = backColor;
        this.boundsBox = boundsBox;
        this.posX = boundsBox.getX();
        this.posY = boundsBox.getY();
        this.tileX = (int)posX/ Tile.getWidth();
        this.tileY = (int)posY/ Tile.getWidth();

        this.position.x = (int)this.posX;
        this.position.y = (int)this.posY;
    }

    public Entity(Image texture, Rectangle boundsBox) {
        this.texture = texture;
        this.boundsBox = boundsBox;
        this.posX = boundsBox.getX();
        this.posY = boundsBox.getY();
        this.tileX = (int)posX/ Tile.getWidth();
        this.tileY = (int)posY/ Tile.getWidth();
    }


    public void tick(){
        // update positions and velocities / speeds
        //this.posX += this.xSpeed;
        //this.posY += this.ySpeed;
        this.tileX = (int)this.posX / Tile.getWidth();
        this.tileY = (int)this.posY / Tile.getWidth();
        this.posX = this.position.x;
        this.posY = this.position.y;
    }

    public void checkVisiblity(Player p){
        Tile[][] visibleTiles = p.visibleTiles;

        if(this.tileX >= visibleTiles[0][0].getPosX() && this.tileY >= visibleTiles[0][0].getPosY()){
            if(this.tileX <= visibleTiles[visibleTiles.length-1][0].getPosX()){
                if(this.tileY <= visibleTiles[visibleTiles.length-1][visibleTiles.length-1].getPosY()){



                }
            }
        }
    }


    /**
     * Checks the entity that this method is called on for collision with a given tile
     */
    public void checkTileCollision(Tile tile){
        Rectangle tileBounds = tile.getBoundsBox();

        if (tile.isSolid()) {

            Rectangle shiftedRect = new Rectangle(this.posX - (this.boundsBox.getWidth() / 2), this.posY - (this.boundsBox.getHeight() / 2), this.boundsBox.getWidth(), this.boundsBox.getHeight());

            //boolean intersects = tileBounds.intersects(shiftedRect.getBoundsInLocal());

            Shape intersect = Shape.intersect(shiftedRect, tileBounds);
            if (intersect.getBoundsInLocal().getWidth() != -1) {

                // left
                if(tileBounds.getX() + tileBounds.getWidth() < this.boundsBox.getX() && (tile.getPosY() / tile.getBoundsBox().getHeight()) == this.tileY){
                    this.position.x += intersect.getBoundsInLocal().getWidth();
                    //this.xSpeed = 0;
                }

                // right
                if(tileBounds.getX() > this.boundsBox.getX() && (tile.getPosY() / tile.getBoundsBox().getHeight()) == this.tileY){
                    this.position.x -= intersect.getBoundsInLocal().getWidth();
                    //this.posX -= intersect.getBoundsInLocal().getWidth();
                    //this.xSpeed = 0;
                }

                // above
                if(tileBounds.getY() + tileBounds.getHeight() < this.boundsBox.getY() && (tile.getPosX() / tile.getBoundsBox().getWidth()) == this.tileX){
                    this.position.y += intersect.getBoundsInLocal().getHeight();
                    //this.ySpeed = 0;
                }

                // below
                if(tileBounds.getY() > this.boundsBox.getY() && (tile.getPosX() / tile.getBoundsBox().getWidth()) == this.tileX){
                    this.position.y -= intersect.getBoundsInLocal().getHeight();
                    //this.ySpeed = 0;
                }
            }

        }
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public Vector getPosition() {
        return position;
    }

    public void addToPositionX(double x) {
        this.position.x += x;
    }

    public void addToPositionY(double y) {
        this.position.y += y;
    }


    public Image getTexture() {
        return this.texture;
    }


    /**
     * Gets an entity's coordinates in relation to all of the chunks (first chunk over and two chunks down)
     * @return An int array with index 0 being the x-coordinate and index 1 being the y-coordinate
     */
    public int[] getChunkCoords(){
        int[] chunkCoords = new int[2];
        chunkCoords[0] = this.tileX / Chunk.sideLength;
        chunkCoords[1] = this.tileY / Chunk.sideLength;
        return chunkCoords;
    }

    public Rectangle getBoundsBox() {
        return boundsBox;
    }


    public void setBoundsBox(Rectangle boundsBox) {
        this.boundsBox = boundsBox;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public Color getBackColor() {
        return backColor;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }
}
