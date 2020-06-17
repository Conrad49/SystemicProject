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


    /**
     * gets the image of the animation object that this method is called on. repeats frames to slow down animation and
     * resets index as necessary
     */
    public Image getImage(){

        // slows down animation to approximately 10 fps
        if (Main.numOfFrames % 6 == 0 || this.count == -1) {
            this.count ++;
        }
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
