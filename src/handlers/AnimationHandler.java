package handlers;

import Game.Entities.Entity;

public class AnimationHandler{
    public Entity entity;
    public AnimationHandler(Entity entity){
        this.entity = entity;
    }

    public void animate(){
        setCurrentAnimation();
    }

    public void setCurrentAnimation(){
        //logic for switching what is the entity's currently playing animation
        //reset animation counters if necessary
    }

}
