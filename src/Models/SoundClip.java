/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javafx.scene.media.AudioClip;
import java.net.URL;
import static javafx.scene.media.AudioClip.INDEFINITE;

/**
 *
 * @author Drazen Dragovic
 */
public class SoundClip {
    private AudioClip clip;
    private boolean looping = false;
    private int repeat = 0;
    private String filename = "";
    
    
    public SoundClip() {
        
    }
    
    public SoundClip(String filename) {
        this();
        load(filename);
    }
    
    public AudioClip getClip() {
        return clip;
    }
    
    public void setLooping(boolean looping) {
        this.looping = looping;
    }
    
    public boolean getLooping() {
        return looping;
    }
    
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
    
    public int getRepeat() {
        return repeat;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public boolean isLoaded() {
        return (boolean) (clip != null);
    }
    
    private URL getURL(String filename) {
        URL url = null;
        try {
            url = this.getClass().getResource(filename);
        } catch (Exception e) {

        }
        return url;
    }
    
    public boolean load(String audiofile) {
            
        try {
            setFilename(audiofile);
            this.clip = new AudioClip(getURL(filename).toString());
            clip.setCycleCount(1);
            return true;
        } catch (Exception e) {
        }
        return false;
        }
    
    public void play() {
        if (!isLoaded()) {
            return;
        }
        clip.setCycleCount(repeat);

        if (looping) {
            clip.setCycleCount(INDEFINITE);
        } else {
            clip.play();
        }
    }

    
    public void stop() {
        clip.stop();
    }
    

}
