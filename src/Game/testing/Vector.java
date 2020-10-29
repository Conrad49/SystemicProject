package Game.testing;

import java.awt.*;

public class Vector {
    private double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector() {
    }

    public Vector(Vector vector){
        this.setToVec(vector);
    }

    public void setToVec(Vector vec){
        this.x = vec.x;
        this.y = vec.y;
    }

    public Vector add(Vector vector){
        double newX = x + vector.x;
        double newY = y + vector.y;

        return new Vector(newX, newY);
    }

    public Vector add(double num){
        double newX = x + num;
        double newY = y + num;

        return new Vector(newX, newY);
    }

    public Vector subtract(Vector vector){
        double newX = x - vector.x;
        double newY = y - vector.y;

        return new Vector(newX, newY);
    }

    public Vector multiply(Vector vector){
        double newX = x * vector.x;
        double newY = y * vector.y;

        return new Vector(newX, newY);
    }

    public Vector multiply(double num){
        double newX = x * num;
        double newY = y * num;

        return new Vector(newX, newY);
    }

    public void normalize(double magnitude){

        if (Math.abs(this.x) != 0) {
            this.x = this.x / magnitude;
        }
        if (Math.abs(this.y) != 0) {
            this.y = this.y / magnitude;
        }
    }

    public Vector divide(Vector vector){
        double newX = x / vector.x;
        double newY = y / vector.y;

        return new Vector(newX, newY);
    }

    public static Vector getVecFromPt(Point point){
        return new Vector(point.x, point.y);
    }

    public double getMagnitude(){
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void printComponents(){
        System.out.println(this.getX() + ", " + this.getY());
    }
}
