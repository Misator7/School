/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.util.Random;

/**
 *
 * @author User
 */
public class SinglePlayer extends Game {

    private int difficulty;

    Random ran = new Random();
    int counter;
    int x = 0;

    public SinglePlayer(double H, double W) {
        super(H, W);
    }

    @Override
    protected void movePaddle(boolean up1, boolean down1, boolean up2, boolean down2) {
        if (down1 || down2) {
            paddleR.setY(paddleR.getY() + (paddleR.getSpeed()));
        } else if (up1 || up2) {
            paddleR.setY(paddleR.getY() - (paddleR.getSpeed()));
        }
        if (paddleR.getY() > (H - paddleR.getHeight())) {
            paddleR.setY(H - paddleR.getHeight());
        } else if (paddleR.getY() < 0) {
            paddleR.setY(0);
        }
        switch (difficulty) {
            case 1:
                NPC1();
                break;
            default:
                NPC1();
        }
    }

    private void NPC1() {
        if (counter % 80 == 0) {
            x = ran.nextInt(8);
        }
        double res = paddleL.getY() + x * paddleL.getHeight() / 8 + paddleL.getHeight() / 16;
        counter++;
        if (ball.getY() + ball.getRadius() > res && (ball.getY() + ball.getRadius()) - res > 4) {
            paddleL.setY(paddleL.getY() + paddleL.getSpeed());
        } else if (ball.getY() + ball.getRadius() < res && (ball.getY() + ball.getRadius()) - res < -4) {
            paddleL.setY(paddleL.getY() - paddleL.getSpeed());
        }
        if (paddleL.getY() > (H - paddleL.getHeight())) {
            paddleL.setY(H - paddleL.getHeight());
        } else if (paddleL.getY() < 0) {
            paddleL.setY(0);
        }
    }
}
