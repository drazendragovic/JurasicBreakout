/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Drazen Dragovic
 */
@XmlRootElement(name = "Ball")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"player", "posX", "posY", "bHeight", "bWidth", "imagePath", "NuHit", "initSpeed"})
public class Ball extends Elements {

    private int hit;
    private int player;

    public Ball(int player, double posX, double posY, double bHeight, double bWidth, String imagePath, int NuHit, double initSpeed) {
        super(posX, posY, bWidth, bHeight, initSpeed, imagePath);

        hit = NuHit;
        speY = -1 * initSpeed;
        speX = Math.rint((Math.random() + 1)) * 1 * initSpeed * Math.random();
        this.player = player;
    }

    public Ball() {
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getPlayer() {
        return player;
    }

}
