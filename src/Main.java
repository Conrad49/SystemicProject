import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;

/**
 * This is where all the running action takes place.
 */
public class Main extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    private static Scene mainScene;
    private static Camera root;
    private static int WIDTH = 512;
    private static int HEIGHT = 256;

    private static HashSet<String> currentlyActiveKeys;

    static final double speed = 5;


    private static Player player = new Player(Color.AQUA, new Rectangle(3000, 3000, 50, 70));

    @Override
    public void start(Stage stage) {
        stage.setTitle("Event Handling");

        root = new Camera();

        mainScene = new Scene(root);

        stage.setFullScreen(true);

        stage.setScene(mainScene);

        prepareActionHandlers();
        makeWorld();

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

        stage.show();

    }

    /**
     * Adds all the action handlers to the game.
     */
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

    /**
     * A tick is what needs to be done in a frame like plant growth or creature ai choices and movement.
     * This method does all of that and everything related to displaying that frame.
     */
    private static void tickAndRender() {
        // camera.renderVisibleTiles(graphicsContext);

        if (currentlyActiveKeys.contains("A")) {
            player.posX -= speed;
            player.tileX = (int)player.posX / Tile.width;
        }

        if (currentlyActiveKeys.contains("D")) {
            player.posX += speed;
            player.tileX = (int)player.posX / Tile.width;
        }

        if (currentlyActiveKeys.contains("W")) {
            player.posY -= speed;
            player.tileY = (int)player.posY / Tile.width;
        }

        if (currentlyActiveKeys.contains("S")) {
            player.posY += speed;
            player.tileY = (int)player.posY / Tile.width;
        }

<<<<<<< HEAD
        root.update();
=======
        player.boundsBox = new Rectangle(player.posX, player.posY, player.boundsBox.getWidth(), player.boundsBox.getHeight());

        Tile[] surroundingTiles = new Tile[4];
        Tile[][] allTiles = Tile.getAllTiles();


        // above
        surroundingTiles[0] = allTiles[player.tileX][player.tileY - 1];

        // below
        surroundingTiles[1] = allTiles[player.tileX][player.tileY + 1];

        // left
        surroundingTiles[2] = allTiles[player.tileY][player.tileX - 1];

        // right
        surroundingTiles[3] = allTiles[player.tileY][player.tileX + 1];

        for(Tile tile : surroundingTiles){
            player.checkTileCollision(tile);
        }

>>>>>>> e34059a128722b920f50792e8418e82fc86c34b0
    }

    public static Player getPlayer(){
        return player;
    }

    /**
     * very basic world generation method.
     */
    private static void makeWorld(){
        Tile[][] allTiles = Tile.getAllTiles();
        for (int i = 0; i < 999; i++) {
            for (int j = 0; j < 999; j++) {
                if(i == 30 || j == 30){
                    new StoneTile(i * Tile.width, j * Tile.width);
                }else {
                    new GrassTile(i * Tile.width, j * Tile.width);
                }
            }
        }
    }
}
