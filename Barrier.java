import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;

/**
 * The barrier class.
 */
public class Barrier {

	public static int BARRIER_HEIGHT = 10;
	public static int BARRIER_WIDTH = 25;

	private int leftPosition = 0;
	private int heightPosition = 0;

	private boolean hitState = false; // Whether this barrier has already been
										// shot

	private Image barrierImage = null;

	SpaceInvaders spaceInvaders = null;

	public Barrier(Image ai, SpaceInvaders si) {
		barrierImage = ai;
		spaceInvaders = si;
	}

	/**
	 * Returns whether the barrier had been hit
	 */
	public boolean hasBeenHit() {
		return hitState;
	}

	/**
	 * Check if a shot fired hit a barrier
	 */
	public boolean hitBarrier(int x, int y) {

		// Is the barrier currently alive?
		if (hitState) {
			// If it's already been shot then return false;
			return false;
		}

		// First lets check the X range
		if ((x >= leftPosition) && (x <= (leftPosition + BARRIER_WIDTH))) {
			// X is ok, now lets check the Y range
			if ((y >= heightPosition) && (y <= (heightPosition + BARRIER_HEIGHT))) {
				// We shot an barrier!
				hitState = true;
				return true;
			}
		}
		return false;
	}
	
	public boolean shipHitBarrier(int x, int y) {

		// Is the barrier currently alive?
		if (hitState) {
			// If it's already been shot then return false;
			return false;
		}

		// First lets check the X range
		if ((x >= leftPosition) && (x <= (leftPosition + BARRIER_WIDTH))) {
			// X is ok, now lets check the Y range
			if ((y >= heightPosition) && (y <= (heightPosition + BARRIER_HEIGHT))) {
				//HIT STATE DOES NOT MATTER HERE!! - WE ONLY WANT TO GET RID OF THE SHOT
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the position of the barrier on the screen
	 */
	public void setPosition(int x, int y) {
		leftPosition = x;
		heightPosition = y;
	}

	/**
	 * Returns the current x position of the barrier
	 */
	public int getXPos() {
		return leftPosition;
	}

	/**
	 * Returns the current x position of the barrier
	 */
	public int getYPos() {
		return heightPosition;
	}

	/**
	 * Draw the image of the barrier
	 */
	public void drawbarrier(Graphics g) {
		if (!hitState) {
			g.setColor(Color.white);
			g.fillRect(leftPosition, heightPosition, BARRIER_WIDTH, BARRIER_HEIGHT);
		}
	}

}