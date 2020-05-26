package Game;

import javafx.scene.image.Image;

public class Animation {
    Image[] animation;
    int count = -1;

    public Animation() {

    }

    public Animation(Image[] animation) {
        this.animation = animation;
    }

    public void resetCount(){
        this.count = -1;
    }

    public Image getImage(){
        this.count ++;
        if (this.count < this.animation.length) {
            return this.animation[count];
        } else {
            this.count = 0;
            return this.animation[count];
        }
    }

    public void setImages(Image [] images){
        this.animation = images;
    }
}
