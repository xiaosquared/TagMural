package test;

import de.looksgood.ani.Ani;
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
		
		hs = new ScrollingHouseScene(650, width-100, font, true, this);
		hs.drawOffscreen();
		hs.draw(this);

		Ani.init(this);
	}
	
	public void draw() {
		hs.moveLeft();
		hs.draw(this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font);  
	}
	
	public void keyPressed() {
		if (keyCode == 37) {
			
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.ScrollingBlocksTest"); 
	}
}
