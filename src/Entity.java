import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public abstract class Entity {

    double xSpeed;
    double ySpeed;
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

    // checks the entity that this method is called on for collision with a given tile
    public void checkTileCollision(Tile tile){
        Rectangle tileBounds = tile.boundsBox;

        if (tile.isSolid) {

            Rectangle shiftedRect = new Rectangle(this.posX - (this.boundsBox.getWidth() / 2), this.posY - (this.boundsBox.getHeight() / 2), this.boundsBox.getWidth(), this.boundsBox.getHeight());

            boolean intersects = tileBounds.intersects(shiftedRect.getBoundsInLocal());

            Shape intersect = Shape.intersect(shiftedRect, tileBounds);
            if (intersect.getBoundsInLocal().getWidth() != -1) {

                // left
                if(tileBounds.getX() < this.boundsBox.getX() && this.xSpeed < 0 && (tile.posY / tile.boundsBox.getHeight()) == this.tileY){
                    this.posX = this.posX + intersect.getBoundsInLocal().getWidth();
                    this.xSpeed = 0;
                }

                if(tileBounds.getX() > this.boundsBox.getX() && this.xSpeed > 0 && (tile.posY / tile.boundsBox.getHeight()) == this.tileY){
                    this.posX = this.posX - intersect.getBoundsInLocal().getWidth();
                    this.xSpeed = 0;
                }

                // above
                if(tileBounds.getY() < this.boundsBox.getY() && this.ySpeed < 0 && (tile.posX / tile.boundsBox.getWidth()) == this.tileX){
                    this.posY = this.posY + intersect.getBoundsInLocal().getHeight();
                    this.ySpeed = 0;
                }

                // below
                if(tileBounds.getY() > this.boundsBox.getY() && this.ySpeed > 0 && (tile.posX / tile.boundsBox.getWidth()) == this.tileX){
                    this.posY = this.posY - intersect.getBoundsInLocal().getHeight();
                    this.ySpeed = 0;
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

    public Image getTexture() {
        return this.texture;
    }

    public int[] getChunkCoords(){
        int[] chunkCoords = new int[2];
        chunkCoords[0] = this.tileX / Chunk.sideLength;
        chunkCoords[1] = this.tileY / Chunk.sideLength;
        return chunkCoords;
    }
}
