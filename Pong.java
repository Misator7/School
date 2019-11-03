/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Misator
 */
public class Pong extends Application {

    private static final double W = 900, H = 600;
    boolean backspace, enter, upm, downm, up1, down1, up2, down2, upb, rightb;
    static int index = 1;
    static int sizeSing = 45, sizeMulti = 30, sizeSet = 30;
    static double sizeRestart = 15.5, extend = 0;

    AnimationTimer animationTimer, menuTimer, winMenuTimer;
    GraphicsContext gc;
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
                        winMenu(250);
                        break;
                    case 1:
                        animationTimer.stop();
                        winMenu(600);
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
        //animationTimer.start();
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
        Color singleplayer = Color.WHITESMOKE;
        Color multiplayer = Color.WHITESMOKE;
        Color settings = Color.WHITESMOKE;
        menuTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (up1 || up2) {
                    switch (index) {
                        case 1:
                            index = 3;
                            sizeSet = 45;
                            sizeSing = 35;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 2:
                            index = 1;
                            sizeSing = 45;
                            sizeMulti = 35;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 3:
                            index = 2;
                            sizeMulti = 45;
                            sizeSet = 35;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                } else if (down1 || down2) {
                    switch (index) {
                        case 1:
                            index = 2;
                            sizeMulti = 45;
                            sizeSing = 35;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 2:
                            index = 3;
                            sizeSet = 45;
                            sizeMulti = 35;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 3:
                            index = 1;
                            sizeSing = 45;
                            sizeSet = 35;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                    }
                }
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, W, H);
                gc.setFill(Color.WHITE);
                gc.setTextAlign(TextAlignment.CENTER);
                gc.setFont(new Font(80));
                gc.fillText("Pong", W / 2, 150);
                gc.setLineWidth(5);
                gc.strokeLine(60, 185, W - 60, 185);
                gc.setFont(new Font(sizeSing));
                gc.setStroke(singleplayer);
                gc.fillText("1 Player", W / 2, 300);
                gc.setFont(new Font(sizeMulti));
                gc.setStroke(multiplayer);
                gc.fillText("2 Players", W / 2, 370);
                gc.setFont(new Font(sizeSet));
                gc.setStroke(settings);
                gc.fillText("Settings", W / 2, 440);
                if (enter) {
                    menuTimer.stop();
                    switch (index) {
                        case 1:
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0, 0, W, H);
                            gc.setFill(Color.WHITESMOKE);
                            gc.strokeLine(W / 2, 0, W / 2, H);
                            gc.setFont(new Font(30));
                            gc.setTextAlign(TextAlignment.CENTER);
                            gc.setTextBaseline(VPos.TOP);
                            gc.fillText(Integer.toString(0), W - 60, 30);
                            gc.fillText(Integer.toString(0), 60, 30);
                            game = new SinglePlayer(H, W);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            animationTimer.start();
                            break;
                        case 2:
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0, 0, W, H);
                            gc.setFill(Color.WHITESMOKE);
                            gc.strokeLine(W / 2, 0, W / 2, H);
                            gc.setFont(new Font(30));
                            gc.setTextAlign(TextAlignment.CENTER);
                            gc.setTextBaseline(VPos.TOP);
                            gc.fillText(Integer.toString(0), W - 60, 30);
                            gc.fillText(Integer.toString(0), 60, 30);
                            game = new Game(H, W);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            animationTimer.start();
                            break;
                        case 3:
                            menuTimer.start();
                            break;
                    }
                    index = 1;
                    sizeSing = 45;
                    sizeMulti = 35;
                    sizeSet = 35;
                }
            }
        };
        menuTimer.start();
    }

    public void winMenu(double xwinner) {
        gc.setFill(Color.BLACK);
        gc.fillRect(50, 37, 30, 30);
        gc.fillRect(830, 37, 30, 30);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(Integer.toString(game.scoreR), W - 60, 30);
        gc.fillText(Integer.toString(game.scoreL), 60, 30);
        gc.setFill(Color.GOLD);
        gc.setFont(new Font(60));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Winner", xwinner, H / 2);
        winMenuTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (extend == 5 || extend == 10 || extend == 15 || extend == 20 || extend == 25) {
                    sizeRestart += 0.1;
                } else if (extend == 30 || extend == 35 || extend == 40 || extend == 45 || extend == 50) {
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
                    winMenuTimer.stop();
                    if (game.getClass() == Game.class) {
                        game = new Game(H, W);
                    } else if (game.getClass() == SinglePlayer.class) {
                        game = new SinglePlayer(H, W);
                    }
                    animationTimer.start();
                }
                if (backspace) {
                    winMenuTimer.stop();
                    menuTimer.start();
                }
            }
        };
        winMenuTimer.start();
    }
}
