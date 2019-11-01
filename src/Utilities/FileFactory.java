
package Utilities;

import Models.Ball;
import Models.BasicBrick;
import Models.Boulders;
import Models.Brick;
import Models.Elements;
import Models.Paddle;
import Models.PowerUp;
import Models.Powers;
import Models.SoundClip;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;


public class FileFactory {
    
        public static void saveGame(String filename, List<Object> data) {
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            
            oos.writeObject(data);
            
            oos.close();
            
        } catch(IOException e) {
            new Alert(AlertType.ERROR, "Error saving to file: " + e.getMessage(), ButtonType.CLOSE).showAndWait();
        }
    }
        
    
    public static List<Object> loadGame(String loadFile) {
        
        List<Object> loadList = new ArrayList<>();
        
        if (new File(loadFile).exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(loadFile))) {

                loadList = (List<Object>)ois.readObject();
                
                for (Object ob : loadList) {
                    if (ob instanceof Elements) {
                        String s = ((Elements)ob).getImagePath();
                        ((Elements)ob).SetRectangleWithImage(new Image(s));
                    }
                }
                ois.close();
                
            } catch(Exception e) {
                new Alert(AlertType.ERROR, "Error loading from file: " + e.getMessage(), ButtonType.CLOSE).showAndWait();
            }
        }
        return loadList;
    }
    
    
    public static void printReflection() {
        
        StringBuffer bafer = new StringBuffer();
        
        bafer.append("<!DOCTYPE html>\n");
        bafer.append("<html>\n");
        bafer.append("<head>\n");
        bafer.append("<title>Jurasic Breakout</title>\n");
        bafer.append("<meta charset=\"utf-8\">\n");
        bafer.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        bafer.append("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n");
        bafer.append("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\">\n");
        bafer.append("</script>\n");
        bafer.append("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\">\n");
        bafer.append("</script>\n");
        bafer.append("</head>\n");
        
        bafer.append("<body>\n");
        bafer.append("<div class=\"container\">\n");

        bafer.append("<h1>Class list</h1>\n");
  
        try {
            
            FileWriter writer = new FileWriter(new File("JurasicBreakout.html"), false);
            
            bafer.append(getConstructors(Elements.class));
            bafer.append(getMethods(Elements.class));
            
            bafer.append(getConstructors(Ball.class));
            bafer.append(getMethods(Ball.class));
            
            bafer.append(getConstructors(Brick.class));
            bafer.append(getMethods(Brick.class));
            
            bafer.append(getConstructors(BasicBrick.class));
            bafer.append(getMethods(BasicBrick.class));
            
            bafer.append(getConstructors(Boulders.class));
            bafer.append(getMethods(Boulders.class));
            
            bafer.append(getConstructors(PowerUp.class));
            bafer.append(getMethods(PowerUp.class));
            
            bafer.append(getConstructors(Paddle.class));
            bafer.append(getMethods(Paddle.class));
            
            bafer.append(getConstructors(Powers.class));
            bafer.append(getMethods(Powers.class));
            
            bafer.append(getConstructors(SoundClip.class));
            bafer.append(getMethods(SoundClip.class));
            
            
            bafer.append("</div>\n");
            bafer.append("</body>\n");
            bafer.append("</html>\n");
            
            writer.append(bafer.toString());
            
            writer.flush();
            writer.close();
            
            
            Desktop.getDesktop().browse(new URI("JurasicBreakout.html"));
            
        } catch (URISyntaxException ex) {
            Logger.getLogger(FileFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String getMethods(Object arg0) throws SecurityException {
        
        StringBuffer bafer = new StringBuffer();
        bafer.append("<h3>Popis metoda</h3>\n");
        bafer.append("<table class=\"table table-striped\">\n");
        bafer.append("<th>Naziv metode</th>"
                + "<th>Ulazni parametri metode</th>"
                + "<th>Izlazni parametar metode</th>");
        
        Class className = arg0.getClass();
        for(Method metoda : className.getMethods()) {
            bafer.append("<tr><td>\n");
            bafer.append(metoda.getName());
            bafer.append("</td><td>\n");
            if(metoda.getParameterCount() > 0) {
                for(Parameter parameter : metoda.getParameters()) {
                    bafer.append(parameter.getType().getName() + " "
                            + parameter.getName() + "\n");
                }
            }
            bafer.append("</td><td>\n");
            bafer.append(metoda.getReturnType().getName()).append("</td>");
        }
        
        bafer.append("</table>\n");
        
        return bafer.toString();
    }

    private static String getConstructors(Class className) throws SecurityException {
        
        StringBuffer bafer = new StringBuffer();
//        Class className = arg0.getClass();
        bafer.append("<h2>" + className.getName() +  "</h2>\n");
        bafer.append("<h3>Constructor list</h3>\n");
        bafer.append("<table class=\"table table-striped\">\n");
        bafer.append("<th>Constructor name</th>"
                + "<th>Constructor params</th>");
        for(Constructor c : className.getConstructors()) {
            bafer.append("<tr><td>\n");
            bafer.append(c.getName());
            bafer.append("</td><td>\n");
            if(c.getParameterCount() > 0) {
                for(Parameter parameter : c.getParameters()) {
                    bafer.append(parameter.getType().getName() + " "
                            + parameter.getName() + "\n");
                    
                }
            }
            bafer.append("</td></tr>\n");
        }
        bafer.append("</table>\n");
        
        return bafer.toString();
    }

}
