
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import pong.menu.Menu;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import pong.menu.DifMenu;
import pong.menu.WinMenu;
import pong.options.Document;
import pong.scoreboard.Nickname;

/**
 *
 * @author Misator
 */
public class Pong extends Application {

    private static double W = 900, H = 600;
    private boolean backspace, enter, upm, downm, up1, down1, up2, down2, upb, rightb;
    private boolean pause = false;

    private long startTime, gameTime, startPauseTime, pauseTime;
    private int position;

    private AnimationTimer animationTimer, menuTimer, at, setTimer;
    private GraphicsContext gc;
    private Group plGround;

    private Menu menu;
    private Game game;

    @Override
    public void start(Stage stage) throws Exception {
        Document doc = new Document();
        String path = "rsrc\\background.mp3";
        try {
            Media sound = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(doc.getMusicVolume() / 300);
            if (doc.getMusic()) {
                mediaPlayer.play();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        if (doc.isAutoResolution()) {
            Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
            W = primaryScreenBounds.getWidth();
            H = primaryScreenBounds.getHeight() - 22;
            stage.setMaximized(true);
        } else {
            W = doc.getWidth();
            H = doc.getHeight();
        }
        menu = new Menu(W, H);
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
                        game = null;
                        menu = new Menu(W, H);
                        menu();
                    }
                } else {
                    if (startTime == 0) {
                        startTime = System.currentTimeMillis();
                    }
                    game.animationHandle(gc,
                            up1, down1, up2, down2, pause);
                    switch (game.scoreChecking(W)) {
                        case 0:
                            gameTime = System.currentTimeMillis() - pauseTime - startTime;
                            startTime = 0;
                            pauseTime = 0;
                            animationTimer.stop();
                            menu = new WinMenu(W, H, gc, game, W / 5);
                            if (game.getClass() == SinglePlayer.class) {
                                scoreboard();
                            }                            
                            menu();
                            break;
                        case 1:
                            gameTime = System.currentTimeMillis() - pauseTime - startTime;
                            startTime = 0;
                            pauseTime = 0;
                            animationTimer.stop();
                            menu = new WinMenu(W, H, gc, game, 4 * (W / 5));
                            if (game.getClass() == SinglePlayer.class) {
                                scoreboard();
                            }
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
                if (startPauseTime == 0) {
                    startPauseTime = System.currentTimeMillis();
                } else {
                    pauseTime += System.currentTimeMillis() - startPauseTime;
                    startPauseTime = 0;
                }
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
                            break;
                        case 1:
                            game = new Game(W, H);
                            game.fillFrame(gc);
                            break;
                        case 2:
                            try {
                                window("rsrc\\layout.fxml", "Options");
                                game = null;
                                menu = new Menu(W, H);
                                menu();
                            } catch (IOException ex) {
                                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case 3:
                            try {
                                window("rsrc\\scoreboard.fxml", "Scoreboard");
                                game = null;
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
        menu = new DifMenu(W, H);
        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (menu.menuTimerHandle(gc, up1, up2, down1, down2, enter, backspace)) {
                    game.setDifficulty(menu.getResult());
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

    public void window(String path, String title) throws IOException {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new File(path).toURI().toURL());
        Parent root = loader.load();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        Scene dialogScene = new Scene(root);
        dialog.setTitle(title);
        dialog.setScene(dialogScene);
        dialog.setOnHiding(event -> {
            if (Nickname.nick != null) {
                saveScoreboard();
            }            
        });
        dialog.show();
    }

    public void scoreboard() {
        Document doc = new Document();
        String[][] scoreboard = doc.getScoreboard();
        int n;
        int k = (scoreboard.length - 1) / 2;
        int j = k;
        boolean bool = true;
        while (j != 0 && j != 9 && bool) {
            n = j;
            if (Float.parseFloat(scoreboard[j][1]) < gameTime) {
                if (Float.parseFloat(scoreboard[j - 1][1]) > gameTime) {
                    bool = false;
                } else if (k > 1) {
                    j -= k / 2;
                } else {
                    j -= k;
                }
            } else if (Float.parseFloat(scoreboard[j][1]) > gameTime) {
                if (Float.parseFloat(scoreboard[j + 1][1]) < gameTime) {
                    j++;
                    bool = false;
                }else if (Float.parseFloat(scoreboard[j + 1][1]) > gameTime && j == 8) {
                    j += 2;
                    bool = false;
                } else if (k > 1) {
                    j += k / 2;
                } else {
                    j += k;
                }
            }
            if (n == j) {
                k = 1;
            } else {
                k = Math.abs(j - n);
            }
        }
        if (j < 10) {
            position = j;
            try {
                window("rsrc\\name.fxml", "Nick");
            } catch (IOException ex) {
                Logger.getLogger(Pong.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveScoreboard() {
        System.out.println(position);
        Document doc = new Document();
        String[][] scoreboard = doc.getScoreboard();
        Properties prop = new Properties();
        for (int i = 0; i < position; i++) {
            prop.setProperty(i + "name", scoreboard[i][0]);
            prop.setProperty(i + "time", scoreboard[i][1]);
        }
        prop.setProperty(position + "name", Nickname.nick);
        prop.setProperty(position + "time", Float.toString(gameTime));
        for (int i = position; i < scoreboard.length - 1; i++) {
            prop.setProperty(i + 1 + "name", scoreboard[i][0]);
            prop.setProperty(i + 1 + "time", scoreboard[i][1]);
        }
        Nickname.nick = null;
        position = 10;
        doc.saveScoreboard(prop);
    }
}
