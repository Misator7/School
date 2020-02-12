/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.powerBlock;

import javafx.scene.paint.Color;
import pong.Ball;

/**
 *
 * @author User
 */
public class Wall extends BasicPowerBlock {
    
    public Wall(int x, int y, int l) {
        super(x, y, l);
        this.color = Color.LIGHTBLUE;
    }
    
    @Override
    public boolean action(Ball ball) {
        ball.setSpeed(ball.getSpeedSave());
        ball.setxSpeed(ball.getxSpeed() * -1);
        ball.updateSpeed();        
        return false;  
    }
    
}
