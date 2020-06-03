package Game.testing;

import Game.biomes.Biome;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    double zCount = ThreadLocalRandom.current().nextInt(0, 100);

    public void testImage(Pane root){
        root.getChildren().clear();
        Canvas canvas = new Canvas(root.getWidth(), root.getHeight());
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();

        double xCount = 0;
        double yCount = 0;
        zCount += 50;
        double zOffset = 10;
        double inc = 0.005;
        float roughness = 0.55f;
        int octaves = 20;

        new Biome("frozen lake", "low", "low medium", "low");
        new Biome("lake", "medium", "low medium", "low");
        new Biome("swamp", "high", "low medium", "low");
        new Biome("ocean", "low medium high", "high", "low");

        new Biome("fields", "medium", "low medium", "medium");
        new Biome("snow", "low", "low medium", "medium");
        new Biome("forest", "medium", "high", "medium");
        new Biome("jungle", "high", "high", "medium");
        new Biome("grasslands", "high", "medium", "medium");
        new Biome("desert", "high", "low", "medium");
        new Biome("snow forest", "low", "high", "medium");

        new Biome("volcano", "high", "low", "high");
        new Biome("peaks", "low medium", "low", "high");
        new Biome("peaks", "medium high", "medium high", "high");
        new Biome("snow peaks", "low", "medium high", "high");

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
                        pw.setColor(j, i, Color.GRAY);
                        break;
                    case "snow peaks":
                        pw.setColor(j, i, Color.SILVER);
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

    public String biome(double elevation, double temperature, double moisture) {

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
    private String findRange(double exactValue){
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
}
