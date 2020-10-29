package Game.testing;

import Game.Chunk;
import Game.Entities.Entity;
import Game.Tiles.*;
import Game.World;
import Game.biomes.Biome;
import Game.plants.Plant;
import Game.plants.SingleTallGrass;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NoiseBiomeGen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1200, 700);

        testImage(root);
        root.setOnMousePressed(e -> {
            testImage(root);
        });

        stage.setScene(scene);
        stage.show();



    }

    static double zCount = ThreadLocalRandom.current().nextInt(0, 100);

    public static void testImage(Pane root){
        root.getChildren().clear();
        Canvas canvas = new Canvas(root.getWidth(), root.getHeight());
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();

        double xCount = 0;
        double yCount = 0;
        zCount += 50;
        double zOffset = 10;
        double inc = 0.005;
        float roughness = 0.55f;
        int octaves = 10;

        new Biome("frozen lake", "low", "low", "low");
        new Biome("frozen lake2", "low", "medium", "low");
        new Biome("lake", "medium", "low", "low");
        new Biome("lake2", "medium", "medium", "low");
        new Biome("swamp", "high", "low", "low");
        new Biome("swamp2", "high", "medium", "low");
        new Biome("ocean", "high", "high", "low");
        new Biome("ocean2", "medium", "high", "low");
        new Biome("ocean3", "low", "high", "low");

        new Biome("fields", "medium", "low", "medium");
        new Biome("fields2", "medium", "medium", "medium");
        new Biome("snow", "low", "medium", "medium");
        new Biome("snow2", "low", "low", "medium");
        new Biome("forest", "medium", "high", "medium");
        new Biome("jungle", "high", "high", "medium");
        new Biome("grasslands", "high", "medium", "medium");
        new Biome("desert", "high", "low", "medium");
        new Biome("snow forest", "low", "high", "medium");

        new Biome("volcano", "high", "low", "high");
        new Biome("peaks", "low", "low", "high");
        new Biome("peaks2", "medium", "low", "high");
        new Biome("peaks3", "medium", "medium", "high");
        new Biome("peaks4", "high", "medium", "high");
        new Biome("peaks5", "medium", "high", "high");
        new Biome("peaks6", "high", "high", "high");
        new Biome("snow peaks", "low", "high", "high");
        new Biome("snow peaks2", "low", "medium", "high");

        for(int i = 0; i < root.getHeight(); i ++) {
            for (int j = 0; j < root.getWidth(); j++) {
                double noiseVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount,  zCount) + 1) / 2.0;
                double tempVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount,  zCount + zOffset) + 1) / 2.0;
                double moistureVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + 2 * zOffset) + 1) / 2.0;
                double elevation = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount+ 3 * zOffset) + 1) / 2.0;

                String biome = biome(elevation, tempVal, moistureVal);

                switch(biome){
                    case "fields":
                        pw.setColor(j, i, Color.LIMEGREEN);
                        break;
                    case "snow":
                        pw.setColor(j, i, Color.GOLDENROD);
                        break;
                    case "fields2":
                        pw.setColor(j, i, Color.RED);
                        break;
                    case "snow2":
                        pw.setColor(j, i, Color.DARKGREY);
                        break;
                    case "grasslands":
                        pw.setColor(j, i, Color.WHEAT);
                        break;
                    case "desert":
                        pw.setColor(j, i, Color.YELLOW);
                        break;
                    case "forest":
                        pw.setColor(j, i, Color.GREEN);
                        break;
                    case "jungle":
                        pw.setColor(j, i, Color.DARKGREEN);
                        break;
                    case "snow forest":
                        pw.setColor(j, i, Color.PALEGREEN);
                        break;
                    case "volcano":
                        pw.setColor(j, i, Color.ORANGE);
                        break;
                    case "peaks":
                        pw.setColor(j, i, Color.BROWN);
                        break;
                    case "peaks2":
                        pw.setColor(j, i, Color.DARKGRAY);
                        break;
                    case "peaks3":
                        pw.setColor(j, i, Color.DARKSALMON);
                        break;
                    case "peaks4":
                        pw.setColor(j, i, Color.LIGHTPINK);
                        break;
                    case "peaks5":
                        pw.setColor(j, i, Color.LIGHTSALMON);
                        break;
                    case "peaks6":
                        pw.setColor(j, i, Color.CORAL);
                        break;
                    case "snow peaks":
                        pw.setColor(j, i, Color.SILVER);
                        break;
                    case "snow peaks2":
                        pw.setColor(j, i, Color.LAVENDER);
                        break;
                    case "frozen lake":
                        pw.setColor(j, i, Color.LIGHTBLUE);
                        break;
                    case "lake":
                        pw.setColor(j, i, Color.BLUE);
                        break;
                    case "swamp":
                        pw.setColor(j, i, Color.SEAGREEN);
                        break;
                    case "ocean":
                        pw.setColor(j, i, Color.DARKBLUE);
                        break;
                    case "frozen lake2":
                        pw.setColor(j, i, Color.LIME);
                        break;
                    case "lake2":
                        pw.setColor(j, i, Color.PURPLE);
                        break;
                    case "swamp2":
                        pw.setColor(j, i, Color.MAROON);
                        break;
                    case "ocean2":
                        pw.setColor(j, i, Color.PINK);
                        break;
                    case "ocean3":
                        pw.setColor(j, i, Color.TURQUOISE);
                        break;
                    default:
                        System.out.println("eek");
                        pw.setColor(j, i, new Color(noiseVal, noiseVal, noiseVal, 1));
                        break;



                }

                xCount += inc;
            }
            xCount = 0;
            yCount += inc;
        }
        root.getChildren().add(canvas);
    }

    public static String biome(double elevation, double temperature, double moisture) {

        String elevationRange = findRange(elevation);
        String temperatureRange = findRange(temperature);
        String moistureRange = findRange(moisture);

        for (Biome b : Biome.getAllBiomes()) {
            if(b.canSpawn(elevationRange, temperatureRange, moistureRange)){
                return b.getName();
            }
        }

        return "no biome found";
    }

    /**
     * Takes an exact value from 0-1 and finds if it is in the high low or medium
     * range of numbers and returns the range as a lowercase string.
     */
    private static String findRange(double exactValue){
        double low = 0.43;
        double high = 0.57;

        String range;
        if(exactValue <= low){
            range = "low";
        }else if(exactValue >= high){
            range = "high";
        }else{
            range = "medium";
        }

        return range;
    }


    /**
     * Generates a new text document using this form of biome based generation
     */
    public static void generateWorld() {

        double xCount = 0;
        double yCount = 0;
        zCount += 50;
        double zOffset = 10;
        double inc = 0.005;
        float roughness = 0.55f;
        int octaves = 10;
        int chunkCount = 0;

        new Biome("frozen lake", "low", "low", "low");
        new Biome("lake", "high", "low", "low");
        new Biome("swamp", "medium", "low", "low");
        new Biome("ocean", "low medium high", "high", "low");
        new Biome("valley", "low medium high", "medium", "low");

        new Biome("fields", "medium", "low medium", "medium");
        new Biome("snow", "low", "low medium", "medium");
        new Biome("forest", "low medium high", "high", "medium");
        new Biome("desert", "high", "low medium", "medium");

        new Biome("volcano", "high", "low", "high");
        new Biome("peaks", "high", "medium", "high");
        new Biome("peaks", "medium", "low", "high");
        new Biome("hills", "low medium high", "high", "high");
        new Biome("plateau", "medium", "medium", "high");
        new Biome("snow peaks", "low", "low medium", "high");

        ThreadLocalRandom rand = ThreadLocalRandom.current();

        Image grassImage = new Image("/res/GrassTile.png");
        Image sandImage = new Image("/res/SandTile.png");
        Image iceImage = new Image("/res/IceTile.png");
        Image lavaImage = new Image("/res/LavaTile.png");
        Image dirtImage = new Image("/res/DirtTile.png");
        Image stoneImage = new Image("/res/StoneTile.png");
        Image snowImage = new Image("/res/SnowTile.png");
        Image waterImage = new Image("/res/WaterTile.png");

        int size = Chunk.getTileSize();

        for (int chunkY = 0; chunkY < World.getWorldChunkHeight(); chunkY++) {
            for (int chunkX = 0; chunkX < World.getWorldChunkWidth(); chunkX++) {
                // new chunk
                generateChunk(chunkX, chunkY);
                /*xCount = chunkX * inc * size;
                yCount = chunkY * inc * size;

                Tile[][] tiles = new Tile[size][size];
                ArrayList<Plant> plants = new ArrayList<>();

                for (int j = 0; j < size; j++) {
                    for (int i = 0; i < size; i++) {
                        double tempVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + zOffset) + 1) / 2.0;
                        double moistureVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + 2 * zOffset) + 1) / 2.0;
                        double elevation = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + 3 * zOffset) + 1) / 2.0;

                        String biome = biome(elevation, tempVal, moistureVal);

                        Tile tile;

                        double test = (chunkX * size + i) * Tile.getTileWidth();
                        double test2 = (chunkY * size + j) * Tile.getTileWidth();

                        switch (biome) {
                            case "fields":

                            case "forest":

                            case "valley":

                            case "hills":

                            case "plateau":
                                tile = new GrassTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/GrassTile.png");

                                // For testing purposes this should add tall grass to every 4th grass tile
                                int count = rand.nextInt(4, 6);
                                for (int k = 0; k < count; k++) {
                                    Plant p = new SingleTallGrass(
                                            rand.nextInt(0, SingleTallGrass.getMaxHealth()),
                                            rand.nextInt(0, SingleTallGrass.getMaxEnergy()),
                                            (chunkX * size + i) * Tile.getTileWidth() + rand.nextInt(Tile.getTileWidth()),
                                            (chunkY * size + j) * Tile.getTileWidth() + rand.nextInt(Tile.getTileWidth()));

                                    tile.addPlant(p);
                                    plants.add(p);
                                }
                                break;
                            case "snow":

                            case "snow peaks":
                                tile = new SnowTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/SnowTile.png");
                                break;
                            case "desert":
                                tile = new SandTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/SandTile.png");
                                break;
                            case "volcano":
                                tile = new LavaTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/LavaTile.png");
                                break;
                            case "peaks":
                                tile = new StoneTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/StoneTile.png");
                                break;
                            case "frozen lake":
                                tile = new IceTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/IceTile.png");
                                break;
                            case "swamp":

                            case "lake":

                            case "ocean":
                                tile = new WaterTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());
                                tile.setTexture("/res/WaterTile.png");
                                break;
                            default:
                                System.out.println("No biome case found");
                                tile = new GrassTile((chunkX * size + i) * Tile.getTileWidth(), (chunkY * size + j) * Tile.getTileWidth());;
                                break;
                        }

                        tiles[i][j] = tile;
                        xCount += inc;
                    }
                    xCount = chunkX * inc * size;
                    yCount += inc;
                }
                //chunk complete
                Chunk c = new Chunk(new ArrayList<Entity>(), tiles, plants, chunkX, chunkY);
                chunkCount++;
                //System.out.println(chunkCount);*/
            }
            //System.out.println(chunkY);
        }
    }




    public static void generateChunk(int x, int y) {

        double xCount = 0;
        double yCount = 0;
        zCount += 50;
        double zOffset = 10;
        double inc = 0.005;
        float roughness = 0.55f;
        int octaves = 10;

        new Biome("frozen lake", "low", "low", "low");
        new Biome("lake", "high", "low", "low");
        new Biome("swamp", "medium", "low", "low");
        new Biome("ocean", "low medium high", "high", "low");
        new Biome("valley", "low medium high", "medium", "low");

        new Biome("fields", "medium", "low medium", "medium");
        new Biome("snow", "low", "low medium", "medium");
        new Biome("forest", "low medium high", "high", "medium");
        new Biome("desert", "high", "low medium", "medium");

        new Biome("volcano", "high", "low", "high");
        new Biome("peaks", "high", "medium", "high");
        new Biome("peaks", "medium", "low", "high");
        new Biome("hills", "low medium high", "high", "high");
        new Biome("plateau", "medium", "medium", "high");
        new Biome("snow peaks", "low", "low medium", "high");

        ThreadLocalRandom rand = ThreadLocalRandom.current();

        Image grassImage = new Image("/res/GrassTile.png");
        Image sandImage = new Image("/res/SandTile.png");
        Image iceImage = new Image("/res/IceTile.png");
        Image lavaImage = new Image("/res/LavaTile.png");
        Image dirtImage = new Image("/res/DirtTile.png");
        Image stoneImage = new Image("/res/StoneTile.png");
        Image snowImage = new Image("/res/SnowTile.png");
        Image waterImage = new Image("/res/WaterTile.png");

        int tileSize = Chunk.getTileSize();

        // new chunk

                xCount = x * inc * tileSize;
                yCount = y * inc * tileSize;

                Tile[][] tiles = new Tile[tileSize][tileSize];
                ArrayList<Plant> plants = new ArrayList<>();

                for (int j = 0; j < tileSize; j++) {
                    for (int i = 0; i < tileSize; i++) {
                        double tempVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + zOffset) + 1) / 2.0;
                        double moistureVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + 2 * zOffset) + 1) / 2.0;
                        double elevation = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + 3 * zOffset) + 1) / 2.0;

                        String biome = biome(elevation, tempVal, moistureVal);

                        Tile tile;

                        double test = (x * tileSize + i) * Tile.getTileWidth();
                        double test2 = (y * tileSize + j) * Tile.getTileWidth();

                        switch (biome) {
                            case "fields":

                            case "forest":

                            case "valley":

                            case "hills":

                            case "plateau":
                                tile = new GrassTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/GrassTile.png");

                                // For testing purposes this should add tall grass to every 4th grass tile
                                int count = rand.nextInt(4, 6);
                                for (int k = 0; k < count; k++) {
                                    Plant p = new SingleTallGrass(
                                            rand.nextInt(0, SingleTallGrass.getMaxHealth()),
                                            rand.nextInt(0, SingleTallGrass.getMaxEnergy()),
                                            (x * tileSize + i) * Tile.getTileWidth() + rand.nextInt(Tile.getTileWidth()),
                                            (y * tileSize + j) * Tile.getTileWidth() + rand.nextInt(Tile.getTileWidth()));

                                    tile.addPlant(p);
                                    plants.add(p);
                                }
                                break;
                            case "snow":

                            case "snow peaks":
                                tile = new SnowTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/SnowTile.png");
                                break;
                            case "desert":
                                tile = new SandTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/SandTile.png");
                                break;
                            case "volcano":
                                tile = new LavaTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/LavaTile.png");
                                break;
                            case "peaks":
                                tile = new StoneTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/StoneTile.png");
                                break;
                            case "frozen lake":
                                tile = new IceTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/IceTile.png");
                                break;
                            case "swamp":

                            case "lake":

                            case "ocean":
                                tile = new WaterTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());
                                tile.setTexture("/res/WaterTile.png");
                                break;
                            default:
                                System.out.println("No biome case found");
                                tile = new GrassTile((x * tileSize + i) * Tile.getTileWidth(), (y * tileSize + j) * Tile.getTileWidth());;
                                break;
                        }

                        tiles[i][j] = tile;
                        xCount += inc;
                    }
                    xCount = x * inc * tileSize;
                    yCount += inc;
                }
                //chunk complete
                Chunk c = new Chunk(new ArrayList<Entity>(), tiles, plants, x, y);
                // makes chunk in game memory and in a file but cannot just make a file ^

            System.out.println(x + ", " + y);

    }



}
