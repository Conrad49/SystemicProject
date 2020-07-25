package Game.Entities;

import Game.Animation;
import Game.Camera;
import Game.Chunk;
import Game.Main;
import Game.Tiles.Tile;
import Game.testing.Vector;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.w3c.dom.css.Rect;

import java.awt.*;

public abstract class Entity {

    Image texture;
    Color backColor;
    protected Rectangle boundsBox;
    int tileX;
    int tileY;
    double posX;
    double posY;
    boolean isVisible = false;
    // this is a stupid sloppy fix for the special case that is our only entitiy the player
    // when more are added this will be moved to displayable and the fixed values will be removed
    protected int width = 64;
    protected int height = 128;
    private Animation currentAnimation;

    private Vector position = new Vector();
    private Vector direction = new Vector(0, 0);
    private Vector velocity = new Vector(0, 0);

    static Vector contactPoint = new Vector(0, 0);
    private Vector contactNormal = new Vector(0, 0);
    double time = 0;
    Vector mouseVec = new Vector(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
    Vector dir = mouseVec.subtract(position);

    private static final Vector UP = new Vector(0, -1);
    private static final Vector DOWN = new Vector(0, 1);
    private static final Vector LEFT = new Vector(-1, 0);
    private static final Vector RIGHT = new Vector(1, 0);

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

        this.position.setX((int) this.posX);
        this.position.setY((int) this.posY);
    }

    public Entity(Image texture, Rectangle boundsBox) {
        this.texture = texture;
        this.boundsBox = boundsBox;
        this.posX = boundsBox.getX();
        this.posY = boundsBox.getY();
        this.tileX = (int)posX/ Tile.getTileWidth();
        this.tileY = (int)posY/ Tile.getTileWidth();
    }


