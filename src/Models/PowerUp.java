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
@XmlRootElement(name = "PowerUp")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"posX", "posy", "width", "height", "imagePath"})
public class PowerUp extends Brick {

    public PowerUp() {

    }

    public PowerUp(double posX, double posY, double width, double height, String imagePath) {
        super(posX, posY, width, height, imagePath);
        hits = 1;
    }

    public PowerUp(Brick b) {
        this((double) b.getPosX(), (double) b.getPosY(), (double) b.getWidth(), (double) b.getHeight(), (String) b.getImagePath());
        hits = 1;
    }

    @Override
    public boolean collision(Ball b) {
        b.setSpeX(1 * b.getSpeX());
//        b.setSpeY(1 * b.getSpeY());
        Powers p = new Powers(b.getHit(), getPosX(), getPosY(), b.getPlayer());
        p.usePower(b);
        return true;
    }

}
