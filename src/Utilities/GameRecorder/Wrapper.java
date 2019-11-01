/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities.GameRecorder;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Drazen Dragovic
 */
@XmlRootElement(name = "Game")
public class Wrapper<T> {

    private List<T> items;

    public Wrapper() {
        items = new ArrayList<T>();
    }

    public Wrapper(List<T> items) {
        this.items = items;
    }

    @XmlAnyElement(lax = true)
    public List<T> getItems() {
        return items;
    }

}
