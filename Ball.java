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

    private double x, y, xSpeed, ySpeed, speed, speedSave;
    private final int diameter;
    private int radius;
    private Color color = Color.WHITESMOKE;

    private final double[] vectorX = new double[7];
    private final double[] vectorY = new double[7];
    private int a;

    public Ball(double x, double y, int diameter, double speed) {
        this.diameter = diameter;
        this.radius = diameter / 2;
        this.x = x;
        this.y = y;
        this.xSpeed = speed;
        this.ySpeed = speed;
        this.speed = speed;
        iniAngle();
        randomDirect();
    }

    public void move(GraphicsContext gc) {        
        y += ySpeed;        
        x += xSpeed;
        gc.setFill(color);
        gc.fillRect(x, y, diameter, diameter);
    }

    public void changeColor(GraphicsContext gc, Color color) {
        gc.setFill(color);
        gc.fillRect(x, y, diameter, diameter);
    }

    private void randomDirect() {
        Random r = new Random();
        updateDirect(6);
        if (r.nextBoolean()) {
            xSpeed *= -1;            
        }
        if (r.nextBoolean()) {
            changeYSpeed();
        }
    }
    
    public void updateSpeed() {
        ySpeed = speed * vectorY[a];
        if (xSpeed < 0) {
            xSpeed = speed * vectorX[a] * -1;
        } else {
            xSpeed = speed * vectorX[a];
        }
    }

    public void updateDirect(int a) {
        this.a = a;
        ySpeed = speed * vectorY[a];
        if (xSpeed < 0) {
            xSpeed = speed * vectorX[a];
        } else {
            xSpeed = speed * vectorX[a] * -1;
        }
    }

    public void changeYSpeed() {
        switch (a) {
            case 0:
                a = 6;
                break;
            case 1:
                a = 5;
                break;
            case 2:
                a = 4;
                break;
            case 3:
                break;
            case 4:
                a = 2;
                break;
            case 5:
                a = 1;
                break;
            case 6:
                a = 0;
                break;
        }
        updateSpeed();
    }

    private void iniAngle() {
        vectorX[0] = Math.cos(Math.toRadians(45));
        vectorX[1] = Math.cos(Math.toRadians(30));
        vectorX[2] = Math.cos(Math.toRadians(15));
        vectorX[3] = 1;
        vectorX[4] = vectorX[2];
        vectorX[5] = vectorX[1];
        vectorX[6] = vectorX[0];
        vectorY[0] = Math.sin(Math.toRadians(45));
        vectorY[1] = -0.5;
        vectorY[2] = Math.sin(Math.toRadians(-15));
        vectorY[3] = 0;
        vectorY[4] = Math.sin(Math.toRadians(15));
        vectorY[5] = 0.5;
        vectorY[6] = Math.sin(Math.toRadians(-45));
    }

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

    public double getSpeedSave() {
        return speedSave;
    }

    public void setSpeedSave() {
        speedSave = speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
