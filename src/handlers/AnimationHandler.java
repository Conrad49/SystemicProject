package handlers;

import Game.Entities.Entity;

public abstract class AnimationHandler{
    protected Entity entity;
    public AnimationHandler(Entity entity){
        this.entity = entity;
    }

    public void animate(){
        setCurrentAnimation();
    }

    protected abstract void setCurrentAnimation();

}
