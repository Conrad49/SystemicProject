import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;

public class Main extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    private static Scene mainScene;
    private static int WIDTH = 512;
    private static int HEIGHT = 256;

    private static HashSet<String> currentlyActiveKeys;

    static final double speed = 5;


    private static Player player = new Player(Color.AQUA, new Rectangle(100, 100, 50, 70));

    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("Event Handling");

        Group root = new Group();

        mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        prepareActionHandlers();

        /**
         * Main "game" loop
         */
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                tickAndRender();
            }
        }.start();

        mainStage.show();

    }

    private static void prepareActionHandlers() {
        // HashSets don't allow duplicate values
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(e -> currentlyActiveKeys.add(e.getCode().toString()));
        mainScene.setOnKeyReleased(e -> currentlyActiveKeys.remove(e.getCode().toString()));

    }

    public void renderTiles(){

        for(int i = 0; i < Tile.getAllTiles().length; i ++){
            for(int j = 0; j < Tile.getAllTiles().length; j ++){
                Tile[][] tiles = Tile.getAllTiles();
                Tile tileToBeRendered = tiles[j][i];
            }
        }
    }


    private static void tickAndRender() {
        int topLeftX = player.tileX - Camera.cameraWidth/2;
        int topLeftY = player.tileY - Camera.cameraWidth/2;
        Tile[][] allTiles = Tile.getAllTiles();
        Tile[][] visibleTiles = new Tile[Camera.cameraWidth][Camera.cameraWidth];

        if (topLeftX >= 0 && topLeftY >= 0) {
            for(int i = topLeftY; i < topLeftY + Camera.cameraWidth; i ++){
                for(int j = topLeftX; j < topLeftX + Camera.cameraWidth; j ++){
                    visibleTiles[j - topLeftX][i - topLeftY] = allTiles[j][i];
                }
            }
        }

        Camera camera = new Camera(visibleTiles);

        // camera.renderVisibleTiles(graphicsContext);

        if (currentlyActiveKeys.contains("A"))
        {
            player.posX -= speed;
        }

        if (currentlyActiveKeys.contains("D"))
        {
            player.posX += speed;
        }

        if (currentlyActiveKeys.contains("W"))
        {
            player.posY -= speed;
        }

        if (currentlyActiveKeys.contains("S"))
        {
            player.posY += speed;
        }
    }
}
