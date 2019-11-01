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
@XmlRootElement(name = "Powers")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"NuHit", "posX", "posy", "player"})
public class Powers extends Elements {

    @XmlElement(name = "power")
    private String power;
    private int hit;
    @XmlElement(name = "player")
    private int player;
    private Ball ball;
    private double speed = 200.0;

    public Powers() {
    }

    public Powers(int NuHit, double posx, double posy, int player) {
        super(posx, posy);

        int rand = (int) Math.floor(Math.random() * 4);

        switch (rand) {
            case 0:
                power = "bigPaddle";
                setImagePath("/Images/pow1.png");
                break;
            case 1:
                power = "smallPaddle";
                setImagePath("/Images/pow2.png");
                break;
            case 2:
                power = "fastBall";
                setImagePath("/Images/pow3.png");
                break;
            case 3:
                power = "slowBall";
                setImagePath("/Images/pow4.png");
                break;
            case 4:
                power = "addBall";
                break;
        }

        this.setPosX(posx);
        this.setPosY(posy);

        rect.setWidth(20);
        rect.setHeight(20);

        hit = NuHit;
        this.player = player;
        speed = speed;
    }

    public int getHit() {
        return hit;
    }

    @XmlElement(name = "hit")
    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getPlayer() {
        return player;
    }

    public Ball getBall() {
        return ball;
    }

    @XmlElement(name = "ball")
    public void setBall(Ball b) {
        this.ball = b;
    }

    public String getPower() {
        return power;
    }

    public double getSpeed() {
        return speed;
    }

    public void usePower(Ball b) {

    }

}
