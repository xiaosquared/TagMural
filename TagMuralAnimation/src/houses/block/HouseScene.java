package houses.block;

import java.util.Iterator;

import global.Settings;
import houses.bricks.Brick;
import processing.core.PApplet;

public class HouseScene {
	private Block block;
	
	private Iterator<Brick> bricks;
	private boolean fadeIn = false;
	private boolean isPaused = false;
	private int NUM_TO_FADE = 20; 
	private int PAUSE_TIME = 2000;
	private int pause_start = 0;
	
	public HouseScene(float y, float width, PApplet parent) {
		block = new Block(y, width, Settings.SIDEWALK_HEIGHT, parent);
	}
	
	public void initBlock(boolean visibility, PApplet parent) {
		block.populateBlock(parent);
		block.fillAll(parent, visibility);
		bricks = block.getAllBricks().iterator();
		fadeIn = !visibility;
	}
	
	public void fadeInAndOut(PApplet parent) {
		if (isPaused) {
			if (parent.millis() - pause_start < PAUSE_TIME)
				return;
			else
				isPaused = false;
		}
		
		parent.background(0);
		block.draw(false, false, true, parent);
		
		for (int i = 0; i < NUM_TO_FADE; i++) {
			if (bricks.hasNext())
				bricks.next().setVisibility(fadeIn);
			else {
				fadeIn = !fadeIn;
				
				// if we are fading from invisible to visible
				// fill the block but set visibility to false initially
				if (fadeIn) {
					block.clear();
					block.populateBlock(parent);
					block.fillAll(parent, false);
					clearScreen(parent);
				}
				// reset the iterator
				bricks = block.getAllBricks().iterator();
				isPaused = true;
				pause_start = parent.millis();
				break;
			}
		}
	}
	
	private void clearScreen(PApplet parent) {
		parent.fill(0);
		parent.rect(0, 0, parent.width, parent.height);
	}
}
