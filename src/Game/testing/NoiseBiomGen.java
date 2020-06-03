package Game.testing;

import Game.ImprovedNoise;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class NoiseBiomGen extends Application {

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
        double tempXCount = 40;
        double tempYCount = 40;
        double moistureXCount = 5;
        double moistureYCount = 5;
        double salinityXCount = 6;
        double salinityYCount = 6;
        zCount += 50;
        double inc = 0.005;

        for(int i = 0; i < root.getHeight(); i ++) {
            for (int j = 0; j < root.getWidth(); j++) {


                 double noiseVal = (SimplexNoise.noise(xCount, yCount, zCount) + 1) / 2.0;

                // double noiseVal = (SimplexNoise.octavedNoise(10, 0.7f, 1f, xCount, yCount) + 1) / 2.0;
                // noise value using octaves ^^

                double tempVal = (SimplexNoise.noise(tempXCount, tempYCount, zCount) + 1) / 2.0;
                double moistureVal = (SimplexNoise.noise(moistureXCount, moistureYCount, zCount) + 1) / 2.0;
                double elevation = (SimplexNoise.noise(salinityXCount, salinityYCount, zCount) + 1) / 2.0;

                /*if(noiseVal > 0.5){
                    pw.setColor(j, i, Color.GREEN);
                } else {
                    pw.setColor(j, i, new Color(noiseVal, noiseVal, noiseVal, 1));
                }*/
                // for octaved noiseVal ^^

                // comment out when doing octaves vvv
                String biome = biome(elevation, tempVal, moistureVal);

                switch(biome){
                    case "OCEAN":
                        pw.setColor(j, i, Color.BLUE);
                        break;
                    case "TEMPERATE_RAIN_FOREST":
                        pw.setColor(j, i, Color.GREEN);
                        break;
                    case "TAIGA":
                        pw.setColor(j, i, Color.RED);
                        break;
                    case "TEMPERATE_DESERT":
                        pw.setColor(j, i, Color.YELLOW);
                    case "SUBTROPICAL_DESERT":
                        pw.setColor(j, i, Color.SKYBLUE);
                    default:
                        pw.setColor(j, i, new Color(noiseVal, noiseVal, noiseVal, 1));
                        break;

                }

                xCount += inc;
                tempXCount += inc;
                moistureXCount += inc;
                salinityXCount += inc;
            }
            xCount = 0;
            tempXCount = 0;
            moistureXCount = 0;
            salinityXCount = 0;

            tempYCount += inc + 0.05;
            moistureYCount += inc + 0.05;
            salinityYCount += inc + 0.05;
            yCount += inc;
        }
        root.getChildren().add(canvas);
    }

    //public String biome(double elevation, double temperature, double moisture) {
    //
    //}
}
