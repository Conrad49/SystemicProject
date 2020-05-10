import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public abstract class Entity {

    int xSpeed;
    int ySpeed;
    Image texture;
    Color backColor;
    Rectangle boundsBox;
    int tileX;
    int tileY;
    double posX;
    double posY;
    boolean isVisible = false;

    public Entity(Color backColor, Rectangle boundsBox) {
        this.backColor = backColor;
        this.boundsBox = boundsBox;
        this.posX = boundsBox.getX();
        this.posY = boundsBox.getY();
        this.tileX = (int)posX/Tile.width;
        this.tileY = (int)posY/Tile.width;
    }

    public Entity(Image texture, Rectangle boundsBox) {
        this.texture = texture;
        this.boundsBox = boundsBox;
        this.posX = boundsBox.getX();
        this.posY = boundsBox.getY();
        this.tileX = (int)posX/Tile.width;
        this.tileY = (int)posY/Tile.width;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void tick(){

        System.out.println("tick!");
        // update positions and velocities / speeds
    }

    public void render(GraphicsContext g){
        if(texture != null){
            g.drawImage(this.texture, this.posX, this.posY);
        } else {
            g.setStroke(this.backColor);
            g.fillRect(this.boundsBox.getX(), this.boundsBox.getY(), this.boundsBox.getWidth(), this.boundsBox.getHeight());
        }
    }

    public void checkVisiblity(Player p){
        Tile[][] visibleTiles = p.visibleTiles;

        if(this.tileX >= visibleTiles[0][0].posX && this.tileY >= visibleTiles[0][0].posY){
            if(this.tileX <= visibleTiles[visibleTiles.length-1][0].posX){
                if(this.tileY <= visibleTiles[visibleTiles.length-1][visibleTiles.length-1].posY){



                }
            }
        }
    }

    public void checkTileCollision(Tile tile){
        Rectangle tileBounds = tile.boundsBox;
        if (tile.isSolid) {

            Shape intersect = Shape.intersect(this.boundsBox, tileBounds);
            if (intersect.getBoundsInLocal().getWidth() != -1) {
               System.out.println("COLLISION!");
            }

            if(tileBounds.intersects(this.boundsBox.getBoundsInLocal())){
                if(tileBounds.getX() < this.boundsBox.getX()){
                    this.posX = tileBounds.getX() + tileBounds.getWidth();
                            //- (this.boundsBox.getWidth()/2);
                }

                if(tileBounds.getX() > this.boundsBox.getX()){
                    this.posX = tileBounds.getX() - (this.boundsBox.getWidth()/2);
                }

                if(tileBounds.getY() < this.boundsBox.getY()){
                    this.posY = tileBounds.getY() + tileBounds.getHeight();
                }

                if(tileBounds.getY() > this.boundsBox.getY()){
                    this.posY = tileBounds.getY() + tileBounds.getHeight();
                }
            }
        }
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }
}
