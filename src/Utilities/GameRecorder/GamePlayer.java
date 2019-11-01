/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.GameRecorder;

import Models.Ball;
import Models.Brick;
import Models.Paddle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Drazen Dragovic
 */
public class GamePlayer {

    JAXBContext jaxbContext;
    Unmarshaller unmarshaller;
    InputStream is;
    private String file = "src/Recordings/" + "GameState" + ".xml";
    private List<Object> gameState;
    

    public GamePlayer() throws FileNotFoundException {
        this.is = new FileInputStream(file);
    }

    public List<Object> startReading(String gameFile) throws JAXBException {
        file = gameFile;
        jaxbContext = JAXBContext.newInstance(Wrapper.class, Brick.class, Paddle.class, Ball.class);
        unmarshaller = jaxbContext.createUnmarshaller();
        List<Brick> bricks = unmarshal(unmarshaller, Brick.class, is);
        List<Paddle> paddles = unmarshal(unmarshaller, Paddle.class, is);
        List<Ball> balls = unmarshal(unmarshaller, Ball.class, is);
        
        for (Brick brick : bricks) {
            gameState.add(brick);
        }
        
        return gameState;
    }

    private <T> List<T> unmarshal(Unmarshaller unmarshaller, Class<T> clazz, InputStream file) throws JAXBException {
        StreamSource xml = new StreamSource(file);
        Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(xml, Wrapper.class).getValue();
        return wrapper.getItems();
    }

}
