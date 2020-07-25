package Game;

import Game.Entities.Entity;
import Game.Entities.Player;
import Game.Tiles.*;
import Game.plants.SingleTallGrass;
import Game.testing.NoiseBiomeGen;
import Game.testing.Vector;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This is where the setup and running happens. Everything from drawing a frame to
 * deciding what each creature does in that from is decided from here.
 */
public class Main extends Application {

    public static int numOfFrames = 0;

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
    private static long last_time = System.nanoTime();
    private static int delta_time = 0;
    public static boolean colliding;

    private static HashSet<String> currentlyActiveKeys;

    static final double speed = 5;


    private static Player player = new Player(Color.AQUA, new Rectangle(3000, 3000, 64, 32));



    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        stage.setTitle("Systemic Project");

        root = new Camera();

        mainScene = new Scene(root);
        stage.setScene(mainScene);

        //stage.setFullScreen(true);

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
                numOfFrames ++;
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

        // a calculation for the time between updates taken from:
        // https://gamedev.stackexchange.com/questions/111741/calculating-delta-time
        // don't use for now VVV
        long time = System.nanoTime();
        delta_time = (int) ((time - last_time) / 1000000);
        last_time = time;

        Chunk[] chunks = player.getSurroundingChunks();

        //for(Game.Chunk chunk : chunks){
        //    chunk.tick();
        //}

        player.setBoundsBox(new Rectangle(player.getPosX(), player.getPosY(),
                player.getBoundsBox().getWidth(), player.getBoundsBox().getHeight()));

        Tile[] surroundingTiles = new Tile[9];
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

        surroundingTiles[4] = allTiles[player.getTileY()][player.getTileX()];

        surroundingTiles[5] = allTiles[player.getTileY() - 1][player.getTileX() - 1];
        surroundingTiles[6] = allTiles[player.getTileY() - 1][player.getTileX() + 1];
        surroundingTiles[7] = allTiles[player.getTileY() + 1][player.getTileX() + 1];
        surroundingTiles[8] = allTiles[player.getTileY() + 1][player.getTileX() - 1];

        colliding = false;
        Group group = new Group();
        for(Tile tile : surroundingTiles){
            Entity.drawContactPoint();
            Rectangle box = new Rectangle(tile.getBoundsBox().getX(), tile.getBoundsBox().getY(), tile.getBoundsBox().getWidth(), tile.getBoundsBox().getHeight());
            double[] coords = shift(box.getX(), box.getY());
            box.setX(coords[0]);
            box.setY(coords[1]);
            group.getChildren().add(box);
            if (tile.isSolid()) {
                if (player.movingRectVcRect(player, tile.getBoundsBox())) {


                    player.setVelocity(player.getContactNormal().multiply(new Vector(Math.abs(player.getVelocity().getX()), Math.abs(player.getVelocity().getY()))).multiply(1-player.getTime()));

                    /*player.setVelocity(new Vector(0, 0));
                    Player.setMoving(false);
                    System.out.println(player.getVelocity().getX());*/
                    System.out.println(1-player.getTime());
                    colliding = true;
                }
            }
        }
        Camera.setGUIGroup(group);

        player.tick();
        tileTick();

        root.update();
    }

    private static void tileTick(){
        //growth rate
        if(numOfFrames % 600 == 0) {
            root.doUpdateAll();
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            int loadWidth = 50;
            for (int i = 0; i < loadWidth; i++) {
                for (int j = 0; j < loadWidth; j++) {
                    int tx = ((int) player.getPosX() / Tile.getTileWidth()) - loadWidth / 2 + j;
                    int ty = ((int) player.getPosY() / Tile.getTileWidth()) - loadWidth / 2 + i;

                    Tile t = Tile.getAllTiles()[ty][tx];
                    if (t.getPlants().size() > 0) {
                        if (rand.nextInt(10) == 0) {
                            t.tick();
                        }
                    }
                }
            }
        }
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
                    GrassTile grassTile = new GrassTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                    grassTile.setTexture(grassImage);
                } else {
                    StoneTile stoneTile = new StoneTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
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

            ThreadLocalRandom rand = ThreadLocalRandom.current();

            for (int i = 0; i < Tile.getMapHeight(); i++) {
                String[] line = reader.nextLine().split(",");
                for (int j = 0; j < Tile.getMapWidth(); j++) {
                    if(line[j].equals("g")){
                        GrassTile grassTile = new GrassTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        grassTile.setTexture(grassImage);

                        // For testing purposes this should add tall grass to every 4th grass tile
                        int count = rand.nextInt(4, 6);
                        for (int k = 0; k < count; k++) {
                            grassTile.addPlant(new SingleTallGrass(
                                    rand.nextInt(0, SingleTallGrass.getMaxHealth()),
                                    rand.nextInt(0, SingleTallGrass.getMaxEnergy()),
                                    i * Tile.getTileWidth() + rand.nextInt(Tile.getTileWidth()),
                                    j * Tile.getTileWidth() + rand.nextInt(Tile.getTileWidth())
                            ));
                        }
                    }else if(line[j].equals("s")){
                        StoneTile stoneTile = new StoneTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        stoneTile.setTexture(stoneImage);
                    }else if(line[j].equals("a")){
                        SandTile sandTile = new SandTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        sandTile.setTexture(sandImage);
                    }else if(line[j].equals("i")){
                        IceTile iceTile = new IceTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        iceTile.setTexture(iceImage);
                    }else if(line[j].equals("l")){
                        LavaTile lavaTile = new LavaTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        lavaTile.setTexture(lavaImage);
                    }else if(line[j].equals("o")){
                        SnowTile snowTile = new SnowTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        snowTile.setTexture(snowImage);
                    }else if(line[j].equals("w")){
                        WaterTile waterTile = new WaterTile(i * Tile.getTileWidth(), j * Tile.getTileWidth());
                        waterTile.setTexture(waterImage);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
        }
    }

    /**
     * returns the time between frames in milliseconds?
     */
    public static int getDelta_time() {
        return delta_time;
    }

    private static double[] shift(double x, double y){
        int px = (int)Math.round(Main.getPlayer().getPosX());
        int py = (int)Math.round(Main.getPlayer().getPosY());
        int halfWidth = (int)root.getWidth() / 2;
        int halfHeight = (int)root.getHeight() / 2;

        x = x - root.getScreenCenterX() + halfWidth;
        y = y - root.getScreenCenterY() + halfHeight;

        return new double[] {x, y};
    }

}
