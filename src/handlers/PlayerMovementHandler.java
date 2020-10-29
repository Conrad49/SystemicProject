package handlers;

import Game.Entities.Entity;
import Game.Entities.Player;
import Game.Main;
import Game.testing.Vector;
import handlers.MovementHandler;

import java.util.HashSet;

public class PlayerMovementHandler extends MovementHandler {

    Player player;

    public PlayerMovementHandler(Entity entity) {
        super(entity);
        player = (Player)entity;
    }

    @Override
    public void move(){
        super.move();
        handleKeyPresses();
    }

    public void handleKeyPresses(){

        HashSet<String> currentlyActiveKeys = MovementHandler.getCurrentlyActiveKeys();

        entity.setDirection(new Vector(0, 0));
        boolean isMoving = currentlyActiveKeys.contains("A") || currentlyActiveKeys.contains("D") || currentlyActiveKeys.contains("W") || currentlyActiveKeys.contains("S");

        if (isMoving) {
            player.setMoving(true);
            //speed = maxSpeed;
            findDirection();
        }


        /*if (!currentlyActiveKeys.contains("A")) {
            player.walkLeft.resetCount();
        }

        if (!currentlyActiveKeys.contains("D")) {
            player.walkRight.resetCount();
        }

        if (!currentlyActiveKeys.contains("W")) {
            player.walkUp.resetCount();
        }

        if (!currentlyActiveKeys.contains("S")) {
            player.walkDown.resetCount();
        }*/

        //player.setCurrentAnimation();

        // Binding for going in and out of fullscreen
        if(currentlyActiveKeys.contains("F11")) {
            if (!player.hasChangedFullscreen) {
                Main.switchFullscreen();
            }
            player.hasChangedFullscreen = true;
        }

        if(currentlyActiveKeys.contains("I")) {
            Main.slowVal ++;
        }

        if(currentlyActiveKeys.contains("K")) {
            if (Main.slowVal > 1) {
                Main.slowVal --;
            }
        }


        if(!player.moving){
            player.setCurrentAnimation(player.idleAnimation);
        }

    }

    @Override
    public void findDirection(){

        if (MovementHandler.getCurrentlyActiveKeys().contains("A")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getLEFT()));
        } else if (MovementHandler.getCurrentlyActiveKeys().contains("D")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getRIGHT()));
        }

        if (MovementHandler.getCurrentlyActiveKeys().contains("W")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getUP()));
        } else if (MovementHandler.getCurrentlyActiveKeys().contains("S")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getDOWN()));
        }
    }
}
