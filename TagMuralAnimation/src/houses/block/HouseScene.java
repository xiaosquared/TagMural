package houses.block;

import java.util.HashSet;
import java.util.Iterator;

import global.Settings;
import houses.bricks.Brick;
import houses.vehicles.RollingWord;
import processing.core.PApplet;
import processing.core.PGraphics;
import words.WordSetsManager;

public class HouseScene {
	
	private PGraphics pg;
	
	private Block block;
	private RollingWord featured;
	
	private Iterator<Brick> bricks;
	private float total_bricks;
	private float fade_count;
	
	private boolean fadeIn = false;
	private boolean isPaused = false;
	private int NUM_TO_FADE = 20; 
	private int PAUSE_TIME = 2000;
	private int pause_start = 0;
	
	private float AMOUNT = 2;
	
	public HouseScene(float y, float width, PApplet parent) {
		block = new Block(y, width, Settings.SIDEWALK_HEIGHT, parent);
		
		featured = new RollingWord(WordSetsManager.getRandomWord(), 60, 200, parent.height-50, 6, parent);
		
		pg = parent.createGraphics(parent.width, parent.height);
		pg.textAlign(PApplet.LEFT, PApplet.TOP);
	}
	
	public void initBlock(boolean visibility, PApplet parent) {
		block.populateBlock(parent);
		block.fillAll(parent, visibility);

		HashSet<Brick> bricks_hash = block.getAllBricks();
		total_bricks = bricks_hash.size();
		fade_count = 0;
		bricks = bricks_hash.iterator();
		
		fadeIn = !visibility;
	}
	
	public void updateVehicle(PApplet parent) {
		featured.translateX(AMOUNT);
		if (featured.offScreenLeft(parent))
			AMOUNT = 2;
		else if (featured.offScreenRight(parent))
			AMOUNT = -2;
	}
	
	public boolean isFading() {
		return !isPaused;
	}
	
	
	// return false if paused, true if fading
	public boolean fadeInAndOut(PApplet parent) {
		if (isPaused) {
			if (parent.millis() - pause_start < PAUSE_TIME)
				return false;
			else {
				isPaused = false;
			}
		}
		
		draw(parent);
		
		for (int i = 0; i < NUM_TO_FADE; i++) {
			if (bricks.hasNext()) {
				bricks.next().setVisibility(fadeIn);
				if (fadeIn)
					fade_count++;
				else
					fade_count--;
			}
			else {
				fadeIn = !fadeIn;
				
				// if we are fading from invisible to visible
				// fill the block but set visibility to false initially
				if (fadeIn) {
					block.clear();
					block.populateBlock(parent);
					block.fillAll(parent, false);
					//clearScreen(parent);
				}
				
				// reset the iterator
				HashSet<Brick> bricks_hash = block.getAllBricks();
				total_bricks = bricks_hash.size();
				
				if (fadeIn)
					fade_count = 0;
				else
					fade_count = total_bricks;
				
				bricks = bricks_hash.iterator();
				
				isPaused = true;
				pause_start = parent.millis();
				break;
			}
		}
		return true;
	}
	
	public void draw(PApplet parent) {
		parent.background(0);
		block.draw(false, false, true, parent);
		
		featured.draw(fade_count/(total_bricks + 1), parent);
	}
	
	public void drawOffscreen() {
		pg.beginDraw();
		pg.background(0);
		block.draw(false, false, true, pg);
		pg.endDraw();
		//featured.draw(fade_count/(total_bricks + 1), pg);
	}
	
	public void drawFromOffscreen(PApplet parent) {
		parent.image(pg, 0, 0);
	}
	
	private void clearScreen(PApplet parent) {
		parent.fill(0);
		parent.rect(0, 0, parent.width, parent.height);
	}
}
