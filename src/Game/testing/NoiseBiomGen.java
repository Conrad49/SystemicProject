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


                // test 1 D;
                double noiseVal = (SimplexNoise.noise(xCount, yCount, zCount) + 1) / 2.0;
                double tempVal = (SimplexNoise.noise(tempXCount, tempYCount, zCount + 1) + 1) / 2.0;
                double moistureVal = (SimplexNoise.noise(moistureXCount, moistureYCount, zCount + 2) + 1) / 2.0;
                double salinity = (SimplexNoise.noise(salinityXCount, salinityYCount, zCount + 3) + 1) / 2.0;


                if(tempVal > 0.3 && salinity < 0.5 && moistureVal > 0.5){
                    pw.setColor(j, i, Color.ORANGE);
                } else {
                    pw.setColor(j, i, new Color(noiseVal, noiseVal, noiseVal, 1));
                }


                /*if (noiseVal < 0.2) {
                    pw.setColor(j, i, Color.RED);
                }else if(noiseVal < 0.25){
                    pw.setColor(j, i, Color.ORANGERED);
                }else if(noiseVal < 0.3){
                    pw.setColor(j, i, Color.ORANGE);
                }else if(noiseVal < 0.35){
                    pw.setColor(j, i, Color.GOLD);
                } else if(noiseVal < 0.4){
                    //pw.setColor(j, i, Color.YELLOW);
                } else if(noiseVal < 0.45){
                    //pw.setColor(j, i, Color.YELLOWGREEN);
                }else if(noiseVal < 0.5){
                    //pw.setColor(j, i, Color.GREEN);
                }else if(noiseVal < 0.55){
                    //pw.setColor(j, i, Color.DARKGREEN);
                }else if(noiseVal < 0.6){
                    //pw.setColor(j, i, Color.BLUE);
                }else if(noiseVal < 0.65){
                    pw.setColor(j, i, Color.SKYBLUE);
                }else if(noiseVal < 0.7){
                    pw.setColor(j, i, Color.DARKBLUE);
                }else if(noiseVal < 0.75){
                    pw.setColor(j, i, Color.MAGENTA);
                }else if(noiseVal < 0.8){
                    pw.setColor(j, i, Color.PURPLE);
                }*/



                /*
                // test 2 :/
                double noiseVal = (ImprovedNoise.noise(count, yCount, zCount) + 1) / 2.0;
                if (noiseVal < 0.44) {
                    pw.setColor(j, i, Color.RED);
                }else if(noiseVal < 0.56){
                    pw.setColor(j, i, Color.GREEN);
                }else if(noiseVal < 1){
                    pw.setColor(j, i, Color.BLUE);
                }
                */

/*
                // Test 3 start :D
                double r = (ImprovedNoise.noise(count, yCount, zCount) + 1) / 2.0;
                double g = (ImprovedNoise.noise(count, yCount, zCount + 10) + 1) / 2.0;
                double b = (ImprovedNoise.noise(count, yCount, zCount + 20) + 1) / 2.0;

                // test 4 is test 3 but with the next 3 lines added
                //r = (double)((int)(r * 10)) / 10.0;
                //g = (double)((int)(g * 10)) / 10.0;
                //b = (double)((int)(b * 10)) / 10.0;



                // test 5 start requires test 3 enabled
                // maybe?
                boolean bigR = false;
                boolean bigG = false;
                boolean bigB = false;

                double toBeBig = 0.5;
                if(r > toBeBig){
                    bigR = true;
                }
                if(g > toBeBig){
                    bigG = true;
                }
                if(b > toBeBig){
                    bigB = true;
                }

                /*
                // test 6
                if(bigR && bigG && bigB){
                    r = 1;
                    g = 0;
                    b = 0;
                }else if(!bigR && !bigG && !bigB){
                    r = 0;
                    g = 0;
                    b = 1;
                }else{
                    if(bigR){
                        g = 0;
                        r = 1;
                        if(bigB){
                            r = 0;
                        }else{
                            b = 0;
                        }
                    }
                    if(bigG){
                        b = 0;
                        g = 1;
                        if(bigR){
                            g = 0;
                        }else{
                            r = 0;
                        }
                    }
                    if(bigB){
                        r = 0;
                        b = 1;
                        if(bigG){
                            b = 0;
                        }else{
                            g = 0;
                        }
                    }
                }
                if(r != 1 && r != 0 || g != 1 && g != 0 || b != 1 && b != 0)
                System.out.println(bigR + " " + bigG + " " + bigB);
                // end test 6


                 */
/*
                // comment out if monstrosity for test 6 to work
                if(bigR || bigG || bigB) {
                    if (bigR) {
                        r = 1;
                        if (!bigG) {
                            g = 0;
                        } else {
                            g = 1;
                        }
                        if (!bigB) {
                            b = 0;
                        } else {
                            b = 1;
                        }
                    } else if (bigG || bigB) {
                        r = 0;
                        if (!bigG) {
                            g = 0;
                        } else {
                            g = 1;
                        }
                        if (!bigB) {
                            b = 0;
                        } else {
                            b = 1;
                        }
                    }
                }else{
                    r = 0;
                    g = 0;
                    b = 0;
                }
                // test 5 end




                Color c = new Color(r, g, b, 1);
                pw.setColor(j, i, c);
                // test 3 end
*/


                xCount += inc;
                tempXCount += inc;
                moistureXCount += inc;
                salinityXCount += inc;
            }
            xCount = 0;
            tempXCount = 0;
            moistureXCount = 0;
            salinityXCount = 0;

            tempYCount += inc + 0.1;
            moistureYCount += inc + 0.1;
            salinityYCount += inc + 0.1;
            yCount += inc;
        }
        root.getChildren().add(canvas);
    }
}
