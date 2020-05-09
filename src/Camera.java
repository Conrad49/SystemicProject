import javafx.scene.canvas.GraphicsContext;

public class Camera {
    Tile[][] visibleTiles = new Tile[10][10];
    static int cameraWidth = 36;

    public Camera(Tile[][] visibleTiles){
        this.visibleTiles = visibleTiles;


        //TODO: if other entities are on the tiles that are being rendered, draw them to
    }

    public void render(GraphicsContext g){
        renderVisibleTiles(g);
    }


    public void renderVisibleTiles(GraphicsContext g){

        //TODO: replace 10 with calculated value

        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                // visibleTiles[j][i].render(g);
            }
        }
    }
}
