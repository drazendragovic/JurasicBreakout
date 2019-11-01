/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jurasicbreakout.JurasicBreakout;
import Utilities.ScreensController;
import Interface.iScreenControlable;

/**
 *
 * @author Drazen Dragovic
 */
public class Main implements Initializable, iScreenControlable {

    ScreensController controller;
    
    private GameScreenController gsc;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        controller = screenParent;
    }

    @FXML
    void btnChartOnAction(ActionEvent event) {
        controller.loadScreen(JurasicBreakout.chartScrID, JurasicBreakout.chartScrFile);
        controller.setScreen(JurasicBreakout.chartScrID);
    }

    @FXML
    void btnLoadOnAction(ActionEvent event) {
        controller.loadScreen(JurasicBreakout.loadScrID, JurasicBreakout.loadScrFile);
        controller.setScreen(JurasicBreakout.loadScrID);
    }

    @FXML
    void btnStartOnAction(ActionEvent event) {
        controller.loadScreen(JurasicBreakout.gameScrID, JurasicBreakout.gameScrFile);
        gsc = GameScreenController.getGsc();
        gsc.Init();
        controller.setScreen(JurasicBreakout.gameScrID);
    }

    @FXML
    void btnMultipOnAction(ActionEvent event) {
        controller.loadScreen(JurasicBreakout.multiPLayScrID, JurasicBreakout.multiPLayScrFile);
        controller.setScreen(JurasicBreakout.multiPLayScrID);
    }

    @FXML
    void btnQuitOnAction(ActionEvent event) {
        System.exit(1);
    }

}
