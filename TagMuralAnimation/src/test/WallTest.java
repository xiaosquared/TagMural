package test;

import java.util.Iterator;

import houses.block.HouseScene;
import houses.bricks.Brick;
import houses.vehicles.RollingWord;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	HouseScene hs;
	RollingWord rw;
	
	PFont font;
	int font_size = 100;
	Iterator<Brick> bricks;
	
	boolean fadeIn = false;
	boolean isPaused = false;
	int pause_time = 2000;
	int pause_start = 0;
	
	public void settings(){
		//size(1400,1000, P2D);
		fullScreen(P2D);
		smooth(4);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		colorMode(HSB, 360, 100, 100);
		initWords();

		hs = new HouseScene(650, width-100, this);
		hs.initBlock(true, this);
		hs.drawOffscreen();
		hs.drawFromOffscreen(this);
	}
	
	public void draw() {
//		boolean isFading = hs.fadeInAndOut(this);
//		hs.drawOffscreen();
//		hs.drawFromOffscreen(this);
		
//		if (isFading) {
//			//hs.draw(this);
//			hs.drawOffscreen();
//			hs.drawFromOffscreen(this);
//		} 
//		//else {
//			//hs.drawFromOffscreen(this);
//		//}
//		hs.updateVehicle(this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}
	
	public void keyPressed() {
//		if (keyCode == 39) {
//			rw.translateX(2);
//		} else if (keyCode == 37) {
//			rw.translateX(-2);
//		} 
		
		switch(key) {
		case ' ':
			rw = new RollingWord(WordSetsManager.getRandomWord(), 60, 200, height-80, 6, this);
			break;
		case 'f':
			WordSetsManager.switchWordSet();
			break;
		}
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
