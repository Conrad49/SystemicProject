package Game.Entities;

import Game.Animation;
import Game.Chunk;
import Game.Tiles.Tile;
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
        this.tileX = (int)posX/ Tile.getTileWidth();
        this.tileY = (int)posY/ Tile.getTileWidth();
        this.width = (int)boundsBox.getWidth();
        this.height = (int)boundsBox.getHeight() * 4;
    }

    public Entity(Image texture, Rectangle boundsBox) {
        this.texture = texture;
        this.boundsBox = boundsBox;
        this.posX = boundsBox.getX();
        this.posY = boundsBox.getY();
        this.tileX = (int)posX/ Tile.getTileWidth();
        this.tileY = (int)posY/ Tile.getTileWidth();
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void tick(){
        // update positions and velocities / speeds
    }

    public void checkVisiblity(Player p){
        Tile[][] visibleTiles = p.visibleTiles;

        if(this.tileX >= visibleTiles[0][0].getX() && this.tileY >= visibleTiles[0][0].getY()){
            if(this.tileX <= visibleTiles[visibleTiles.length-1][0].getX()){
                if(this.tileY <= visibleTiles[visibleTiles.length-1][visibleTiles.length-1].getY()){



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

            boolean intersects = tileBounds.intersects(shiftedRect.getBoundsInLocal());

            Shape intersect = Shape.intersect(shiftedRect, tileBounds);
            if (intersect.getBoundsInLocal().getWidth() != -1) {

                // left
                if(tileBounds.getX() + tileBounds.getWidth() < this.boundsBox.getX() && (tile.getY() / tile.getBoundsBox().getHeight()) == this.tileY){
                    this.posX = this.posX + intersect.getBoundsInLocal().getWidth();
                    this.xSpeed = 0;
                }

                // right
                if(tileBounds.getX() > this.boundsBox.getX() && (tile.getY() / tile.getBoundsBox().getHeight()) == this.tileY){
                    this.posX = this.posX - intersect.getBoundsInLocal().getWidth();
                    this.xSpeed = 0;
                }

                // above
                if(tileBounds.getY() + tileBounds.getHeight() < this.boundsBox.getY() && (tile.getX() / tile.getBoundsBox().getWidth()) == this.tileX){
                    this.posY = this.posY + intersect.getBoundsInLocal().getHeight();
                    this.ySpeed = 0;
                }

                // below
                if(tileBounds.getY() > this.boundsBox.getY() && (tile.getX() / tile.getBoundsBox().getWidth()) == this.tileX){
                    this.posY = this.posY - intersect.getBoundsInLocal().getHeight();
                    this.ySpeed = 0;
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
