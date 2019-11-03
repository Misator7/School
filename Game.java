/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author User
 */
public class Game {

    private final int winningScore = 3;

    protected final double H, W;

    protected Paddle paddleL;
    protected Paddle paddleR;
    protected Ball ball;

    int scoreL = 0, scoreR = 0;

    int reboundCounter, accelerationCounter;
    static double[] angles = new double[4];

    public Game(double H, double W) {
        this.H = H;
        this.W = W;
        this.paddleL = new Paddle(10, (H - 80) / 2, 80, 20, 5);
        this.paddleR = new Paddle(W - 20 - 10, (H - 80) / 2, 80, 20, 5);
        this.ball = new Ball((W - 20) / 2, (H - 20) / 2, 20, 4);
        iniAngle();
    }

    public Game() {
        H = 5;
        W = 5;
    }

    //Runs logic of game
    public void animationHandle(GraphicsContext gc,
            boolean up1, boolean down1, boolean up2, boolean down2) {
        movePaddle(up1, down1, up2, down2);

        if (ball.getY() < 0 || ball.getY() + ball.getDiameter() > H) {
            ball.setySpeed(ball.getySpeed() * -1);
        }
        if (accelerationCounter == 3) {
            if (ball.getxSpeed() < 0) {
                ball.setxSpeed(ball.getxSpeed() - 0.5);
            } else {
                ball.setxSpeed(ball.getxSpeed() + 0.5);
            }
            if (ball.getySpeed() < 0) {
                ball.setySpeed(ball.getySpeed() - 0.5);
            } else {
                ball.setySpeed(ball.getySpeed() + 0.5);
            }
            accelerationCounter = 0;
        }
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
        gc.setFill(Color.WHITESMOKE);
        gc.strokeLine(W / 2, 0, W / 2, H);
        gc.setFont(new Font(30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText(Integer.toString(this.scoreR), W - 60, 30);
        gc.fillText(Integer.toString(this.scoreL), 60, 30);
        ball.move(gc);
        paddleL.refill(gc);
        paddleR.refill(gc);
        double a;

        if ((a = collisionDetection(paddleL, paddleR, ball)) != 10) {
            ball.setySpeed(ball.getxSpeed() * a);
            ball.setxSpeed(ball.getxSpeed() * -1);
        }
        if (ballCollision(paddleL, paddleR, ball)) {
            ball.setySpeed(ball.getySpeed() * -1);
        }
    }

    protected void movePaddle(boolean up1, boolean down1, boolean up2, boolean down2) {
        if (down1) {
            paddleL.setY(paddleL.getY() + (paddleL.getSpeed()));
        } else if (up1) {
            paddleL.setY(paddleL.getY() - (paddleL.getSpeed()));
        }
        if (paddleL.getY() > (H - paddleL.getHeight())) {
            paddleL.setY(H - paddleL.getHeight());
        } else if (paddleL.getY() < 0) {
            paddleL.setY(0);
        }

        if (down2) {
            paddleR.setY(paddleR.getY() + (paddleR.getSpeed()));
        } else if (up2) {
            paddleR.setY(paddleR.getY() - (paddleR.getSpeed()));
        }
        if (paddleR.getY() > (H - paddleR.getHeight())) {
            paddleR.setY(H - paddleR.getHeight());
        } else if (paddleR.getY() < 0) {
            paddleR.setY(0);
        }
    }

    protected double collisionDetection(Paddle paddleL, Paddle paddleR, Ball ball) {
        if (ball.getX() + 20 >= paddleR.getX() && ball.getX() + 20 <= paddleR.getX() + 5 && ball.getY() + 20 > paddleR.getY() && ball.getY() < paddleR.getY() + paddleR.getHeight()) {
            reboundCounter++;
            accelerationCounter++;
            if (ball.getY() + ball.getDiameter() > paddleR.getY() && ball.getY() + ball.getRadius() < paddleR.getY() + paddleR.getHeight() / 8) {
                return 1;
            } else if (ball.getY() + ball.getDiameter() > paddleR.getY() + paddleR.getHeight() / 8 && ball.getY() + ball.getRadius() < paddleR.getY() + 2 * (paddleR.getHeight() / 8)) {
                return angles[0];
            } else if (ball.getY() + ball.getDiameter() > paddleR.getY() + 2 * (paddleR.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleR.getY() + 3 * (paddleR.getHeight() / 8)) {
                return angles[1];
            } else if (ball.getY() + ball.getDiameter() > paddleR.getY() + 3 * (paddleR.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleR.getY() + 5 * (paddleR.getHeight() / 8)) {
                return 0;
            } else if (ball.getY() + ball.getDiameter() > paddleR.getY() + 5 * (paddleR.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleR.getY() + 6 * (paddleR.getHeight() / 8)) {
                return angles[2];
            } else if (ball.getY() + ball.getDiameter() > paddleR.getY() + 6 * (paddleR.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleR.getY() + 7 * (paddleR.getHeight() / 8)) {
                return angles[3];
            } else if (ball.getY() + ball.getDiameter() > paddleR.getY() + 7 * (paddleR.getHeight() / 8) && ball.getY() < paddleR.getY() + paddleR.getHeight()) {
                return -1;
            }
        } else if (ball.getX() <= paddleL.getX() + paddleL.getWidth() && ball.getX() >= paddleL.getX() + paddleL.getWidth() - 5 && ball.getY() + 20 > paddleL.getY() && ball.getY() < paddleL.getY() + paddleL.getHeight()) {
            reboundCounter++;
            accelerationCounter++;
            if (ball.getY() + ball.getDiameter() > paddleL.getY() && ball.getY() + ball.getRadius() < paddleL.getY() + paddleR.getHeight() / 8) {
                return -1;
            } else if (ball.getY() + ball.getDiameter() > paddleL.getY() + paddleL.getHeight() / 8 && ball.getY() + ball.getRadius() < paddleL.getY() + 2 * (paddleL.getHeight() / 8)) {
                return angles[3];
            } else if (ball.getY() + ball.getDiameter() > paddleL.getY() + 2 * (paddleL.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleL.getY() + 3 * (paddleL.getHeight() / 8)) {
                return angles[2];
            } else if (ball.getY() + ball.getDiameter() > paddleL.getY() + 3 * (paddleL.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleL.getY() + 5 * (paddleL.getHeight() / 8)) {
                return 0;
            } else if (ball.getY() + ball.getDiameter() > paddleL.getY() + 5 * (paddleL.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleL.getY() + 6 * (paddleL.getHeight() / 8)) {
                return angles[1];
            } else if (ball.getY() + ball.getDiameter() > paddleL.getY() + 6 * (paddleL.getHeight() / 8) && ball.getY() + ball.getRadius() < paddleL.getY() + 7 * (paddleL.getHeight() / 8)) {
                return angles[0];
            } else if (ball.getY() + ball.getDiameter() > paddleL.getY() + 7 * (paddleL.getHeight() / 8) && ball.getY() < paddleL.getY() + paddleL.getHeight()) {
                return 1;
            }
        }
        return 10;
    }

    //returns true if ball hits upper or downer side of paddle otherwise returns false 
    protected boolean ballCollision(Paddle paddleL, Paddle paddleR, Ball ball) {
        return ball.getX() + 20 > paddleR.getX() && ball.getX() < paddleR.getX() + paddleR.getWidth() && ball.getY() < paddleR.getY() && ball.getY() > paddleR.getY() - 20
                || ball.getX() + 20 > paddleR.getX() && ball.getX() < paddleR.getX() + paddleR.getWidth() && ball.getY() > paddleR.getY() + paddleR.getHeight() - 20 && ball.getY() < paddleR.getY() + paddleR.getHeight()
                || ball.getX() < paddleL.getX() + paddleL.getWidth() && ball.getX() + 20 > paddleL.getX() && ball.getY() < paddleL.getY() && ball.getY() > paddleL.getY() - 20
                || ball.getX() + 20 > paddleL.getX() && ball.getX() < paddleL.getX() + paddleL.getWidth() && ball.getY() > paddleL.getY() + paddleL.getHeight() - 20 && ball.getY() < paddleL.getY() + paddleL.getHeight();
    }

    public int scoreChecking(double W) {
        if (ball.getX() + 20 > W) {
            this.scoreL++;
            if (this.scoreL == this.getWinningScore()) {
                return 0;
            } else {
                return 2;
            }
        } else if (ball.getX() < 0) {
            this.scoreR++;
            if (this.scoreR == this.getWinningScore()) {
                return 1;
            } else {
                return 2;
            }
        }
        return 10;
    }

    public void positionReset(GraphicsContext gc) {
        this.paddleL = new Paddle(10, (H - 80) / 2, 80, 20, 5);
        this.paddleR = new Paddle(W - 20 - 10, (H - 80) / 2, 80, 20, 5);
        this.ball = new Ball((W - 20) / 2, (H - 20) / 2, 20, 4);
        paddleL.refill(gc);
        paddleR.refill(gc);
        ball.move(gc);
    }

    private void iniAngle() {
        angles[0] = Math.tan(Math.toRadians(-30));
        angles[1] = Math.tan(Math.toRadians(-15));
        angles[2] = Math.tan(Math.toRadians(15));
        angles[3] = Math.tan(Math.toRadians(30));
    }

    /**
     * @return the winningScore
     */
    public int getWinningScore() {
        return winningScore;
    }
}
