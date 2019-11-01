/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import Interface.iScreenControlable;
import Utilities.ScreensController;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import jurasicbreakout.JurasicBreakout;

/**
 * FXML Controller class
 *
 * @author Drazen Dragovic
 */
public class LoadScreenController implements Initializable, iScreenControlable {

    ScreensController controller;
    private String filename = "";
    
    private GameScreenController gsc;

    @FXML
    private ListView<String> lwLoadGame;

    @FXML
    void btnMainOnAction(ActionEvent event) {
        controller.unloadScreen(JurasicBreakout.loadScrID);
        controller.setScreen(JurasicBreakout.mainScrID);
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        controller = screenPage;
    }

    @FXML
    void btnLoadGameOnAction(ActionEvent event) throws IOException {
        if (filename.isEmpty()) {

        } else {
            controller.loadScreen(JurasicBreakout.gameScrID, JurasicBreakout.gameScrFile);
            gsc = GameScreenController.getGsc();
            gsc.loadGame(filename);
            controller.setScreen(JurasicBreakout.gameScrID);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadGameList();
    }

    private void loadGameList() {

        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.fscontext.RefFSContextFactory");

        env.put(Context.PROVIDER_URL, "file:src/Save_Load/");

        try {
            Context ctxt = new InitialContext(env);

            NamingEnumeration flist = ctxt.listBindings("");

            while (flist.hasMore()) {
                Binding p = (Binding) flist.next();
                lwLoadGame.getItems().add(p.getName());
            }

            lwLoadGame.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov,
                        final String oldvalue, final String newvalue) {
                    filename = newvalue;
                }
            });

        } catch (NamingException ne) {
            System.out.println(ne);
        }
    }

}
