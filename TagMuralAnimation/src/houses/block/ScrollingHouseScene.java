package houses.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import global.Scene;
import global.Settings;
import houses.block.BlockDissolver.DissolveState;
import houses.vehicles.RollingWord;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import words.WordSetsManager;

public class ScrollingHouseScene implements Scene {
	PApplet parent;
	private float y;
	private float width;
	
	private PGraphics b1_drawing;
	private PGraphics b2_drawing;

	private Block block1;
	private Block block2;
	BlockDissolver dissolver;
	
	static float trans_x = 0;
	static float trans_y = 0;
	float MOVE_AMOUNT = 1.5f;	
	boolean isScrolling = true;
	
	ArrayList<RollingWord> featured_words;
	
	float FEATURED_FONT_SIZE = 60;
	float FEATURED_MOVE_AMOUNT = -2;
	
	float ADD_WORD_INTERVAL = 2000;
	float last_add_time = 0;
	
	
	public ScrollingHouseScene(float y, float width, PFont font, boolean visibility, PApplet parent) {
		block1 = initBlock(y, width, visibility, parent);
		block2 = initBlock(y, width, visibility, parent);
		
		dissolver = new BlockDissolver(visibility);
		dissolver.addBlock(block1);
		dissolver.addBlock(block2);
		
		b1_drawing = setupPGraphics(b1_drawing, font, parent);
		b2_drawing = setupPGraphics(b2_drawing, font, parent);
		
		this.parent = parent;
		this.y = y;
		this.width = width;
		
		featured_words = new ArrayList<RollingWord>();
	}
	
	private PGraphics setupPGraphics(PGraphics pg, PFont font, PApplet parent) {
		pg = parent.createGraphics(parent.width, parent.height, PApplet.P2D);
		pg.smooth(4);
		pg.textAlign(PApplet.LEFT, PApplet.TOP);
		pg.beginDraw();
		pg.textFont(font);
		pg.endDraw();
		return pg;
	}
	
	private Block initBlock(float y, float width, boolean visibility, PApplet parent) {
		Block block = new Block(y, width, Settings.SIDEWALK_HEIGHT, parent);
		block.populateBlock(parent);
		block.fillAll(parent, visibility);
		return block;
	}
	
	public void clearFeaturedWords() { featured_words.clear(); }
	
	public float getPercentageVisible() { return dissolver.getPercentageVisible(); }
	
	public void startDissolve() { dissolver.startDissolve(); }
	public DissolveState runDissolve() { return dissolver.runDissolve(); }
	public boolean isDissolving() { return dissolver.isDissolving(); }
	
	public boolean doneFadingOut(DissolveState ds) {
			return ds == DissolveState.DONE_FADING_OUT;
	}
	
	public boolean isScrolling() { return isScrolling; } 
	public void setIsScrolling(boolean scrolling) { isScrolling = scrolling; }
	
	public void addFromQueue(LinkedList<String> featured_word_queue) {
		if (!featured_word_queue.isEmpty()) {
			float y = parent.random(block1.getBaseY() + FEATURED_FONT_SIZE + 100, parent.height - 10);
			if (!tooClose(y))
				addNextWord(featured_word_queue.pop(), y);
		}
	}
	
	private void addNextWord(String word, float y) {
		float font_size = parent.random(FEATURED_FONT_SIZE - 10, FEATURED_FONT_SIZE + 10);
		float dx = parent.random(-3, -2);
	
		featured_words.add(new RollingWord(word, font_size, parent.width, y, 6, dx, parent));
	}
	
	private boolean tooClose(float y) {
		for (RollingWord w : featured_words) {
			if (w.tooMuchOverlap(y))
				return true;
		}
		return false;
	}
	
	public void updateVehicle(PApplet parent) {
		for (RollingWord w : featured_words) {
			w.translateX();
		}
	}
	
	public void run() {
		if (isDissolving()) {
			BlockDissolver.DissolveState ds = runDissolve();
			if (ds == DissolveState.DONE_FADING_OUT) {
				WordSetsManager.switchWordSet();
				resetBlocks(false);
				resetDissolver();
				startDissolve();
			} 
			if (ds == DissolveState.DONE_FADING_IN) {
				setIsScrolling(true);
			}
			drawOffscreen();
		}
		
		
		else if (isScrolling()) {
			moveLeft();
		}
		
		updateVehicle(parent);
		draw(parent);
	}
	
	public void draw(PApplet parent) {
		parent.background(0);
		parent.image(b1_drawing, trans_x, trans_y);
		parent.image(b2_drawing, trans_x + b1_drawing.width, trans_y);
		Iterator<RollingWord> rit = new ArrayList<>(featured_words).iterator();
		while (rit.hasNext()) {
			try {
				RollingWord rw = rit.next();
				if (rw.offScreenLeft(parent))
					featured_words.remove(rw);
				rw.draw(1, parent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void moveLeft() {
		trans_x -= MOVE_AMOUNT;
		if (trans_x < -b1_drawing.width)
			resetForScroll();
	}
	
	public void changeWordSet() {
		//setIsScrolling(false);
		//resetDissolver();
		//startDissolve();
	}
	
	public void resetForScroll() {
		Block b = new Block(block1);
		block1 = new Block(block2);
		trans_x += b1_drawing.width;
		block2 = b;
		//block2 = initBlock(y, width, true, parent);
		drawOffscreen();
	}
	
	public void resetBlocks(boolean visibility) {
		block1 = initBlock(y, width, visibility, parent);
		block2 = initBlock(y, width, visibility, parent);
	}
	
	public void resetDissolver() {
		dissolver.clearBricks();
		dissolver.addBlock(block1);
		dissolver.addBlock(block2);
	}
	
	public void drawOffscreen() {
		b1_drawing.beginDraw();
		b1_drawing.background(0);
		block1.draw(false, false, true, b1_drawing);
		b1_drawing.endDraw();
		
		b2_drawing.beginDraw();
		b2_drawing.background(0);
		block2.draw(false, false, true, b2_drawing);
		b2_drawing.endDraw();
	}
	
}
