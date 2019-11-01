/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Drazen Dragovic
 */

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"posx", "posy", "width", "height", "imagePath"})
public class Brick extends Elements {

    protected int hits;

    public Brick() {

    }

    public Brick(double posx, double posy, double width, double height, String imagePath) {
        super(posx, posy, width, height, imagePath);

    }

    public Brick(double posx, double posy, double width, double height) {
        super(posx, posy, width, height);

    }

    public boolean checkForCollision(ArrayList<Ball> balls) {

        double ballWidth = balls.get(0).getRectangle().getWidth();

        for (int i = 0; i < balls.size(); i++) {
            boolean x_overlap = balls.get(i).getPosX() + ballWidth >= getPosX() && balls.get(i).getPosX() <= getPosX() + getWidth();
            boolean y_overlap = balls.get(i).getPosY() >= getPosY() + getHeight() && balls.get(i).getPosY() <= getPosY() + getHeight();
            if (x_overlap && y_overlap) {
                return collision(balls.get(i));
            }
        }
        return false;
    }

    public boolean collision(Ball b) {
        return false;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

}
