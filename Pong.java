/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.io.File;
import java.io.IOException;
import pong.menu.Menu;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pong.menu.DifMenu;
import pong.menu.WinMenu;
import pong.options.Document;

/**
 *
 * @author Misator
 */
public class Pong extends Application {

    private static final double W = 900, H = 600;
    private boolean backspace, enter, upm, downm, up1, down1, up2, down2, upb, rightb;
    private boolean pause = false;

    private AnimationTimer animationTimer, menuTimer, at, setTimer;
    private GraphicsContext gc;
    private Group plGround;

    private Menu menu = new Menu(W, H);
    private Game game;

    @Override
    public void start(Stage stage) throws Exception {
        Document doc = new Document();
//        String a = prop.getProperty("resolution");
//        a = a.replace(" ", "");
//        String[] b = a.split("x");
//        W = Integer.parseInt(b[0]);
//        H = Integer.parseInt(b[1]);
        String path = "src\\pong\\background.mp3";
        try {
            Media sound = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(doc.getMusicVolume() / 300);
            if (doc.getMusic()) {
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.err.print(e);
        }
        stage.setTitle("Pong");
        Canvas canvas = new Canvas(W, H);
        plGround = new Group(canvas);
        gc = canvas.getGraphicsContext2D();
        Scene scene = new Scene(plGround, W, H);
        stage.setScene(scene);
        stage.show();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pause) {
                    if (backspace) {
                        backspace = false;
                        pause = false;
                        animationTimer.stop();
                        menu = new Menu(W, H);
                        menu();
                    }
                } else {
                    game.animationHandle(gc,
                            up1, down1, up2, down2, pause);
                    switch (game.scoreChecking(W)) {
                        case 0:
                            animationTimer.stop();
                            menu = new WinMenu(W, H, gc, game, 250);
                            menu();
                            break;
                        case 1:
                            animationTimer.stop();
                            menu = new WinMenu(W, H, gc, game, 600);
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
            case P:
                pause = !pause;
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
                    enter = false;
                    menuTimer.stop();
                    switch (menu.getResult()) {
                        case 0:
                            game = new SinglePlayer(W, H);
                            game.fillFrame(gc);
                            break;
                        case 1:
                            game = new Game(W, H);
                            game.fillFrame(gc);
                            break;
                        case 2:
                            try {
                                options();
                                menu = new Menu(W, H);
                                menu();
                            } catch (IOException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        default:
                            game = null;
                            menu = new Menu(W, H);
                            menu();
                            break;
                    }
                    if (game != null) {
                        if (game.getClass() == SinglePlayer.class) {
                            singlePlayer();
                        } else if (game.getClass() == Game.class) {
                            animationTimer.start();
                        }
                    }
                }
            }
        };
        menuTimer.start();
    }

    public void singlePlayer() {
        DifMenu difmenu = new DifMenu(W, H);
        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (difmenu.menuTimerHandle(gc, up1, up2, down1, down2, enter, backspace)) {
                    game.setDifficulty(difmenu.getResult());
                    at.stop();
                    animationTimer.start();
                } else if (backspace) {
                    at.stop();
                    menu = new Menu(W, H);
                    menu();
                }
            }
        };
        at.start();
    }

    public void options() throws IOException {
        Stage dialog = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("options//layout.fxml"));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        Scene dialogScene = new Scene(root);
        dialog.setTitle("Options");
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
