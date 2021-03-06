package handlers;

import Game.Camera;
import Game.Entities.Entity;
import Game.Main;
import Game.Tiles.Tile;
import Game.World;
import Game.testing.Vector;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;

public abstract class CollisionHandler {
    protected Entity entity;
    static Vector contactPoint = new Vector(0, 0);
    private Vector contactNormal = new Vector(0, 0);
    double time = 0;

    public CollisionHandler(Entity entity) {
        this.entity = entity;
    }

    public void collide(){
        checkSurroundingTiles();
    }

    protected abstract void doPostCollisionAction(Tile tile);

    public void resolveTileCollision(Vector normal, double ctime){
        entity.setVelocity(entity.getVelocity().add(((normal.multiply(new Vector(Math.abs(entity.getVelocity().getX()), Math.abs(entity.getVelocity().getY()))).multiply(1-ctime)))));
    }

    private void checkSurroundingTiles(){
        Tile[] surroundingTiles = new Tile[9];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                surroundingTiles[3 * i + j] = World.getTile(entity.getTileX() - 1 + j, entity.getTileY() - 1 + i);
            }
        }

        Arrays.stream(surroundingTiles)
                .forEach(this::doPostCollisionAction);
        Camera.setGUIGroup(Entity.group);
    }

    protected void createCollisionDrawing(Vector vector) {
        double[] pointShift = Main.shift(vector.getX(), vector.getY());
        Entity.group.getChildren().add(new Circle(pointShift[0], pointShift[1], 5));
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
        double[] coords = Main.shift(oPosition.getX(), oPosition.getY());
        Vector displayPos = new Vector(coords[0], coords[1]);
        Line rayLine = new Line(displayPos.getX(), displayPos.getY(), displayPos.getX() + ray.getX(), displayPos.getY() + ray.getY());

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
            } else {
                contactNormal.setX(-1);
            }
            contactNormal.setY(0);
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
        Rectangle inputRectangle = in.getBoundsBox();
        if(in.getVelocity().getX() == 0 && in.getVelocity().getY() == 0){
            return new Object[] {false};
        }

        Rectangle adjustedTarget = new Rectangle();

        //expands left and upwards
        adjustedTarget.setX(target.getX() - (inputRectangle.getWidth() / 2));
        adjustedTarget.setY(target.getY() - (inputRectangle.getHeight() / 2));

        //expands right and downwards
        adjustedTarget.setWidth(target.getWidth() + inputRectangle.getWidth() / 2);
        adjustedTarget.setHeight(target.getHeight() + inputRectangle.getHeight());


        double[] coords = Main.shift(adjustedTarget.getX(), adjustedTarget.getY());
        Rectangle displayRect = new Rectangle(coords[0], coords[1], adjustedTarget.getWidth(), adjustedTarget.getHeight());
        //group.getChildren().add(displayRect);

        Object[] results = rayVsRect(in.getPosition(), in.getVelocity().multiply(3), adjustedTarget);

        if((boolean)results[0]){
            if(entity.getTime() <= 1){
                entity.getCurrentAnimation().resetCount();
                return results;
            }
        }

        return new Object[] {false};
    }



}
