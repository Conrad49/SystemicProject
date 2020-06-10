package Game;

import Game.Entities.Player;
import Game.Tiles.*;
import Game.testing.NoiseBiomeGen;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * This is where the setup and running happens. Everything from drawing a frame to
 * deciding what each creature does in that from is decided from here.
 */
public class Main extends Application {
    public static void main(String[] args)
    {
        Tile.setMapDimensions(999, 999);
        launch(args);
    }
    private static Stage stage; //
    private static Scene mainScene;
    private static Camera root;
    private static int WIDTH = 512;
    private static int HEIGHT = 256;

    private static HashSet<String> currentlyActiveKeys;

    static final double speed = 5;


    private static Player player = new Player(Color.AQUA, new Rectangle(3000, 3000, 64, 40));



    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setTitle("Event Handling");

        root = new Camera();

        mainScene = new Scene(root);
        stage.setScene(mainScene);

        stage.setFullScreen(true);

        prepareActionHandlers();

        System.out.println("Would you like to generate a new world file?");
        System.out.println("Yes [1]");
        System.out.println("No [0]");

        Scanner reader = new Scanner(System.in);
        int response = reader.nextInt();

        if (response == 1) {
            double rand = Math.random() * 50;
            NoiseBiomeGen.generateWorld();
            //ImprovedNoise.generateNoiseArrayFile(rand);
            makeWorldFromFile();
        } else {
            boolean exists = new File("map.txt").exists();
            if(exists){
                System.out.println("Running with existing map file");
                makeWorldFromFile();
            } else {
                System.out.println("No existing map file");
                System.out.println("Stopping...");
                System.exit(0);
            }
        }


        //Game.Main "game" loop
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
     * Switches the program in and out of fullscreen
     */
    public static void switchFullscreen() {
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }
    /**
     * Adds all the action handlers to the game.
     */
    private static void prepareActionHandlers() {
        // HashSets don't allow duplicate values
        //currentlyActiveKeys = new HashSet<String>();
        currentlyActiveKeys = Player.getCurrentlyActiveKeys();
        mainScene.setOnKeyPressed(e -> {
            currentlyActiveKeys.add(e.getCode().toString());
        });
        mainScene.setOnKeyReleased(e -> {
            player.setHasChangedFullscreen(false);
            currentlyActiveKeys.remove(e.getCode().toString());
        });
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
        //for(Game.Chunk chunk : chunks){
        //    chunk.tick();
        //}

        player.setBoundsBox(new Rectangle(player.getPosX(), player.getPosY(),
                player.getBoundsBox().getWidth(), player.getBoundsBox().getHeight()));

        Tile[] surroundingTiles = new Tile[4];
        Tile[][] allTiles = Tile.getAllTiles();


        // above
        surroundingTiles[0] = allTiles[player.getTileY() - 1][player.getTileX()];
        //surroundingTiles[0].backColor = Color.RED;

        // below
        surroundingTiles[1] = allTiles[player.getTileY() + 1][player.getTileX()];
        //surroundingTiles[1].backColor = Color.BLUE;

        // left
        surroundingTiles[2] = allTiles[player.getTileY()][player.getTileX() - 1];
        //surroundingTiles[2].backColor = Color.PALEGOLDENROD;

        // right
        surroundingTiles[3] = allTiles[player.getTileY()][player.getTileX() + 1];
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
        //GrassTile.setTexture(new Image("/res/GrassTile.png"));
        //StoneTile.setTexture(new Image("/res/StoneTile.png"));

        Image grassImage = new Image("/res/GrassTile.png");
        Image stoneImage = new Image("/res/StoneTile.png");

        Rectangle[][] noiseRectangles = ImprovedNoise.getNoiseArray();
        Tile[][] allTiles = Tile.getAllTiles();
        for (int i = 0; i < Tile.getMapHeight(); i++) {
            for (int j = 0; j < Tile.getMapWidth(); j++) {
                Color color = (Color)noiseRectangles[i][j].getFill();
                if (color.getBlue() < 0.5){
                    GrassTile grassTile = new GrassTile(i * Tile.getWidth(), j * Tile.getWidth());
                    grassTile.setTexture(grassImage);
                } else {
                    StoneTile stoneTile = new StoneTile(i * Tile.getWidth(), j * Tile.getWidth());
                    stoneTile.setTexture(stoneImage);
                }

                /*if(i == 30 || j == 30){
                    new StoneTile(i * Tile.getWidth(), j * Tile.getWidth());
                }else {
                    GrassTile grassTile = new GrassTile(i * Tile.getWidth(), j * Tile.getWidth());
                    //grassTile.setTexture(new Image("/res/Game.Tiles.GrassTile.png"));
                }*/
            }
        }
    }

    private static void makeWorldFromFile(){
        Image grassImage = new Image("/res/GrassTile.png");
        Image sandImage = new Image("/res/SandTile.png");
        Image iceImage = new Image("/res/IceTile.png");
        Image lavaImage = new Image("/res/LavaTile.png");
        Image dirtImage = new Image("/res/DirtTile.png");
        Image stoneImage = new Image("/res/StoneTile.png");
        Image snowImage = new Image("/res/SnowTile.png");
        Image waterImage = new Image("/res/WaterTile.png");

        File file = new File("map.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(file);

            for (int i = 0; i < Tile.getMapHeight(); i++) {
                String[] line = reader.nextLine().split(",");
                for (int j = 0; j < Tile.getMapWidth(); j++) {
                    if(line[j].equals("g")){
                        GrassTile grassTile = new GrassTile(i * Tile.getWidth(), j * Tile.getWidth());
                        grassTile.setTexture(grassImage);
                    } else if(line[j].equals("s")){
                        StoneTile stoneTile = new StoneTile(i * Tile.getWidth(), j * Tile.getWidth());
                        stoneTile.setTexture(stoneImage);
                    }else if(line[j].equals("a")){
                        SandTile sandTile = new SandTile(i * Tile.getWidth(), j * Tile.getWidth());
                        sandTile.setTexture(sandImage);
                    }else if(line[j].equals("i")){
                        IceTile iceTile = new IceTile(i * Tile.getWidth(), j * Tile.getWidth());
                        iceTile.setTexture(iceImage);
                    }else if(line[j].equals("l")){
                        LavaTile lavaTile = new LavaTile(i * Tile.getWidth(), j * Tile.getWidth());
                        lavaTile.setTexture(lavaImage);
                    }else if(line[j].equals("o")){
                        SnowTile snowTile = new SnowTile(i * Tile.getWidth(), j * Tile.getWidth());
                        snowTile.setTexture(snowImage);
                    }else if(line[j].equals("w")){
                        WaterTile waterTile = new WaterTile(i * Tile.getWidth(), j * Tile.getWidth());
                        waterTile.setTexture(waterImage);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
        }


    }
}
