import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    static Scene mainScene;
    static GraphicsContext graphicsContext;
    static int WIDTH = 512;
    static int HEIGHT = 256;

    static Image left;
    static Image leftGreen;

    static Image right;
    static Image rightGreen;

    static HashSet<String> currentlyActiveKeys;

    private static boolean shouldDrawLine = true;
    private static double mouseX = 0;
    private static double mouseY = 0;
    private static double mouseXOffset = 0;
    private static double mouseYOffset = 0;
    private static double posX;
    private static double posY;
    private static final double speed = 5;

    static double height = 0;
    static double width = 0;

    static Player player = new Player(Color.AQUA, new Rectangle(100, 100, 50, 70));

    @Override
    public void start(Stage mainStage)
    {
        mainStage.setTitle("Event Handling");

        Group root = new Group();

        mainScene = new Scene(root);
        mainStage.setScene(mainScene);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        root.getChildren().add(canvas);

        graphicsContext = canvas.getGraphicsContext2D();

        posX = canvas.getWidth()/2;
        posY = canvas.getHeight()/2;
        height = canvas.getHeight();
        width = canvas.getWidth();

        prepareActionHandlers();

        /**
         * Main "game" loop
         */
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                tickAndRender();
            }
        }.start();

        mainStage.show();

    }

    private static void prepareActionHandlers()
    {
        // use a set so duplicates are not possible
        currentlyActiveKeys = new HashSet<String>();
        mainScene.setOnKeyPressed(e -> currentlyActiveKeys.add(e.getCode().toString()));
        mainScene.setOnKeyReleased(e -> currentlyActiveKeys.remove(e.getCode().toString()));
        mainScene.setOnMouseEntered(e -> shouldDrawLine = true);
        mainScene.setOnMouseMoved(e -> {
            shouldDrawLine = true;
            mouseX = e.getX();
            mouseY = e.getY();

            mouseXOffset = mouseX - posX;
            mouseYOffset = mouseY - posY;

        });
        mainScene.setOnMouseExited(e -> shouldDrawLine = false);

    }

    public void renderTiles(){

        for(int i = 0; i < Tile.getAllTiles().length; i ++){
            for(int j = 0; j < Tile.getAllTiles().length; j ++){
                Tile[][] tiles = Tile.getAllTiles();
                Tile tileToBeRendered = tiles[j][i];
            }
        }
    }


    private static void tickAndRender()
    {
        /*mouseX  = MouseInfo.getPointerInfo().getLocation().x;
        mouseY  = MouseInfo.getPointerInfo().getLocation().y;

        mouseXOffset = mouseX - posX;
        mouseYOffset = mouseY - posY;*/

        // clear canvas
        // doesn't work :/  v

        graphicsContext.clearRect(0, 0, WIDTH, HEIGHT);
        
        
        int topLeftX = player.tileX - Camera.cameraWidth/2;
        int topLeftY = player.tileY - Camera.cameraWidth/2;
        Tile[][] allTiles = Tile.getAllTiles();
        Tile[][] visibleTiles = new Tile[Camera.cameraWidth][Camera.cameraWidth];

        if (topLeftX >= 0 && topLeftY >= 0) {
            for(int i = topLeftY; i < topLeftY + Camera.cameraWidth; i ++){
                for(int j = topLeftX; j < topLeftX + Camera.cameraWidth; j ++){
                    visibleTiles[j - topLeftX][i - topLeftY] = allTiles[j][i];
                }
            }
        }

        Camera camera = new Camera(visibleTiles);

        // camera.renderVisibleTiles(graphicsContext);

        if (currentlyActiveKeys.contains("A"))
        {
            posX -= speed;
        }

        if (currentlyActiveKeys.contains("D"))
        {
            posX += speed;
        }

        if (currentlyActiveKeys.contains("W"))
        {
            posY -= speed;
        }

        if (currentlyActiveKeys.contains("S"))
        {
            posY += speed;
        }

        if(shouldDrawLine){

            double angle = Math.atan(mouseYOffset/mouseXOffset);
            double degrees = Math.toDegrees(angle);
            graphicsContext.fillText("Angle in degress: " + degrees, 0, 20);

            double xCoord = (posX + -(posY/Math.tan(angle)));

            graphicsContext.setStroke(Color.BLACK);

            if(mouseY < posY){

                graphicsContext.strokeLine(posX, posY, xCoord, 0);
            } else {

                xCoord = (posX + ((height - posY)/Math.tan(angle)));
                graphicsContext.strokeLine(posX, posY, xCoord, height);
            }

            if(mouseY == posY){
                if(mouseX > posX){
                    graphicsContext.strokeLine(posX, posY, width, posY);
                } else {
                    graphicsContext.strokeLine(posX, posY, 0, posY);
                }
            }


        }
    }
}
