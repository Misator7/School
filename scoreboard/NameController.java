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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NameController implements Initializable {

    @FXML
    private TextField nick;
    
    @FXML
    private Button savebtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void savebtn() {
        Nickname.nick = nick.getText();
        Stage window = (Stage) savebtn.getScene().getWindow();
        window.close();
    }
    
}
