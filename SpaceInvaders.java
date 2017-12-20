import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 *
 */
public class SpaceInvaders extends JFrame implements Runnable {
    public static int WIDTH = 600;//The width of the frame
    public static int HEIGHT = 400;//The height of the frame
    public int defenderLives = 2;

    private int gameSpeed = 10;//Try 500 /*******************************************GAME SPEED*******************************************/

    AlienArmy army = null;
    
    BarrierProtect protection = null;

    Ship ship = null;

    private boolean paused = false;

    public int score = 0;
     
    Graphics offscreen_high;
    BufferedImage offscreen;

    Image backGroundImage = null;
    Image alienImage = null; 
    Image barrierImage = null;
    
    /**
     * This is called a constructor. 
     */
    public SpaceInvaders(String frameTitle) {
        super(frameTitle);
        JLabel scoreKeeper = new JLabel("CURRENT SCORE:" + String.valueOf(score), JLabel.CENTER);
//	   	 scoreKeeper.setForeground(Color.yellow);
//	   	 scoreKeeper.setLocation(150, 450);
//	   	 scoreKeeper.setVisible(true);
//	   	 scoreKeeper.setSize(200, 200);
//	   	 scoreKeeper.setForeground(Color.WHITE);
//	   	 super.add(scoreKeeper);


    /**
     * Exit the program if the window is closed. 
     */
    addWindowListener (new java.awt.event.WindowAdapter() {
         @Override public void windowClosing(java.awt.event.WindowEvent windowEvent) { System.exit(0);}});
        
        //Create the barriers
        protection = new BarrierProtect(ship, this, barrierImage);

        //Create the ship to fight off the invading army!
        ship = new Ship(this, protection);

        //Create the alien army
        army = new AlienArmy(protection, ship, this, alienImage);

        //The ship will be controlled by the keys
        addKeyListener(ship);
        
        AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource("./ShakeYourBootay.wav"));
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Clip clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			clip.open(audioInputStream);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        
        offscreen = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
        offscreen_high = offscreen.createGraphics();

        setBackground(Color.black);
        setSize(WIDTH, HEIGHT + 100);
        setVisible(true);
        startGame();
    }

    /**
     * As you move your mouse on and off the screen we want to pause
     * the game.
     */
    public void pauseGame(boolean state) {
        paused = state;
    }

    /**
     * Kill an alien and get 1 point!
     */
    public void hitAlienScore() {
        score += 1;
        System.out.println("Current Score = "+score);
    }

    /**
     *
     */
    public void startGame() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void paint(Graphics g) {
        offscreen_high.setColor(Color.black);
        offscreen_high.fillRect(0,0, WIDTH, HEIGHT);

        army.drawArmy(offscreen_high);

        ship.drawShip(offscreen_high);
        
        protection.drawProtection(offscreen_high);
        
        Font f = new Font("Dialog", Font.BOLD, 20);
        g.setFont(f);
        g.setColor(Color.GREEN);
        g.drawString("CURRENT SCORE: ", 150, 450);
        g.setColor(Color.BLACK);
        g.drawString("", 360, 450);
        g.setColor(Color.GREEN);
        g.drawString(String.valueOf(score), 360, 450);
        
//        scoreKeeper = new JLabel("CURRENT SCORE:" + String.valueOf(score), JLabel.CENTER);
//        scoreKeeper.setForeground(Color.red);
//        scoreKeeper.setLocation(150, 450);
//        scoreKeeper.setVisible(true);
//        scoreKeeper.setSize(200, 200);
//        scoreKeeper.setBounds(0, 0, 1280, 720);
//        this.add(scoreKeeper);
//        scoreKeeper.setVerticalTextPosition(150);
//        scoreKeeper.setHorizontalTextPosition(450);
        

        

        g.drawImage(offscreen,0,0,this);
    }

    public void update(Graphics g) {
        paint(g);
    }

    /**
     *
     */
    public void moveAliens() {
        army.moveArmy();
    }

    /**
     *
     */
    public void run() {
        int count = 0;
        while(true) {
            try {
                Thread.sleep(gameSpeed);
            } catch(InterruptedException ie) {
                //Ignore this exception
            }
            //If the game is currently running, move the aliens
            if (!paused) {
                if (count >= 100) { /****************************************RATE OF ALIENS SHOOTING / MOVING********************************************************/
                    moveAliens();
                    count = 0;
                }
            }
            repaint();//Update the screen
            count ++;

        }
    }

    /**
     * Get a reference to the alien army
     */
    public AlienArmy getAlienArmy() {
        return army;
    }

    /**
     * This is the program entry point
     */
    public static void main(String []args) {
        SpaceInvaders invaders = new SpaceInvaders("Space Invaders");
    }

}