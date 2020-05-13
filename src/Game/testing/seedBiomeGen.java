package Game.testing;
/**
 * A generator based on placing seeds then spreading or growing till they meet lad claimed by another biome
 */

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class seedBiomeGen extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1200, 700);

        test1(root);

        stage.setScene(scene);
        stage.setTitle("Seed Based World Generator");
        stage.show();
    }

    int done = 0;
    // This value will never be used
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000)));

    private void test1 (Pane root){
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        Canvas canvas = new Canvas(root.getWidth(), root.getHeight());
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
        root.getChildren().add(canvas);
        int[][] claims = new int[(int)root.getWidth()][(int)root.getHeight()];


        // Values to play with
        int biomes = 15; // don't go higher than 10 without adding more colors
        int[] growthRates = new int[biomes];
        for (int i = 0; i < biomes; i++) {
            growthRates[i] = rand.nextInt(1, 15);
            System.out.println(growthRates[i]);
        }
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.GOLDENROD,
                Color.MAROON, Color.SKYBLUE, Color.PURPLE, Color.PINK, Color.BLACK,
                Color.ORANGERED, Color.GRAY, Color.DARKBLUE, Color.YELLOWGREEN, Color.YELLOW,
                Color.BROWN};
        ArrayList<Integer>[] availableTiles = new ArrayList[biomes];
        for (int i = 0; i < biomes; i++) {
            availableTiles[i] = new ArrayList<>();
        }


        for (int i = 0; i < biomes; i++) {
            int x = rand.nextInt(0, (int)root.getWidth());
            int y = rand.nextInt(0, (int)root.getHeight());
            if(claims[x][y] == 0) {
                claims[x][y] = i + 1;
                pw.setColor(x, y, colors[i]);

                if(x + 1 < root.getWidth()) {
                    if (claims[x + 1][y] == 0) {
                        availableTiles[i].add(x + 1);
                        availableTiles[i].add(y);
                    }
                }
                if(x -1 >= 0) {
                    if (claims[x - 1][y] == 0) {
                        availableTiles[i].add(x - 1);
                        availableTiles[i].add(y);
                    }
                }
                if(y + 1 < root.getHeight()) {
                    if (claims[x][y + 1] == 0) {
                        availableTiles[i].add(x);
                        availableTiles[i].add(y + 1);
                    }
                }
                if(y - 1 >= 0) {
                    if (claims[x][y - 1] == 0) {
                        availableTiles[i].add(x);
                        availableTiles[i].add(y - 1);
                    }
                }
            }else{
                i--;
            }
        }



        KeyFrame kf = new KeyFrame(Duration.millis(1), e -> {
            if(done == biomes){
                System.out.println("stop");
                timeline.stop();
            }
            for (int i = 0; i < biomes; i++) {
                for (int j = 0; j < growthRates[i]; j++) {
                    if(availableTiles[i] == null){
                        break;
                    }

                    int index = rand.nextInt(availableTiles[i].size() / 2);
                    int x = availableTiles[i].get(index * 2);
                    int y = availableTiles[i].get(index * 2 + 1);
                    availableTiles[i].remove(index * 2);
                    availableTiles[i].remove(index * 2);//y index will shift in to replace x so it will be in the same spot

                    if(availableTiles[i].size() <= 0){
                        done++;
                        availableTiles[i] = null;
                        break;
                    }

                    if(claims[x][y] == 0) {
                        claims[x][y] = i + 1;
                        pw.setColor(x, y, colors[i]);

                        if(x + 1 < root.getWidth()) {
                            if (claims[x + 1][y] == 0) {
                                availableTiles[i].add(x + 1);
                                availableTiles[i].add(y);
                            }
                        }
                        if(x -1 >= 0) {
                            if (claims[x - 1][y] == 0) {
                                availableTiles[i].add(x - 1);
                                availableTiles[i].add(y);
                            }
                        }
                        if(y + 1 < root.getHeight()) {
                            if (claims[x][y + 1] == 0) {
                                availableTiles[i].add(x);
                                availableTiles[i].add(y + 1);
                            }
                        }
                        if(y - 1 >= 0) {
                            if (claims[x][y - 1] == 0) {
                                availableTiles[i].add(x);
                                availableTiles[i].add(y - 1);
                            }
                        }
                    }else{
                        j--;
                    }
                }
            }
        });
        timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
