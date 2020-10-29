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
        // Each index of all the arrays is for each biome
        int biomes = 15; // don't go higher than 15 without adding more colors
        int[] growthRates = new int[biomes];
        for (int i = 0; i < biomes; i++) {
            // a biome will grow this many tiles each frame
            growthRates[i] = rand.nextInt(1, 15);
            System.out.println(growthRates[i]);
        }
        //this way you can see which color is which biome
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.GOLDENROD,
                Color.MAROON, Color.SKYBLUE, Color.PURPLE, Color.PINK, Color.BLACK,
                Color.ORANGERED, Color.GRAY, Color.DARKBLUE, Color.YELLOWGREEN, Color.YELLOW,
                Color.BROWN};
        // contains a list of coordinates for each biome of all the available tiles to move to
        ArrayList<Integer>[] availableTiles = new ArrayList[biomes];
        for (int i = 0; i < biomes; i++) {
            availableTiles[i] = new ArrayList<>();
        }


        // place the starting position
        for (int i = 0; i < biomes; i++) {
            // selects a spot
            int x = rand.nextInt(0, (int)root.getWidth());
            int y = rand.nextInt(0, (int)root.getHeight());
            // checks if the tile is available
            if(claims[x][y] == 0) {
                claims[x][y] = i + 1;
                pw.setColor(x, y, colors[i]);

                // add the first 4 grow to tiles to their available tiles
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
                // this will make the loop redo the last step
                // this way if it finds a taken spot it will simply try again
                i--;
            }
        }


        // This will set up an animation of the bimes growing so you can watch it as it happens
        //                                         \/ the millisecond delay between frames
        KeyFrame kf = new KeyFrame(Duration.millis(1), e -> {
            // this is the conditional that checks if the program is done the done
            // variable increases by 1 every time a biome cannot add any new tiles
            if(done == biomes){
                System.out.println("stop");
                timeline.stop();
            }

            // loops through all the biomes
            for (int i = 0; i < biomes; i++) {
                // adds each individual tile for each biome
                for (int j = 0; j < growthRates[i]; j++) {
                    // if the biome's list is null that means it is done no further action will be made
                    if(availableTiles[i] == null){
                        break;
                    }
                    if(availableTiles[i].size() <= 0){
                        done++;
                        availableTiles[i] = null;
                        break;
                    }

                    // find a random set of coordinates in the list
                    // since coordinates take up 2 places there are twice the amount of
                    // ints as coordinates hence the / 2
                    int index = rand.nextInt(availableTiles[i].size() / 2);
                    int x = availableTiles[i].get(index * 2);
                    int y = availableTiles[i].get(index * 2 + 1);// y coordinate appears after x
                    availableTiles[i].remove(index * 2);// the coordinate will
                    // either be used or unable to be placed either way it cannot be used again
                    availableTiles[i].remove(index * 2);//y index will shift in to replace x so it will be in the same spot


                    // check if the tile is unclaimed
                    if(claims[x][y] == 0) {
                        // claim tile and dislay the claim
                        claims[x][y] = i + 1;
                        pw.setColor(x, y, colors[i]);

                        // check touching coordinates to see if spread can happen
                        //right
                        if(x + 1 < root.getWidth()) {
                            if (claims[x + 1][y] == 0) {
                                availableTiles[i].add(x + 1);
                                availableTiles[i].add(y);
                            }
                        }
                        //left
                        if(x -1 >= 0) {
                            if (claims[x - 1][y] == 0) {
                                availableTiles[i].add(x - 1);
                                availableTiles[i].add(y);
                            }
                        }
                        //down
                        if(y + 1 < root.getHeight()) {
                            if (claims[x][y + 1] == 0) {
                                availableTiles[i].add(x);
                                availableTiles[i].add(y + 1);
                            }
                        }
                        //up
                        if(y - 1 >= 0) {
                            if (claims[x][y - 1] == 0) {
                                availableTiles[i].add(x);
                                availableTiles[i].add(y - 1);
                            }
                        }

                        // if there are no possible moves left then the biome is done

                    }else{
                        // tile was claimed so try again
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
