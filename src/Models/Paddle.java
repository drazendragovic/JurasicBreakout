/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Drazen Dragovic
 */
@XmlRootElement(name = "Paddle")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"player", "posX", "posy", "width", "height", "speed", "imagePath"})
public class Paddle extends Elements {

    private int player;
    public double speed;

    public Paddle() {

    }

    public Paddle(double posX, double posY, String imagePath) {
        super(posX, posY, imagePath);
    }

    public Paddle(int player, double posX, double posY, double width, double height, double speed, String imagePath) {
        super(posX, posY, width, height, imagePath);
        this.speed = speed;
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        width = speed;
    }

}
