/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.powerBlock;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pong.Ball;

/**
 *
 * @author User
 */
public class BasicPowerBlock {
    
    protected Color color;
    public final int X, Y, L;
    private int a;
    private double c = -1.5;
    
    public BasicPowerBlock(int x, int y, int l) {
        this.X = x;
        this.Y = y;
        this.L = l;
        this.color = Color.YELLOW;
    }
    
    public void fillBlock(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(X, Y, L, L);
    }
    
    public boolean action(Ball ball) {
        if (ball.getSpeed() < 0) {
                ball.setSpeed(ball.getSpeed() + Math.pow(1.3, -c));
            } else {
                ball.setSpeed(ball.getSpeed() - Math.pow(1.3, -c));
            }
            c += 0.4;
            if (ball.getSpeed() <= ball.getSpeedSave() + 0.2) {
                ball.setSpeed(ball.getSpeedSave());
                c = -1.5;
                return false;
            }
            ball.updateSpeed();
            return true;
    }
}
