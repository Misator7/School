/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import pong.menu.Menu;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pong.menu.DifMenu;
import pong.menu.WinMenu;

/**
 *
 * @author Misator
 */
public class Pong extends Application {

    private static final double W = 900, H = 600;
    boolean backspace, enter, upm, downm, up1, down1, up2, down2, upb, rightb;

    AnimationTimer animationTimer, menuTimer, at;
    GraphicsContext gc;

    Menu menu = new Menu(H, W);
    Game game;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Pong");
        Canvas canvas = new Canvas(W, H);
        Group plGround = new Group(canvas);
        gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(plGround, W, H);
        stage.setScene(scene);
        stage.show();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.animationHandle(gc,
                        up1, down1, up2, down2);
                switch (game.scoreChecking(W)) {
                    case 0:
                        animationTimer.stop();
                        menu = new WinMenu(H, W, gc, game, 250);
                        menu();
                        break;
                    case 1:
                        animationTimer.stop();
                        menu = new WinMenu(H, W, gc, game, 600);
                        menu();
                        break;
                    case 2:
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        gc.setFill(Color.BLACK);
                        gc.fillRect(0, 0, W, H);
                        game.positionReset(gc);
                        animationTimer.start();
                        break;
                    case 10:
                        break;
                }
            }
        };
        menu();
        scene.setOnKeyPressed((KeyEvent event) -> {
            keyPressed(event);
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            keyReleased(event);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void keyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            enter = true;
        }
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            backspace = true;
        }
        switch (event.getCode()) {
            case UP:
                up2 = true;
                break;
            case DOWN:
                down2 = true;
                break;
            case W:
                up1 = true;
                break;
            case S:
                down1 = true;
                break;
        }
    }

    public void keyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            enter = false;
        }
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
            backspace = false;
        }
        switch (event.getCode()) {
            case UP:
                up2 = false;
                break;
            case DOWN:
                down2 = false;
                break;
            case W:
                up1 = false;
                break;
            case S:
                down1 = false;
                break;
        }
    }

    public void menu() {
        menuTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (menu.menuTimerHandle(gc, up1, up2, down1, down2, enter, backspace)) {
                    menuTimer.stop();
                    game = menu.getResult();
                    if (game != null) {
                        if (game.getClass() == SinglePlayer.class && menu.getClass() != WinMenu.class) {
                            DifMenu difmenu = new DifMenu(H, W);
                            at = new AnimationTimer() {
                                @Override
                                public void handle(long now) {
                                    if (difmenu.menuTimerHandle(gc, up1, up2, down1, down2, enter, backspace)) {
                                        game.setDifficulty(difmenu.getDifficulty());
                                        at.stop();
                                        animationTimer.start();
                                    }
                                }
                            };
                            at.start();
                        } else {
                            animationTimer.start();
                        }
                    } else {
                        menu = new Menu(H, W);
                        menu();
                    }
                }
            }
        };
        menuTimer.start();
    }
}
