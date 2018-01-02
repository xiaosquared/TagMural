package houses.block;

import global.Settings;
import houses.block.BlockDissolver.DissolveState;
import houses.vehicles.RollingWord;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import words.WordSetsManager;

public class ScrollingHouseScene {
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
	
	RollingWord featured_word;
	float FEATURED_MOVE_AMOUNT = -2;
	
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
		
		featured_word = new RollingWord(WordSetsManager.getRandomWord(), 60, parent.width, parent.height-50, 6, parent);
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
	
	public float getPercentageVisible() { return dissolver.getPercentageVisible(); }
	
	public void startDissolve() { dissolver.startDissolve(); }
	public DissolveState runDissolve() { return dissolver.runDissolve(); }
	public boolean isDissolving() { return dissolver.isDissolving(); }
	
	public boolean doneFadingOut(DissolveState ds) {
			return ds == DissolveState.DONE_FADING_OUT;
	}
	
	public boolean isScrolling() { return isScrolling; } 
	public void setIsScrolling(boolean scrolling) { isScrolling = scrolling; }
	
	public void updateVehicle(PApplet parent) {
		featured_word.translateX(FEATURED_MOVE_AMOUNT);
		if (featured_word.offScreenLeft(parent)) {
			FEATURED_MOVE_AMOUNT = -2;
			
			featured_word = new RollingWord(WordSetsManager.getRandomWord(), 60, parent.width, parent.height-50, 6, parent);
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
		
		if (isScrolling()) {
			moveLeft();
		}
		
		updateVehicle(parent);
		draw(parent);
	}
	
	public void draw(PApplet parent) {
		parent.background(0);
		parent.image(b1_drawing, trans_x, trans_y);
		parent.image(b2_drawing, trans_x + b1_drawing.width, trans_y);
		featured_word.draw(1, parent);
	}
	
	public void moveLeft() {
		trans_x -= MOVE_AMOUNT;
		if (trans_x < -b1_drawing.width)
			resetForScroll();
	}
	
	public void resetForScroll() {
		b1_drawing.beginDraw();
		b1_drawing.image(b2_drawing, 0, 0);
		b1_drawing.endDraw();
		
		trans_x += b1_drawing.width;
		
		block2 = initBlock(y, width, true, parent);
		drawOffscreenB2();
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
	
	private void drawOffscreenB2() {
		b2_drawing.beginDraw();
		b2_drawing.background(0);
		block2.draw(false, false, true, b2_drawing);
		b2_drawing.endDraw();
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
