/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Drazen Dragovic
 */
public class Timer extends Thread {

    private Label label;
    private volatile boolean running = true;

    private double tickTimeInSeconds = 1.0;
    private double secondsElapsed = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private String strSeconds = String.format("%02d", seconds);
    private String strMinutes = String.format("%02d", minutes);
    private String strHours = String.format("%02d", hours);

    public Timer(Label label) {
        this.label = label;
    }

    public void terminate() {
        running = false;
    }

    public void reset() {
        terminate();
        secondsElapsed = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        strSeconds = String.format("%02d", seconds);
        strMinutes = String.format("%02d", minutes);
        strHours = String.format("%02d", hours);
        label.setText(strHours + ":" + strMinutes + ":" + strSeconds);
    }

    private void update() {
        secondsElapsed += tickTimeInSeconds;
        seconds += tickTimeInSeconds;

        if (seconds == 60.0) {
            minutes += 1;
            seconds = 0;
        }

        if (minutes == 60.0) {
            hours += 1;
            minutes = 0;
        }

        strSeconds = String.format("%02d", seconds);
        strMinutes = String.format("%02d", minutes);
        strHours = String.format("%02d", hours);
//        label.setText(strHours + ":" + strMinutes + ":" + strSeconds);
    }

    @Override
    public void run() {
        while (running) {
            update();
            Platform.runLater(() -> label.setText(strHours + ":" + strMinutes + ":" + strSeconds));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(getName() + ": interrupted, will check if need to be terminated");
            }
        }
    }

}
