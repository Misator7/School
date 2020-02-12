/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.scoreboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pong.options.Document;

/**
 *
 * @author User
 */
public class Controller implements Initializable {
    
    @FXML
    private Label firstL;
    @FXML
    private Label firstTimeL;
    @FXML
    private Label secondL;
    @FXML
    private Label secondTimeL;
    @FXML
    private Label thirdL;
    @FXML
    private Label thirdTimeL;
    @FXML
    private Label fourthL;
    @FXML
    private Label fourthTimeL;
    @FXML
    private Label fifthL;
    @FXML
    private Label fifthTimeL;
    @FXML
    private Label sixthL;
    @FXML
    private Label sixthTimeL;
    @FXML
    private Label seventhL;
    @FXML
    private Label seventhTimeL;
    @FXML
    private Label eighthL;
    @FXML
    private Label eighthTimeL;
    @FXML
    private Label ninthL;
    @FXML
    private Label ninthTimeL;
    @FXML
    private Label tenthL;
    @FXML
    private Label tenthTimeL;
    
    @FXML
    private Button cancelBut;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Document doc = new Document();
        String[][] scoreboard = doc.getScoreboard();
        firstL.setText(scoreboard[0][0]);
        firstTimeL.setText(timeParsing(scoreboard[0][1]));
        secondL.setText(scoreboard[1][0]);
        secondTimeL.setText(timeParsing(scoreboard[1][1]));
        thirdL.setText(scoreboard[2][0]);
        thirdTimeL.setText(timeParsing(scoreboard[2][1]));
        fourthL.setText(scoreboard[3][0]);
        fourthTimeL.setText(timeParsing(scoreboard[3][1]));
        fifthL.setText(scoreboard[4][0]);
        fifthTimeL.setText(timeParsing(scoreboard[4][1]));
        sixthL.setText(scoreboard[5][0]);
        sixthTimeL.setText(timeParsing(scoreboard[5][1]));
        seventhL.setText(scoreboard[6][0]);
        seventhTimeL.setText(timeParsing(scoreboard[6][1]));
        eighthL.setText(scoreboard[7][0]);
        eighthTimeL.setText(timeParsing(scoreboard[7][1]));
        ninthL.setText(scoreboard[8][0]);
        ninthTimeL.setText(timeParsing(scoreboard[8][1]));
        tenthL.setText(scoreboard[9][0]);
        tenthTimeL.setText(timeParsing(scoreboard[9][1]));
    }
    
    public void cancel() {
        Stage window = (Stage) cancelBut.getScene().getWindow();
        window.close();
    }
    
    private String timeParsing(String t) {
        Float time = Float.parseFloat(t);
        int m = (int) (time / 60000);
        int s = (int) ((time % 60000) / 1000);
        int ms =(int) (time % 1000);
        return m + ":" + s + "." + ms;
    }
}
