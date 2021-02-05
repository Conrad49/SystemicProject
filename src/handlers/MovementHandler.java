package handlers;

import Game.Entities.Entity;
import Game.Tiles.Tile;

import java.util.HashSet;

public abstract class MovementHandler {
    protected Entity entity;
    double speed = 5;
    //private static HashSet<String> currentlyActiveKeys = new HashSet<String>(4);

    public MovementHandler(Entity entity) {
        this.entity = entity;
    }

    public void move(){
        this.findDirection();

        entity.getPosition().add(entity.getVelocity());

        entity.setTileX((int)entity.getPosX() / Tile.getTileWidth());
        entity.setTileY((int)entity.getPosY() / Tile.getTileWidth());
        entity.setPosX(entity.getPosition().getX());
        entity.setPosY(entity.getPosition().getY());

        entity.addToPositionX(entity.getVelocity().getX());
        entity.addToPositionY(entity.getVelocity().getY());

        double magnitude = Math.sqrt(Math.pow(entity.getDirection().getX(), 2) + Math.pow(entity.getDirection().getY(), 2));


        if (magnitude != 0) {
            entity.getDirection().normalize(magnitude);
        }

        entity.getVelocity().setToVec(entity.getDirection().multiply(speed));
    }

    protected abstract void findDirection();


}
