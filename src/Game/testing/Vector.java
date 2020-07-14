package Game.testing;

public class Vector {
    public int x, y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
    }

    public void add(Vector vector){
        this.x += vector.x;
        this.y += vector.y;
    }

    public void multiply(Vector vector){
        this.x *= vector.x;
        this.y *= vector.y;
    }

    public void multiply(int num){
        this.x *= num;
        this.y *= num;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
