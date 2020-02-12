/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import pong.powerBlock.BasicPowerBlock;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pong.options.Document;
import pong.powerBlock.Wall;

/**
 *
 * @author User
 */
public class Game {

    private int winningScore;

    protected final double H, W;

    protected Paddle paddleL;
    protected Paddle paddleR;
    protected Ball ball;
    protected ArrayList<BasicPowerBlock> pb = new ArrayList();
    protected ArrayList<BasicPowerBlock> activeBlocks = new ArrayList();
    
    private AudioClip ac;
    protected Document doc;

    private int scoreL = 0, scoreR = 0;
    private int a, b;

    private boolean ar = false;
    private double c = -1.5;

    private int reboundCounter, accelerationCounter, frameCounter = 0;

    public Game(double W, double H) {
        this.H = H;
        this.W = W;
        doc = new Document();
        winningScore = doc.getWinPoints();
        this.paddleL = new Paddle(W / 100, (H - W / 100) / 2, 0.12 * H, W / 70, 7);
        this.paddleR = new Paddle(W - W / 100 - W / 70, (H - W / 100) / 2, 0.12 * H, W / 70, 7);
        this.ball = new Ball((W - 20) / 2, (H - 20) / 2, (int) W / 70, doc.getBallSpeed() * W * 0.001);
        ac = new AudioClip(Paths.get("rsrc\\pong.mp3").toUri().toString());
        ac.setVolume(doc.getSoundVolume() / 100);
    }

