package test;

import houses.block.BlockDissolver;
import houses.block.BlockDissolver.DissolveState;
import houses.block.ScrollingHouseScene;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class ScrollingBlocksTest extends PApplet {
	
	ScrollingHouseScene hs;
	
	PFont font;
	int font_size = 100;
	
	public void settings() {
		fullScreen(P2D);
		smooth(4);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		initWords();
		
		hs = new ScrollingHouseScene(600, width-100, font, true, this);
		hs.drawOffscreen();
		hs.draw(this);
	}
	
	public void draw() {
		if (hs.isDissolving()) {
			BlockDissolver.DissolveState ds = hs.runDissolve();
			if (ds == DissolveState.DONE_FADING_OUT) {
				WordSetsManager.switchWordSet();
				hs.resetBlocks(false);
				hs.resetDissolver();
				hs.startDissolve();
			} 
			if (ds == DissolveState.DONE_FADING_IN) {
				hs.setIsScrolling(true);
			}
			hs.drawOffscreen();
		}
		
		if (hs.isScrolling()) {
			hs.moveLeft();
		}
		
		hs.updateVehicle(this);
		hs.draw(this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font);  
	}
	
	public void keyPressed() {
		if (key == 'f') {
			hs.setIsScrolling(false);
			hs.resetDissolver();
			hs.startDissolve();
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.ScrollingBlocksTest"); 
	}
}
