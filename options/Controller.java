/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pong.options;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import pong.Pong;

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
        Properties prop = new Document().loadConfig();        
        gameBox.setItems(FXCollections.observableArrayList("A", "B", "C"));
        gameBox.getSelectionModel().select(prop.getProperty("gameMode"));
        speedBox.setItems(FXCollections.observableArrayList("very slow", "slow", "normal", "fast", "very fast"));
        speedBox.getSelectionModel().select(prop.getProperty("ballSpeed"));
        pointsBox.setItems(FXCollections.observableArrayList("1", "3", "5", "10"));
        pointsBox.getSelectionModel().select(prop.getProperty("winPoints"));
        resolutionBox.setItems(FXCollections.observableArrayList("auto", "1920 x 1080", "1680 x 1050", "1600 x 900", "1440 x 900", "900 x 600"));
        resolutionBox.getSelectionModel().select(prop.getProperty("resolution"));
        soundBox.setSelected(Boolean.parseBoolean(prop.getProperty("sound")));
        soundSlider.setValue(Double.parseDouble(prop.getProperty("soundValue")));
        musicBox.setSelected(Boolean.parseBoolean(prop.getProperty("music")));
        musicSlider.setValue(Double.parseDouble(prop.getProperty("musicValue")));

    }
    
    public void save() {
        Properties prop = new Document().getPROP();
        new Document().saveConfig(prop);
        prop.setProperty("gameMode", gameBox.getValue());
        prop.setProperty("ballSpeed", speedBox.getValue());
        prop.setProperty("winPoints", pointsBox.getValue());
        prop.setProperty("sound", Boolean.toString(soundBox.isSelected()));
        prop.setProperty("soundValue", Double.toString(soundSlider.getValue()));
        prop.setProperty("music", Boolean.toString(musicBox.isSelected()));
        prop.setProperty("musicValue", Double.toString(musicSlider.getValue()));
        if (prop.getProperty("resolution") != resolutionBox.getValue()) {
            prop.setProperty("resolution", resolutionBox.getValue());
            new Document().saveConfig(prop);            
            cancel();
            restartApplication();
        } else {            
            cancel();
        }     
    }

    public void cancel() {
        Stage window = (Stage) cancelBut.getScene().getWindow();
        window.close();
    }

    public void restartApplication() {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar;
        final ArrayList<String> command = new ArrayList();
        command.add(javaBin);
        command.add("-jar");
        try {
            currentJar = new File(Pong.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            command.add(currentJar.getPath());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        final ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
