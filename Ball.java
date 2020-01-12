/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Misator
 */
public class Ball {

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double x, y, xSpeed, ySpeed, speed;
    private final int diameter;
    private int radius;
    private Color color = Color.WHITESMOKE;

    public Ball(double x, double y, int diameter, int speed) {
        this.diameter = diameter;
        this.radius = diameter / 2;
        this.x = x;
        this.y = y;
        this.xSpeed = speed;
        this.ySpeed = speed;
        this.speed = speed;
        randomDirect();
    }

    public void move(GraphicsContext gc) {
        y += ySpeed;
        x += xSpeed;
        gc.setFill(color);
        gc.fillRect(getX(), y, getDiameter(), getDiameter());
    }

    public void changeColor(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillRect(x, y, diameter, diameter);
    }

    private void randomDirect() {
        Random r = new Random();
        if (r.nextBoolean()) {
            xSpeed *= -1;
        }
        if (r.nextBoolean()) {
            ySpeed *= -1;
        }
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param xSpeed the xSpeed to set
     */
    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * @param ySpeed the ySpeed to set
     */
    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * @return the xSpeed
     */
    public double getxSpeed() {
        return xSpeed;
    }

    /**
     * @return the ySpeed
     */
    public double getySpeed() {
        return ySpeed;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the diameter
     */
    public int getDiameter() {
        return diameter;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }
}
