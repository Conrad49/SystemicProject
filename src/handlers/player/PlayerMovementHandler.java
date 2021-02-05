package handlers.player;

import Game.Entities.Entity;
import Game.Entities.Player;
import Game.Main;
import Game.testing.Vector;
import handlers.KeyHandler;
import handlers.MovementHandler;

import java.util.HashSet;

public class PlayerMovementHandler extends MovementHandler {

    Player player;

    public PlayerMovementHandler(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public void move(){
        super.move();
        handleKeyPresses();
    }

    public void handleKeyPresses(){

        HashSet<String> currentlyActiveKeys = KeyHandler.getCurrentlyActiveKeys();

        entity.setDirection(new Vector(0, 0));
        boolean isMoving = currentlyActiveKeys.contains("A") || currentlyActiveKeys.contains("D") || currentlyActiveKeys.contains("W") || currentlyActiveKeys.contains("S");

        if (isMoving) {
            player.setMoving(true);
            //speed = maxSpeed;
            findDirection();
        }

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

    public void findDirection(){

        if (KeyHandler.getCurrentlyActiveKeys().contains("A")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getLEFT()));
        } else if (KeyHandler.getCurrentlyActiveKeys().contains("D")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getRIGHT()));
        }

        if (KeyHandler.getCurrentlyActiveKeys().contains("W")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getUP()));
        } else if (KeyHandler.getCurrentlyActiveKeys().contains("S")) {
            entity.getDirection().setToVec(entity.getDirection().add(Entity.getDOWN()));
        }
    }


}
