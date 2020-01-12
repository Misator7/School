/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.options;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class Controller implements Initializable {
    
    @FXML
    private Button cancelBut;
    @FXML
    private Button saveBut;
    
    @FXML
    private ChoiceBox<String> gameBox;
    @FXML
    private ChoiceBox<String> speedBox;   
    @FXML
    private ChoiceBox<String> pointsBox;
    @FXML
    private ChoiceBox<String> resolutionBox;
    @FXML
    private CheckBox soundBox;
    @FXML
    private CheckBox musicBox;
    @FXML
    private Slider soundSlider;
    @FXML
    private Slider musicSlider;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {        
        Properties prop = new Document().loadValues();        
        gameBox.setItems(FXCollections.observableArrayList("A", "B", "C"));
        gameBox.getSelectionModel().select(prop.getProperty("gameMode"));
        speedBox.setItems(FXCollections.observableArrayList("very slow", "slow", "normal", "fast", "very fast"));
        speedBox.getSelectionModel().select(prop.getProperty("ballSpeed"));
        pointsBox.setItems(FXCollections.observableArrayList("1", "3", "5", "10"));
        pointsBox.getSelectionModel().select(prop.getProperty("winPoints"));
        resolutionBox.setItems(FXCollections.observableArrayList("1920 x 1080", "1680 x 1050", "1600 x 900", "1440 x 900"));
        resolutionBox.getSelectionModel().select(prop.getProperty("resolution"));
        soundBox.setSelected(Boolean.parseBoolean(prop.getProperty("sound")));
        soundSlider.setValue(Double.parseDouble(prop.getProperty("soundValue")));
        musicBox.setSelected(Boolean.parseBoolean(prop.getProperty("music")));
        musicSlider.setValue(Double.parseDouble(prop.getProperty("musicValue")));

    }
    
    public void save() {
        Properties prop = new Properties();
        prop.setProperty("gameMode", gameBox.getValue());
        prop.setProperty("ballSpeed", speedBox.getValue());
        prop.setProperty("winPoints", pointsBox.getValue());
        prop.setProperty("sound", Boolean.toString(soundBox.isSelected()));
        prop.setProperty("soundValue", Double.toString(soundSlider.getValue()));
        prop.setProperty("music", Boolean.toString(musicBox.isSelected()));
        prop.setProperty("musicValue", Double.toString(musicSlider.getValue()));
        prop.setProperty("resolution", resolutionBox.getValue());
        new Document().saveValues(prop);
        cancel();
    }
    
    public void cancel() {
        Stage window = (Stage) cancelBut.getScene().getWindow();
        window.close();
    }
    
}
