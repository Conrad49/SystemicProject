import Game.ImprovedNoise;
import Game.Tiles.Tile;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

public class Noise extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1200, 700);

        //new AnimationTimer()
        //{
            //public void handle(long currentNanoTime)
            //{
                //tickAndRender(root);
            //}
        //}.start();

        testImage(root);

        stage.setScene(scene);
        stage.show();



    }

    public void tickAndRender(Pane root){
        Rectangle[][] array = ImprovedNoise.getNoiseArray();
        for (int i = 0; i < array[0].length - 1; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                root.getChildren().add(array[j][i]);
            }
        }
    }

    public void testImage(Pane root){
        Canvas canvas = new Canvas(root.getWidth(), root.getHeight());
        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();

        double count = 3.14;
        double yCount = 1.25;
        double inc = 0.01;

        for(int i = 0; i < root.getHeight(); i ++) {
            for (int j = 0; j < root.getWidth(); j++) {
                double noiseVal = (ImprovedNoise.noise(count, yCount, 1) + 1) / 2.0;
                count += inc;

                if (noiseVal < 0.3) {
                    pw.setColor(j, i, Color.RED);
                } else if(noiseVal < 0.4){
                    pw.setColor(j, i, Color.BLUE);
                }
            }
            count = 0;
            yCount += inc;
        }
        root.getChildren().add(canvas);
    }
}
