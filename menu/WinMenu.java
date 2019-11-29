/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.menu;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pong.Game;
import pong.SinglePlayer;

/**
 *
 * @author User
 */
public class WinMenu extends Menu {

    private double sizeRestart = 15.5, extend = 0;
    private Game originalGame;

    public WinMenu(double H, double W, GraphicsContext gc, Game game, double xwinner) {
        super(H, W);
        this.originalGame = game;
        gc.setFill(Color.BLACK);
        gc.fillRect(50, 37, 30, 30);
        gc.fillRect(830, 37, 30, 30);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(Integer.toString(game.getScoreR()), W - 60, 30);
        gc.fillText(Integer.toString(game.getScoreL()), 60, 30);
        gc.setFill(Color.GOLD);
        gc.setFont(new Font(60));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Winner", xwinner, H / 2);
    }

    @Override
    public boolean menuTimerHandle(GraphicsContext gc,
            boolean up1, boolean up2, boolean down1, boolean down2,
            boolean enter, boolean backspace) {
        if (extend % 5 == 0 && extend <= 25) {
            sizeRestart += 0.1;
        } else if (extend % 5 == 0 && extend <= 50) {
            sizeRestart -= 0.1;
        } else if (extend > 50) {
            extend = 0;
        }
        extend++;
        gc.setFill(Color.BLACK);
        gc.fillRect(340, H - 140, 225, 40);
        gc.setFill(Color.LIGHTGREY);
        gc.setFont(new Font(sizeRestart));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.BOTTOM);
        gc.fillText("Press ENTER to restart game", W / 2, H - 114);
        if (enter) {
            if (originalGame.getClass() == Game.class) {
                this.game = new Game(H, W);
                return true;
            } else if (originalGame.getClass() == SinglePlayer.class) {
                this.game = new SinglePlayer(H, W);
                game.setDifficulty(originalGame.getDifficulty());
                return true;
            }
        } else if (backspace) {
            return true;
        }
        return false;
    }
}
