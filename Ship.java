//Needed for the mouse
import java.awt.event.*;
//Needed for the graphics
import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/*
 * The player class
 */
public class Ship implements KeyListener {

    public static int SHIP_HEIGHT = 25;
    public static int SHIP_WIDTH = 15;

    private int x = 0;
    private int heightPosition = 0;
    private boolean isDead = false;
    int moveDistance = 15;
    boolean isPaused = false;

    SpaceInvaders spaceInvaders = null;
    BarrierProtect protection = null;

    //We are only going to allow one shot at a time
    Shot shot = null;

    boolean hitState = false;

    /**
     *
     */
    public Ship(SpaceInvaders si, BarrierProtect p) {
        spaceInvaders = si;
        protection = p;
        //Dynamically work out the starting position of the ship
        x = (int)((SpaceInvaders.WIDTH/2)+(SHIP_WIDTH/2));
        heightPosition = SpaceInvaders.HEIGHT-SHIP_HEIGHT - 20;
    }
    
    public void keyPressed(KeyEvent e) {
    }
    
	public void keyReleased(KeyEvent e) {
		int newX;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key typed");
            newX = x + moveDistance;
            if (newX > (SpaceInvaders.WIDTH - SHIP_WIDTH - 10)) {
            	//Stop the ship moving off the screen
            	x = SpaceInvaders.WIDTH-SHIP_WIDTH-10;
            } else {
              //Set the new x position
              x = newX;
            }   
		}
		
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key typed");
            newX = x - moveDistance;
            if (newX < 0) {
            	//Stop the ship moving off the screen
            	x = 10;
            } else {
              //Set the new x position
              x = newX;
            }   
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE && !isDead) {
        	AlienArmy army = spaceInvaders.getAlienArmy();
        	shot = new Shot(x+(int)(SHIP_WIDTH/2), heightPosition, army, protection);
        	
        	AudioInputStream audioInputStream = null;
    		try {
    			audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("./shoot.wav"));
    		} catch (UnsupportedAudioFileException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            Clip clip = null;
    		try {
    			clip = AudioSystem.getClip();
    		} catch (LineUnavailableException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            try {
    			clip.open(audioInputStream);
    		} catch (LineUnavailableException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
            clip.start();
        }
        
        if(e.getKeyCode() == KeyEvent.VK_P) {
        	isPaused = !isPaused;
        	spaceInvaders.pauseGame(isPaused);
        }
	}

	public void keyTyped(KeyEvent e) {
	}

    /**
     * Draw the image of the ship
     */ 
    public void drawShip(Graphics g) {
    	//Only draw the ship if there are lives remaining and space invader is not hit yet
    	if(spaceInvaders.defenderLives != 0) {
    		g.setColor(Color.yellow);
            g.fillRect(x, heightPosition, SHIP_WIDTH, SHIP_HEIGHT);
    	} else {
    		isDead = true;
    	}
        
        //If the shot is still alive, i.e. still on the screen
        if ((shot != null) && (shot.getShotState())) {
            shot.drawShot(g);
        }
    }

    /**
     * Check if a shot fired by an alien hit the ship
     */
    public boolean checkShot(int xShot, int yShot) {

        //Is the ship currently alive?
        //if (hitState) {
        //If it's alreay been shot then return false;
        // return false;
        //}

        //First lets check the X range
        if ((xShot >= x) && (xShot <= (x+SHIP_WIDTH))) {
            //X is ok, now lets check the Y range
            if ((yShot >= heightPosition) && (yShot <= (heightPosition+SHIP_HEIGHT))) {
                //The ship was hit!
//                hitState = true;
                spaceInvaders.defenderLives -= 1;
                return true;
            }
        } 
        return false;
    }

    public void hitByAlien() {
//        spaceInvaders.shotShip();
    }
    
    public int getXPos() {
        return x;
    }
    
    public int getYPos() {
        return heightPosition;
    }

}