package Game;


import javafx.scene.image.Image;

import java.io.Serializable;

public class SerializableImage extends Image implements Serializable {
    public SerializableImage(String url) {
        super(url);
    }

    public SerializableImage(Image i){
        this(i.getUrl());
    }
}
