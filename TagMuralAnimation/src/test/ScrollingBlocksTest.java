package test;

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
	}
	
	public void draw() {
		hs.run();
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
