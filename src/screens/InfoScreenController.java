/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import Interface.iScreenControlable;
import Utilities.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Drazen Dragovic
 */
public class InfoScreenController implements Initializable, iScreenControlable {
    
    ScreensController controller;
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
    controller = screenPage;
    }

    @FXML
    private AnchorPane apInfo;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    


    
}
