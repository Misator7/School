/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.menu;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pong.Game;
import pong.Pong;
/**
 *
 * @author User
 */
public class Menu {

    protected int index = 0;

    protected Game game;

    String[] item = initItems();;

    protected double H, W;
    
    protected boolean a = true;

    public Menu(double W, double H) {
        this.H = H;
        this.W = W;
    }

    public boolean menuTimerHandle(GraphicsContext gc,
            boolean up1, boolean up2, boolean down1, boolean down2,
            boolean enter, boolean backspace) {
        
        if (up1 || up2) {
            index--;
            if (index < 0) {
                index = item.length - 1;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (down1 || down2) {
            index++;
            if (index > item.length - 1) {
                index = 0;
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
        gc.setFont(new Font(W/10));
        gc.fillText(getHeader(), W / 2, H/5);        
        gc.setLineWidth(5);
        gc.setLineDashes(null);
        gc.strokeLine(60, H/4, W - 60, H/4);
        for (int i = 0; i < item.length; i++) {
            if (i == index) {
                gc.setFont(new Font(W/40 + 20));
            } else {
                gc.setFont(new Font(W/40));
            }
            gc.setStroke(Color.WHITESMOKE);
            gc.fillText(item[i], W / 2, H/3 + i * H/13);            
        }
        return enter;
    }

    protected String getHeader() {
        return "Pong";
    }

    protected String[] initItems() {
        String[] a = new String[4];
        a[0] = "1 Player";
        a[1] = "2 Players";
        a[2] = "Options";
        a[3] = "Scoreboard";
        return a;
    }

    /**
     * @return the game
     */
    public int getResult() {
        return index;
    }
}
