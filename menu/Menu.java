/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.menu;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pong.Game;
import pong.Pong;
import pong.SinglePlayer;

/**
 *
 * @author User
 */
public class Menu {

    protected int index = 0;
    private int sizeSing = 45, sizeMulti = 30, sizeSet = 30;

    private double sizeRestart = 15.5, extend = 0;

    protected Game game;

    String[] item = new String[3];

    protected double H, W;

    public Menu(double H, double W) {
        this.H = H;
        this.W = W;
        initItems();
    }

    public boolean menuTimerHandle(GraphicsContext gc,
            boolean up1, boolean up2, boolean down1, boolean down2,
            boolean enter, boolean backspace) {

        if (up1 || up2) {
            switch (index) {
                case 0:
                    index = 2;
                    break;
                case 1:
                    index = 0;
                    break;
                case 2:
                    index = 1;
                    break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (down1 || down2) {
            switch (index) {
                case 0:
                    index = 1;
                    break;
                case 1:
                    index = 2;
                    break;
                case 2:
                    index = 0;
                    break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, W, H);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(new Font(80));
        gc.fillText(getHeader(), W / 2, 150);
        gc.setLineWidth(5);
        gc.strokeLine(60, 185, W - 60, 185);
        for (int i = 0; i < item.length; i++) {
            if (i == index) {
                gc.setFont(new Font(45));
            } else {
                gc.setFont(new Font(30));
            }
            gc.setStroke(Color.WHITESMOKE);
            gc.fillText(item[i], W / 2, 300 + i * 70);
        }
        if (enter) {
            return setResult(gc);
        }
        return false;
    }

    protected String getHeader() {
        return "Pong";
    }

    protected void initItems() {
        item[0] = "1 Player";
        item[1] = "2 Players";
        item[2] = "Settings";
    }

    protected boolean setResult(GraphicsContext gc) {
        switch (index) {
            case 0:
                game = new SinglePlayer(H, W);
                game.fillFrame(gc);
                break;
            case 1:
                game = new Game(H, W);
                game.fillFrame(gc);

                break;
            case 2:
                return false;
        }
        try {
            Thread.sleep(700);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    /**
     * @return the game
     */
    public Game getResult() {
        return game;
    }
}
