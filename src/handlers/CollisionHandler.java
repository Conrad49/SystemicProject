package handlers;

import Game.Camera;
import Game.Entities.Entity;
import Game.Main;
import Game.Tiles.LavaTile;
import Game.Tiles.Tile;
import Game.testing.Vector;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class CollisionHandler {
    Entity entity;
    static Vector contactPoint = new Vector(0, 0);
    private Vector contactNormal = new Vector(0, 0);
    double time = 0;

    public CollisionHandler(Entity entity) {
        this.entity = entity;
    }

    public void collide(){
        //detect for collision and find what type of collision resolution has to occur
        //resolve collision
        checkEntityTileCollision();

    }

    public void doPostCollisionAction(){
        //unique to the entity that is colliding and changes depending on what it collided with
    }

    public void detectTileCollision() {

    }

    public void resolveTileCollision(Vector normal, double ctime){
        entity.setVelocity(entity.getVelocity().add(((normal.multiply(new Vector(Math.abs(entity.getVelocity().getX()), Math.abs(entity.getVelocity().getY()))).multiply(1-ctime)))));
    }

    private void checkEntityTileCollision(){
        Tile[] surroundingTiles = new Tile[9];
        Tile[][] allTiles = Tile.getAllTiles();

        // above
        surroundingTiles[0] = allTiles[entity.getTileY() - 1][entity.getTileX()];
        //surroundingTiles[0].backColor = Color.RED;

        // below
        surroundingTiles[1] = allTiles[entity.getTileY() + 1][entity.getTileX()];
        //surroundingTiles[1].backColor = Color.BLUE;

        // left
        surroundingTiles[2] = allTiles[entity.getTileY()][entity.getTileX() - 1];
        //surroundingTiles[2].backColor = Color.PALEGOLDENROD;

        // right
        surroundingTiles[3] = allTiles[entity.getTileY()][entity.getTileX() + 1];
        //surroundingTiles[3].backColor = Color.GRAY;

        surroundingTiles[4] = allTiles[entity.getTileY()][entity.getTileX()];

        //diagonals
        surroundingTiles[5] = allTiles[entity.getTileY() - 1][entity.getTileX() - 1];
        surroundingTiles[6] = allTiles[entity.getTileY() - 1][entity.getTileX() + 1];
        surroundingTiles[7] = allTiles[entity.getTileY() + 1][entity.getTileX() + 1];
        surroundingTiles[8] = allTiles[entity.getTileY() + 1][entity.getTileX() - 1];

        //colliding = false;
        if(allTiles[entity.getTileY()][entity.getTileX()] instanceof LavaTile){
            System.out.println("You Died!");
            System.exit(0);
        }

        for(Tile tile : surroundingTiles){
            if (tile.isSolid()) {
                Object[] stuffs = movingRectVcRect(entity, tile.getBoundsBox());
                if ((boolean)stuffs[0]) {
                    Vector normal = (Vector) stuffs[1];
                    Vector point = (Vector) stuffs[2];

                    double ctime = (double) stuffs[3];

                    //Tile collision resolution
                    resolveTileCollision(normal, ctime);
                    //colliding = true;
                }
            }
        }
        Camera.setGUIGroup(Entity.group);
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
