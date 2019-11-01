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
@XmlRootElement(name = "Boulders")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"posX", "posY", "width", "height", "rand"})
public class Boulders extends Brick {

    public Boulders(double posX, double posY, double width, double height, double rand) {
        super(posX, posY, width, height);

        hits = (int) Math.floor((Math.random() * 2) + 1);

        switch (hits) {

            case 1:
                setImagePath("/Images/din6.png");
                break;
            case 2:
                setImagePath("/Images/din5.png");
                break;
            case 3:
                setImagePath("/Images/din1.png");
                break;
        }
    }

    public Boulders(Brick b, double rand) {
        this((double) b.getPosX(), (double) b.getPosY(), (double) b.getWidth(), (double) b.getHeight(), (double) rand);

        hits = (int) Math.floor((Math.random() * 2) + 1);

        switch (hits) {

            case 1:
                setImagePath("/Images/din6.png");
                break;
            case 2:
                setImagePath("/Images/din5.png");
                break;
            case 3:
                setImagePath("/Images/din1.png");
                break;
        }
    }

    public Boulders() {
    }

    @Override
    public boolean collision(Ball b) {
        b.setSpeX(-1 * b.getSpeX());
        b.setSpeY(1 * b.getSpeY());
        hits -= 1;
        setHitImage(hits);
        return (hits <= 0);
    }

    /*
	 * takes number of hits left as a parameter and adjusts the image of the brick accordingly.
     */
    public void setHitImage(int hits) {
        switch (hits) {
            case 1:
                setImagePath("/Images/din3.png");
                break;
            case 2:
                setImagePath("/Images/din1.png");
                break;
            case 3:
                setImagePath("/Images/din6.png");
                break;
        }
    }
}
