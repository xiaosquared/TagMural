package test;

import java.util.Iterator;

import houses.block.HouseScene;
import houses.bricks.Brick;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	HouseScene hs;
	
	PFont font;
	int font_size = 100;
	String[] words = new String[20];
	Iterator<Brick> bricks;
	
	boolean fadeIn = false;
	boolean isPaused = false;
	int pause_time = 2000;
	int pause_start = 0;
	
	public void settings(){
		//size(1400,1000, P2D);
		fullScreen();
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		colorMode(HSB, 360, 100, 100);
		initWords();

		hs = new HouseScene(750, width-100, this);
		hs.initBlock(false, this);
		
//		WaveScene.init(this, words);
	}
	
	public void draw() {
		hs.fadeInAndOut(this);
	}
	
	
	
	private void initWords() {
		WordSetsManager.init(this);
		words = WordSetsManager.getCurrentWordSet().getTexts();
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}
	
	public void keyPressed() {
		switch(key) {
		case 'f':
			WordSetsManager.switchWordSet();
			break;
		}
	}
	
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
