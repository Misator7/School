/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.menu;

import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author User
 */
public class DifMenu extends Menu {

    private int difficulty;

    public DifMenu(double H, double W) {
        super(H, W);

    }

    @Override
    protected boolean setResult(GraphicsContext gc) {
        difficulty = index;
        return true;
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    protected String getHeader() {
        return "Difficulty";
    }

    @Override
    protected void initItems() {
        item[0] = "Easy";
        item[1] = "Medium";
        item[2] = "Hard";
    }
}
