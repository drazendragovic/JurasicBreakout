/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import Interface.iScreenControlable;
import NetworkGame.Chat.Chat;
import NetworkGame.Client.Client;
import NetworkGame.Server.Server;
import Utilities.ScreensController;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import jurasicbreakout.JurasicBreakout;

/**
 * FXML Controller class
 *
 * @author Drazen Dragovic
 */
public class NetScreenController implements Initializable, iScreenControlable {

    ScreensController controller;

    private static NetScreenController nsc;
    private GameScreenController gsc;
    private boolean isServerOn;
    private List<Object> gameState;
    private Chat chatService;
    
    public static NetScreenController getNsc() {
        return nsc;
    }

    public static void setNsc(NetScreenController nsc) {
        NetScreenController.nsc = nsc;
    }
    
       public Chat getChatService() {
        return chatService;
    }

    public void setChatService(Chat chatService) {
        this.chatService = chatService;
    }
    

    @FXML
    private TextArea taConnInfo;

    @FXML
    private Button btnPlay;

    public Button getBtnPlay() {
        return btnPlay;
    }

    public void setBtnPlay(Button btnPlay) {
        this.btnPlay = btnPlay;
    }

    public TextArea getTaConnInfo() {
        return taConnInfo;
    }

    public void setTaConnInfo(TextArea taConnInfo) {
        this.taConnInfo = taConnInfo;
    }

    @FXML
    void btnConnectOnAction(ActionEvent event) {
        try {
            Client c = new Client("localhost", 1234);
            c.connectToServer();
            isServerOn = false;

        } catch (Exception e) {
            Server s = new Server(1234);
            s.start();
            isServerOn = true;
        }
    }

    @FXML
    void btnMainOnAction(ActionEvent event) {
        controller.setScreen(JurasicBreakout.mainScrID);
    }

    @FXML
    void btnPlayOnAction(ActionEvent event) throws IOException {
        startGame(isServerOn);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfoText();
        setNsc(this);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    private void setInfoText() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("-----------------------------------");
        sb.append(" CONNECTION ");
        sb.append("-----------------------------------\n");

        taConnInfo.setText(sb.toString());
    }

    public void addInfoText(String message) {
        taConnInfo.appendText(message);
    }
    
    public List<Object> getGameState() {
        return gameState;
    }

    public void startGame(boolean server) {
        controller.loadScreen(JurasicBreakout.gameScrID, JurasicBreakout.gameScrFile);
        gsc = GameScreenController.getGsc();
        gsc.startNetGame(isServerOn);
        gameState = gsc.getGameData();
        controller.setScreen(JurasicBreakout.gameScrID);
    }

}