    public void animationHandle(GraphicsContext gc,
            boolean up1, boolean down1, boolean up2, boolean down2,
            boolean pause) {

        frameCounter++;
        if (frameCounter == 1000) {
            frameCounter = 0;
            Random r = new Random();
            switch (r.nextInt(2)) {
                case 0:
                    pb.add(new BasicPowerBlock(r.nextInt(1000) + 300, (int) ball.getY(), (int)W/60));
                    break;
                case 1:
                    pb.add(new Wall(r.nextInt(1000) + 300, (int) ball.getY(), (int)W/60));

            }
        }
        movePaddle(up1, down1, up2, down2);
        if (ball.getY() < 0 || ball.getY() + ball.getDiameter() > H) {
            ball.changeYSpeed();
        }
        if (accelerationCounter == 3) {            
            ball.setSpeed(ball.getSpeed() + 0.5);
            accelerationCounter = 0;
        }
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
        gc.setFill(Color.WHITESMOKE);
        gc.setLineWidth(3);
        gc.setLineDashes(15);
        gc.strokeLine(W / 2, 0, W / 2, H);
        gc.setFont(new Font(30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText(Integer.toString(this.getScoreR()), W - 60, 30);
        gc.fillText(Integer.toString(this.getScoreL()), 60, 30);
        Iterator<BasicPowerBlock> it = pb.iterator();
        while (it.hasNext()) {
            BasicPowerBlock d = it.next();
            if (pbCollision(d)) {
                ball.setSpeedSave();
                ball.setSpeed(ball.getSpeed() + 15);
                ball.updateSpeed();
                activeBlocks.add(d);
                it.remove();
            } else {
                d.fillBlock(gc);
            }
        }                
        ball.move(gc);
        paddleL.refill(gc);
        paddleR.refill(gc);
        if ((a = collisionDetection(paddleR, ball)) != 10) {
            if (doc.getSound()) {
                ac.play();
            }
            ball.updateDirect(a);
        } else if ((a = collisionDetection(paddleL, ball)) != 10) {
            if (doc.getSound()) {
                ac.play();
            }
            ball.updateDirect(a);
        } else if (ballCollision()) {
            ball.changeYSpeed();
        }

        it = activeBlocks.iterator();
        while (it.hasNext()) {
            BasicPowerBlock d = it.next();
            if(!d.action(ball)) {
                it.remove();
            }
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

    

    protected int collisionDetection(Paddle paddle, Ball ball) {
        if ((ball.getX() + ball.getDiameter() >= paddle.getX() && ball.getX() + ball.getDiameter() <= paddle.getX() + ball.getSpeed() && ball.getY() + ball.getDiameter() > paddle.getY() && ball.getY() < paddle.getY() + paddle.getHeight())
                || (ball.getX() <= paddle.getX() + paddle.getWidth() && ball.getX() >= paddle.getX() + paddle.getWidth() - ball.getSpeed() && ball.getY() + ball.getDiameter() > paddle.getY() && ball.getY() < paddle.getY() + paddle.getHeight())) {
            reboundCounter++;
            accelerationCounter++;
            if (ball.getY() + ball.getDiameter() > paddle.getY() && ball.getY() + ball.getRadius() < paddle.getY() + paddle.getHeight() / 8) {
                return 6;
            } else if (ball.getY() + ball.getDiameter() > paddle.getY() + paddle.getHeight() / 8 && ball.getY() + ball.getRadius() < paddle.getY() + 2 * (paddle.getHeight() / 8)) {
                return 1;
            } else if (ball.getY() + ball.getDiameter() > paddle.getY() + 2 * (paddle.getHeight() / 8) && ball.getY() + ball.getRadius() < paddle.getY() + 3 * (paddle.getHeight() / 8)) {
                return 2;
            } else if (ball.getY() + ball.getDiameter() > paddle.getY() + 3 * (paddle.getHeight() / 8) && ball.getY() + ball.getRadius() < paddle.getY() + 5 * (paddle.getHeight() / 8)) {
                return 3;
            } else if (ball.getY() + ball.getDiameter() > paddle.getY() + 5 * (paddle.getHeight() / 8) && ball.getY() + ball.getRadius() < paddle.getY() + 6 * (paddle.getHeight() / 8)) {
                return 4;
            } else if (ball.getY() + ball.getDiameter() > paddle.getY() + 6 * (paddle.getHeight() / 8) && ball.getY() + ball.getRadius() < paddle.getY() + 7 * (paddle.getHeight() / 8)) {
                return 5;
            } else if (ball.getY() + ball.getDiameter() > paddle.getY() + 7 * (paddle.getHeight() / 8) && ball.getY() < paddle.getY() + paddle.getHeight()) {
                return 0;
            }
        }
        return 10;
    }

    protected boolean ballCollision() {
        return ball.getX() + 20 > paddleR.getX() && ball.getX() < paddleR.getX() + paddleR.getWidth() && ball.getY() < paddleR.getY() && ball.getY() > paddleR.getY() - 20
                || ball.getX() + 20 > paddleR.getX() && ball.getX() < paddleR.getX() + paddleR.getWidth() && ball.getY() > paddleR.getY() + paddleR.getHeight() - 20 && ball.getY() < paddleR.getY() + paddleR.getHeight()
                || ball.getX() < paddleL.getX() + paddleL.getWidth() && ball.getX() + 20 > paddleL.getX() && ball.getY() < paddleL.getY() && ball.getY() > paddleL.getY() - 20
                || ball.getX() + 20 > paddleL.getX() && ball.getX() < paddleL.getX() + paddleL.getWidth() && ball.getY() > paddleL.getY() + paddleL.getHeight() - 20 && ball.getY() < paddleL.getY() + paddleL.getHeight();
    }
    
    protected boolean pbCollision(BasicPowerBlock pb) {
        return ball.getX() + ball.getDiameter() >= pb.X 
                && ball.getX() <= pb.X + pb.L 
                && ball.getY() + ball.getDiameter() >= pb.Y
                && ball.getY() <= pb.Y + pb.L;
    }

    public int scoreChecking(double W) {
        if (ball.getX() + 20 > W) {
            this.scoreL++;
            if (this.getScoreL() == this.getWinningScore()) {
                return 0;
            } else {
                return 2;
            }
        } else if (ball.getX() < 0) {
            this.scoreR++;
            if (this.getScoreR() == this.getWinningScore()) {
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

    public void scoreReset() {
        scoreL = 0;
        scoreR = 0;
    }

    public void fillFrame(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
        gc.setFill(Color.WHITESMOKE);
        gc.strokeLine(W / 2, 0, W / 2, H);
        gc.setFont(new Font(30));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.TOP);
        gc.fillText(Integer.toString(0), W - 60, 30);
        gc.fillText(Integer.toString(0), 60, 30);
    }

    public int getWinningScore() {
        return winningScore;
    }

    public int getScoreL() {
        return scoreL;
    }

    public int getScoreR() {
        return scoreR;
    }

    public void setDifficulty(int difficulty) {
    }

    public int getDifficulty() {
        return 0;
    }
}