    public void tick(){
        // update positions and velocities / speeds
        //this.posX += this.xSpeed;
        //this.posY += this.ySpeed;

        this.tileX = (int)this.posX / Tile.getTileWidth();
        this.tileY = (int)this.posY / Tile.getTileWidth();
        this.posX = this.position.getX();
        this.posY = this.position.getY();
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

            Shape intersect = Shape.intersect(shiftedRect, tileBounds);
            if (intersect.getBoundsInLocal().getWidth() != -1) {

                // left
                if(tileBounds.getX() + tileBounds.getWidth() < this.boundsBox.getX() && (tile.getY() / tile.getBoundsBox().getHeight()) == this.tileY){
                    this.position.setX(this.position.getX() + intersect.getBoundsInLocal().getWidth());
                    this.posX += intersect.getBoundsInLocal().getWidth();
                }

                // right
                if(tileBounds.getX() > this.boundsBox.getX() && (tile.getY() / tile.getBoundsBox().getHeight()) == this.tileY){
                    this.position.setX(this.position.getX() - intersect.getBoundsInLocal().getWidth());
                    this.posX -= intersect.getBoundsInLocal().getWidth();

                }

                // above
                if(tileBounds.getY() + tileBounds.getHeight() < this.boundsBox.getY() && (tile.getX() / tile.getBoundsBox().getWidth()) == this.tileX){
                    this.position.setY(this.position.getY() + intersect.getBoundsInLocal().getHeight());
                    this.posY += intersect.getBoundsInLocal().getHeight();
                }

                // below
                if(tileBounds.getY() > this.boundsBox.getY() && (tile.getX() / tile.getBoundsBox().getWidth()) == this.tileX){
                    this.position.setY(this.position.getY() - intersect.getBoundsInLocal().getHeight());
                    this.posY -= intersect.getBoundsInLocal().getHeight();
                }

                this.position.setX((int) this.position.getX());
                this.position.setY((int) this.position.getY());
            }

        }
    }


    /**
     * <p>Takes in a starting position, a vector that represents direction/velocity, and a Rectangle object that represents
     * what you want to check for collision against. It then returns true if the vector/ray intersects with the given
     * rectangle and produces the point at which the collision occurs, a "time" value, and another vector/ray that
     * represents the direction a moving object would have to move in order to stop colliding.<p/>
     *
     * This method was made with the complete help of this video: https://www.youtube.com/watch?v=8JJ-4JgR7Dg
     */
    public Object[] rayVsRect(Vector oPosition, Vector ray, Rectangle target){
        Vector targetPos = new Vector(target.getX(), target.getY());
        Vector near = targetPos.subtract(oPosition).divide(ray);
        Vector far = targetPos.add(target.getHeight()).subtract(oPosition);
        far.setToVec(far.divide(ray));

        if(near.getX() > far.getX()){
            double temp = near.getX();
            near.setX(far.getX());
            far.setX(temp);
        }

        if(near.getY() > far.getY()){
            double temp = near.getY();
            near.setY(far.getY());
            far.setY(temp);
        }

        if(near.getX() > far.getY() || near.getY() > far.getX()){
            return new Object[] {false};
        }

        double hitNearXVal = Math.max(near.getX(), near.getY());
        time = hitNearXVal;
        double hitFarXVal = Math.min(far.getX(), far.getY());

        if(hitFarXVal < 0){
            return new Object[] {false};
        }

        contactPoint = ray.multiply(hitNearXVal);
        contactPoint = contactPoint.add(oPosition);


        if(near.getX() > near.getY()){
            if(ray.getX() < 0){
                contactNormal.setX(1);
                contactNormal.setY(0);
            } else {
                contactNormal.setX(-1);
                contactNormal.setY(0);
            }
        } else if (near.getX() < near.getY()){
            if(ray.getY() < 0){
                contactNormal.setX(0);
                contactNormal.setY(1);
            } else {
                contactNormal.setX(0);
                contactNormal.setY(-1);
            }
        }

        return new Object[] {true, contactNormal, contactPoint, time};
    }


    /**
     * <p>Applies the logic of {@link Entity#rayVsRect(Vector, Vector, Rectangle)} to a moving rectangle that needs to be checked for collision with a given
     * other Rectangle<p/>
     * This method was made with the complete help of this video: https://www.youtube.com/watch?v=8JJ-4JgR7Dg
     */
    public Object[] movingRectVcRect(Entity in, Rectangle target){
        Rectangle inputRectangle = in.boundsBox;
        if(in.getVelocity().getX() == 0 && in.getVelocity().getY() == 0){
            return new Object[] {false};
        }

        Rectangle adjustedTarget = new Rectangle();

        //expands left and upwards
        adjustedTarget.setX(target.getX() - (inputRectangle.getWidth() / 2));
        adjustedTarget.setY(target.getY() - (inputRectangle.getHeight() / 2));

        //expands right and downwards
        adjustedTarget.setWidth(target.getWidth() + inputRectangle.getWidth());
        adjustedTarget.setHeight(target.getHeight() + inputRectangle.getHeight());

        Object[] results = rayVsRect(in.position, in.getVelocity(), adjustedTarget);

        if((boolean)results[0]){
            if(time <= 1){
                return results;
            }
        }

        return new Object[] {false};
    }

    public static void drawContactPoint(){
        Group group = new Group();
        group.getChildren().add(new Circle(contactPoint.getX(), contactPoint.getY(), 5));
        //Camera.setGUIGroup(group);
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

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void addToPositionX(double x) { this.position.setX(this.position.getX() + x); }

    public void addToPositionY(double y) {
        this.position.setY(this.position.getY() + y);
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

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getDirection() {
        return direction;
    }

    public static Vector getUP() {
        return UP;
    }

    public static Vector getDOWN() {
        return DOWN;
    }

    public static Vector getLEFT() {
        return LEFT;
    }

    public static Vector getRIGHT() {
        return RIGHT;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setTexture(Image texture) {
        this.texture = texture;
    }

    public Vector getContactNormal() {
        return contactNormal;
    }

    public double getTime() {
        return time;
    }
}
