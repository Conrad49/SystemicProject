package handlers.player;

import Game.Entities.Player;
import Game.Tiles.Tile;
import Game.testing.Vector;
import handlers.CollisionHandler;

public class PlayerCollisionHandler extends CollisionHandler {

    public PlayerCollisionHandler(Player player) {
        super(player);
    }

    @Override
    protected void doPostCollisionAction(Tile tile) {
        if (tile.isSolid()) {
            Object[] stuffs = entity.movingRectVcRect(entity, tile.getBoundsBox());
            if ((boolean) stuffs[0]) {
                Vector normal = (Vector) stuffs[1];

                createCollisionDrawing((Vector) stuffs[2]);

                double ctime = (double) stuffs[3];

                double xVelocity = Math.abs(entity.getVelocity().getX());
                double yVelocity = Math.abs(entity.getVelocity().getY());
                Vector normalOfVelocity = normal.multiply(new Vector(xVelocity, yVelocity)).multiply(1 - ctime);
                entity.setVelocity(entity.getVelocity().add(normalOfVelocity));
            }
        }
    }
}
