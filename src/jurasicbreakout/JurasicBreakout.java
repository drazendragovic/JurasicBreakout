/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jurasicbreakout;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utilities.ScreensController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author Drazen Dragovic
 */
public class JurasicBreakout extends Application {

    public static String mainScrID = "main";
    public static String mainScrFile = "/screens/Main.fxml";
    public static String gameScrID = "game";
    public static String gameScrFile = "/screens/gameScreen.fxml";
    public static String chartScrID = "chart";
    public static String chartScrFile = "/screens/chartScreen.fxml";
    public static String loadScrID = "load";
    public static String loadScrFile = "/screens/loadScreen.fxml";
    public static String infoScrID = "info";
    public static String infoScrFile = "/screens/infoScreen.fxml";
    public static String multiPLayScrID = "network";
    public static String multiPLayScrFile = "/screens/netScreen.fxml";

    private static MediaPlayer musicplayer;

    @Override
    public void start(Stage primaryStage) {

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(mainScrID, mainScrFile);
        mainContainer.loadScreen(infoScrID, infoScrFile);

        mainContainer.setScreen(mainScrID);

        musicplayer = new MediaPlayer(new Media(getClass().getResource("/Sounds/Intro.wav").toExternalForm()));
        musicplayer.setVolume(0.9);
        musicplayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicplayer.setRate(1.25);
        musicplayer.setMute(true);
        musicplayer.setOnEndOfMedia(() -> {
            musicplayer.play();
        });
        musicplayer.play();

        MediaView mediaView = new MediaView(musicplayer);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        ((Group) scene.getRoot()).getChildren().add(mediaView);
        scene.getStylesheets().add("Style.css");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1325);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static MediaPlayer getMediaPlayer() {
        return JurasicBreakout.musicplayer;
    }
}
