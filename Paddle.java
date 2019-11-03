/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author Misator
 */
public class Paddle {

    private double height = 80, width = 20;
    private double x, y;
    private double speed;

    public Paddle(double x, double y, double height, double width, double speed) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.speed = speed;
    }

    public Paddle(double speed) {
        this.speed = speed;
    }

    public Paddle(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void refill(GraphicsContext gc) {
        gc.setFill(Color.WHITESMOKE);
        gc.fillRect(x, y, width, height);
    }

    public void resetPaddle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }
}
