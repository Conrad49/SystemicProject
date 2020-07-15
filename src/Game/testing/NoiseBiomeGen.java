package Game.testing;

import Game.Tiles.Tile;
import Game.biomes.Biome;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
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
        FileWriter fw;
        try {
            fw = new FileWriter("map.txt");

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

            for(int i = 0; i < Tile.getMapHeight(); i ++) {
                for (int j = 0; j < Tile.getMapWidth(); j++) {
                    double noiseVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount,  zCount) + 1) / 2.0;
                    double tempVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount,  zCount + zOffset) + 1) / 2.0;
                    double moistureVal = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount + 2 * zOffset) + 1) / 2.0;
                    double elevation = (SimplexNoise.octavedNoise(octaves, roughness, 1f, xCount, yCount, zCount+ 3 * zOffset) + 1) / 2.0;

                    String biome = biome(elevation, tempVal, moistureVal);

                    if(i == 183 && j == 172){
                        System.out.println("hi");
                    }

                    switch(biome){
                        case "fields":

                        case "forest":

                        case "valley":

                        case "hills":

                        case "swamp":

                        case "plateau":
                            fw.write("g,");
                            break;
                        case "snow":

                        case "snow peaks":
                            fw.write("o,");
                            break;
                        case "desert":
                            fw.write("a,");
                            break;
                        case "volcano":
                            fw.write("l,");
                            break;
                        case "peaks":
                            fw.write("s,");
                            break;
                        case "frozen lake":
                            fw.write("i,");
                            break;
                        case "lake":

                        case "ocean":
                            fw.write("w,");
                            break;
                        default:
                            System.out.println("eek");
                            fw.write("g,");
                            break;
                    }
                    xCount += inc;
                }
                xCount = 0;
                yCount += inc;
                fw.write("\n");
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
