/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Models.Brick;
import Models.Boulders;
import Models.PowerUp;
import java.util.ArrayList;
import javafx.scene.image.Image;
import static screens.GameScreenController.BRICK_HEIGHT;
import static screens.GameScreenController.BRICK_WIDTH;
import static screens.GameScreenController.BRICK_SEPARATION;
import static screens.GameScreenController.NUM_COLUMN;
import static screens.GameScreenController.NUM_ROW;
import static screens.GameScreenController.POWERS;

/**
 *
 * @author Drazen Dragovic
 */
public class GetBricks {
    
    public ArrayList<Brick> getBrick(ArrayList<Brick> bricks, int level) {
	for (int i = 0; i < NUM_ROW; i++) {
            for(int j = 0; j < NUM_COLUMN; j++) {
                double rand = Math.random();
                double xloc = (BRICK_WIDTH + BRICK_SEPARATION) * j;
		double yloc = (BRICK_HEIGHT + BRICK_SEPARATION) * i; 
                
                if (rand < POWERS * level) {
                    String imagePath = "/Images/din4.png";
                    bricks.add(new PowerUp(xloc, yloc, BRICK_WIDTH, BRICK_HEIGHT, imagePath));
                }
                else {
                    bricks.add(new Boulders(xloc, yloc, BRICK_WIDTH, BRICK_HEIGHT, rand));
                }
            }
        }
        return bricks;
    }   
}
