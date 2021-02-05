package handlers.player;

import Game.Entities.Player;
import handlers.AnimationHandler;
import handlers.KeyHandler;
import handlers.MovementHandler;

public class PlayerAnimationHandler extends AnimationHandler {

    Player player;

    public PlayerAnimationHandler(Player player) {
        super(player);
        this.player = player;
    }

    public void setCurrentAnimation(){

        if (!KeyHandler.getCurrentlyActiveKeys().contains("A")) {
            player.walkLeft.resetCount();
        }

        if (!KeyHandler.getCurrentlyActiveKeys().contains("D")) {
            player.walkRight.resetCount();
        }

        if (!KeyHandler.getCurrentlyActiveKeys().contains("W")) {
            player.walkUp.resetCount();
        }

        if (!KeyHandler.getCurrentlyActiveKeys().contains("S")) {
            player.walkDown.resetCount();
        }

        if(player.moving){
            if(player.getDirection().getX() < 0){
                player.setCurrentAnimation(player.walkLeft);
            } else
            if(player.getDirection().getY() < 0){
                player.setCurrentAnimation(player.walkUp);
            } else
            if(player.getDirection().getY() > 0){
                player.setCurrentAnimation(player.walkDown);
            } else
            if(player.getDirection().getX() > 0){
                player.setCurrentAnimation(player.walkRight);
            } else {
                player.setCurrentAnimation(player.idleAnimation);
            }
        }
    }
}
