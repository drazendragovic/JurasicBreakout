/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/*
 * @author Drazen Dragovic
 */
//@XmlRootElement(name = "Elements")
@XmlAccessorType(XmlAccessType.FIELD)

public class Elements implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "speX")
    protected double speX;
    @XmlElement(name = "speY")
    protected double speY;
    @XmlElement(name = "posX")
    protected double posX;
    @XmlElement(name = "posY")
    protected double posY;
    @XmlElement(name = "width")
    protected double width;
    @XmlElement(name = "height")
    protected double height;
    protected transient Rectangle rect;
    protected transient Image image;
    @XmlElement(name = "imagePath")
    protected String imagePath;

    public Elements(double posX, double posY, double width, double height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        rect = new Rectangle(posX, posY, width, height);
    }

    public Elements() {

    }

    public Elements(double posX, double posY, double width, double height, String imagePath) {
        this.speX = 0;
        this.speY = 0;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.image = new Image(imagePath);
        rect = new Rectangle(posX, posY, width, height);
        rect.setFill(new ImagePattern(image));
    }

    public Elements(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
        rect = new Rectangle(posX, posY);
    }

    public Elements(double posX, double posY, String imagePath) {
        this.speX = 0;
        this.speY = 0;
        this.posX = posX;
        this.posY = posY;
        this.imagePath = imagePath;
        this.image = new Image(imagePath);
        rect = new Rectangle(posX, posY, getWidth(), getHeight());
        rect.setFill(new ImagePattern(image));
    }

    public Elements(double posX, double posY, double speX, String imagePath) {
        this.speX = 0;
        this.posX = posX;
        this.posY = posY;
        this.imagePath = imagePath;
        this.image = new Image(imagePath);
        rect = new Rectangle(posX, posY, getWidth(), getHeight());
        rect.setFill(new ImagePattern(image));
    }

    public Elements(double posX, double posY, double width, double height, double speX, String imagePath) {
        this.speX = speX;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.image = new Image(imagePath);
        rect = new Rectangle(posX, posY, width, height);
        rect.setFill(new ImagePattern(image));
    }

    public Elements(double posX, double posY, double speX, double speY, double width, double height, String imagePath) {
        this.speX = 0;
        this.speY = 0;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.image = new Image(imagePath);
        rect = new Rectangle(posX, posY, width, height);
        rect.setFill(new ImagePattern(image));
    }

    public double getSpeX() {
        return speX;
    }

    public void setSpeX(double speX) {
        this.speX = speX;
    }

    public double getSpeY() {
        return speY;
    }

    public void setSpeY(double speY) {
        this.speY = speY;
    }

    public double getPosX() {
        return rect.getX();
    }

    public void setPosX(double posX) {
        rect.setX(posX);
    }

    public double getPosY() {
        return rect.getY();
    }

    public void setPosY(double posY) {
        rect.setY(posY);
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public double getWidth() {
        return rect.getWidth();
    }

    public double getHeight() {
        return rect.getHeight();
    }

    public Rectangle getRectangle() {
        return rect;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;

        setImage(new Image(imagePath));
        if (rect != null) {
            rect.setFill(new ImagePattern(image));
        }
    }

    public void SetRectangleWithImage(Image image) {
        rect = new Rectangle(posX, posY, width, height);
        rect.setFill(new ImagePattern(image));
    }

}
