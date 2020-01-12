/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.util.Random;
import pong.menu.Menu;

/**
 *
 * @author User
 */
public class SinglePlayer extends Game {

    public int difficulty;
    private Menu menu;

    private final Random ran = new Random();
    private int counter;
    private int x = 0;
    boolean bool = true;

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
            case 0:
                NPC1();
                break;
            case 1:
                NPC2();
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

    private void NPC2() {
        double steps = 0;
        if (ball.getxSpeed() < 0) {
            if (ball.getySpeed() < 0) {
                steps = ball.getY() / ball.getySpeed();
            } else if (ball.getySpeed() != 0) {
                steps = ball.getY() / ball.getySpeed() * -1;
            }
            if (ball.getX() - steps * ball.getxSpeed() > W / 2) {
                bool = false;
                if (ball.getySpeed() > 0) {
                    if (paddleL.getY() + paddleL.getHeight() / 2 < H / 4) {
                        paddleL.setY(paddleL.getY() + paddleL.getSpeed());
                    } else if (paddleL.getY() + paddleL.getHeight() / 2 > H / 4) {
                        paddleL.setY(paddleL.getY() - paddleL.getSpeed());
                    }
                } else {
                    if (paddleL.getY() + paddleL.getHeight() / 2 < H * 3 / 4) {
                        paddleL.setY(paddleL.getY() + paddleL.getSpeed());
                    } else if (paddleL.getY() + paddleL.getHeight() / 2 > H * 3 / 4) {
                        paddleL.setY(paddleL.getY() - paddleL.getSpeed());
                    }
                }
            }
        }
        if (bool || ball.getX() < W / 2) {
            bool = true;
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
        }
        if (paddleL.getY() > (H - paddleL.getHeight())) {
            paddleL.setY(H - paddleL.getHeight());
        } else if (paddleL.getY() < 0) {
            paddleL.setY(0);
        }
    }

    @Override
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }
}
