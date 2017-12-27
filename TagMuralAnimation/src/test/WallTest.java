package test;

import java.util.Iterator;

import de.looksgood.ani.Ani;
import global.ColorPalette;
import global.Settings;
import houses.block.Block;
import houses.block.House;
import houses.bricks.Brick;
import houses.elements.WindowFactory;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	House house;
	Block block;
	PFont font;
	int font_size = 100;
	String[] words = new String[20];
	Iterator<Brick> bricks;
	
	boolean fadeIn = false;
	
	public void settings(){
		//size(1200,800, P2D);
		fullScreen();
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		colorMode(HSB, 360, 100, 100);
		initWords();
		
		block = new Block(800, width-100, Settings.SIDEWALK_HEIGHT, this);
		block.populateBlock(this);
		block.fillAll(this);
		
		background(0);
		block.draw(false, true, true, this);
		//house.draw(false, true, true, this);
		
//		WaveScene.init(this, words);
	}
	
	public void draw() {
		if (bricks != null) {
			background(0);
			block.draw(false, false, true, this);
			for (int i = 0; i < 20; i++) {
				if(bricks.hasNext()) { 
					bricks.next().setVisibility(fadeIn);
				} else {
					fadeIn = !fadeIn;
					if (fadeIn == true) {
						block.clear();
						block.populateBlock(this);
						block.fillAll(this, false);						
					} 
					bricks = block.getAllBricks().iterator();
					delay(2000);
					break;
				}
			}
		}
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		words = WordSetsManager.getCurrentWordSet().getTexts();
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}
	
	public void keyPressed() {
		if (key == 10) {
			block.clear();
			background(0);
			block.populateBlock(this);
			block.fillAll(this);
			block.draw(false, false, true, this);
		}
		else if (key == 32) {
			bricks = block.getAllBricks().iterator();
			
		}
	}
	
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
