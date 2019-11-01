/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.GameRecorder;

import Models.Ball;
import Models.BasicBrick;
import Models.Boulders;
import Models.Paddle;
import Models.PowerUp;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.namespace.QName;

/**
 *
 * @author Drazen Dragovic
 */
public class GameRecorder {

    JAXBContext jaxbContext;
    Marshaller jaxbMarshaller;
    OutputStream os;
    private String file = "src/Recordings/" + "GameState" + ".xml";

    public GameRecorder() {
        try {
            this.os = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void startRecording(List<?> gameState) throws PropertyException, JAXBException, FileNotFoundException {
        jaxbContext = JAXBContext.newInstance(Wrapper.class, BasicBrick.class, Boulders.class, BasicBrick.class, PowerUp.class, Paddle.class, Ball.class);
        jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshal(jaxbMarshaller, gameState, "GameState");
    }

    private void marshal(Marshaller marshaller, List<?> list, String name)
            throws JAXBException, FileNotFoundException {
        QName qName = new QName(name);
        Wrapper wrapper = new Wrapper(list);
        JAXBElement<Wrapper> jaxbElement = new JAXBElement<Wrapper>(qName,
                Wrapper.class, wrapper);
        marshaller.marshal(jaxbElement, System.out);
        marshaller.marshal(jaxbElement, os);
    }

}
