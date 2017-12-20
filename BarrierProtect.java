import java.awt.*;
import java.util.*;

public class BarrierProtect {

	//The army has 4 rows of barriers
	Barrier rowOne[] = new Barrier[12];
	Barrier rowTwo[] = new Barrier[12];
	Barrier rowThree[] = new Barrier[12];
	Barrier rowFour[] = new Barrier[12];    

	private Ship ship;

	private SpaceInvaders spaceInvaders;

	Image barrierImage = null;

	public BarrierProtect(Ship s, SpaceInvaders si, Image ai) {
		ship = s;
		spaceInvaders = si;
		barrierImage = ai;
		createArmy();
		setStartingPositions();
	}

	/*
	 * Initialize the 4 rows of barriers
	 */
	private void createArmy() {
		for (int i = 0; i < 12; i++) {
			rowOne[i] = new Barrier(barrierImage, spaceInvaders);
			rowTwo[i] = new Barrier(barrierImage, spaceInvaders);
			rowThree[i] = new Barrier(barrierImage, spaceInvaders);
			rowFour[i] = new Barrier(barrierImage, spaceInvaders);
		}	
	}

	/**
	 * Set where the barriers will be located
	 */
	private void setStartingPositions() {
		
		int rowHeight = SpaceInvaders.HEIGHT-ship.SHIP_HEIGHT - 50;//Set the height of the top row
		int leftStart = 50; //Sets the furtherest position to the left
		
		for (int i = 0; i < 12; i++) {
			rowOne[i].setPosition(leftStart, rowHeight);
			leftStart += 25;
			if((i+1) % 4 == 0) {
				leftStart += 100;
			}
		}
		
		rowHeight -= 10;//Ready for the next row
		leftStart = 50;//Reset the left position
		for (int i = 0; i < 12; i++) {
			rowTwo[i].setPosition(leftStart, rowHeight);
			leftStart += 25;
			if((i+1) % 4 == 0) {
				leftStart += 100;
			}
		}
	
		rowHeight -= 10;//Ready for the third row
		leftStart = 50;//Reset the left position
		for (int i = 0; i < 12; i++) {
			rowThree[i].setPosition(leftStart, rowHeight);
			leftStart += 25;
			if((i+1) % 4 == 0) {
				leftStart += 100;
			}
		}
		
		rowHeight -= 10;//Ready for the fourth row
		leftStart = 50;//Reset the left position
		for (int i = 0; i < 12; i++) {
			rowFour[i].setPosition(leftStart, rowHeight);
			leftStart += 25;
			if((i+1) % 4 == 0) {
				leftStart += 100;
			}
		}
	}
	
	public void drawProtection(Graphics g) {
		//Draw the first row
		for (int i = 0; i < 12; i++) {
			rowOne[i].drawbarrier(g);//Draw the first row
			rowTwo[i].drawbarrier(g);//Draw the second row
			rowThree[i].drawbarrier(g);//Draw the third row
			rowFour[i].drawbarrier(g);//Draw the fourth row
		}
	}

	/**
	 * collision detection takes place
	 */
	public boolean checkShot(int x, int y) {
		for (int i = 0; i < 12; i++) {
			if (rowOne[i].hitBarrier(x, y)) {
				return true;
			}
			if (rowTwo[i].hitBarrier(x, y)) {
				return true;
			}
			if (rowThree[i].hitBarrier(x, y)) {
				return true;
			}	
			if (rowFour[i].hitBarrier(x, y)) {
				return true;
			}	
		}
		return false;
	}
	
	public boolean checkShipShot(int x, int y) {
		for (int i = 0; i < 12; i++) {
			if (rowOne[i].shipHitBarrier(x, y)) {
				return true;
			}
			if (rowTwo[i].shipHitBarrier(x, y)) {
				return true;
			}
			if (rowThree[i].shipHitBarrier(x, y)) {
				return true;
			}	
			if (rowFour[i].shipHitBarrier(x, y)) {
				return true;
			}	
		}
		return false;
	}
}
