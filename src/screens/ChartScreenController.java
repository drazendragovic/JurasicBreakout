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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jurasicbreakout.JurasicBreakout;
import static jurasicbreakout.JurasicBreakout.mainScrID;

/**
 * FXML Controller class
 *
 * @author Drazen Dragovic
 */
public class ChartScreenController implements Initializable, iScreenControlable {

    ScreensController controller;
    
    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }
    
    @FXML
    void btnMainOnAction(ActionEvent event) {
        controller.unloadScreen(JurasicBreakout.chartScrID);
        controller.setScreen(JurasicBreakout.mainScrID);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
