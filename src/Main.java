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
 * This is where the setup and running happens. Everything from drawing a frame to
 * deciding what each creature does in that from is decided from here.
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
        stage.setScene(mainScene);

        stage.setFullScreen(true);

        prepareActionHandlers();
        makeWorld();

         //Main "game" loop
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
        //currentlyActiveKeys = new HashSet<String>();
        currentlyActiveKeys = Player.getCurrentlyActiveKeys();
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

        Chunk[] chunks = player.getSurroundingChunks();
        player.tick();
        //for(Chunk chunk : chunks){
        //    chunk.tick();
        //}

        player.boundsBox = new Rectangle(player.posX, player.posY, player.boundsBox.getWidth(), player.boundsBox.getHeight());

        Tile[] surroundingTiles = new Tile[4];
        Tile[][] allTiles = Tile.getAllTiles();


        // above
        surroundingTiles[0] = allTiles[player.tileY - 1][player.tileX];
        //surroundingTiles[0].backColor = Color.RED;

        // below
        surroundingTiles[1] = allTiles[player.tileY + 1][player.tileX];
        //surroundingTiles[1].backColor = Color.BLUE;

        // left
        surroundingTiles[2] = allTiles[player.tileY][player.tileX - 1];
        //surroundingTiles[2].backColor = Color.PALEGOLDENROD;

        // right
        surroundingTiles[3] = allTiles[player.tileY][player.tileX + 1];
        //surroundingTiles[3].backColor = Color.GRAY;

        for(Tile tile : surroundingTiles){
            player.checkTileCollision(tile);
        }

        root.update();
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
