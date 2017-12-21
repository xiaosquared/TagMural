package test;

import de.looksgood.ani.Ani;
import global.Settings;
import houses.elements.WindowFactory;
import houses.stories.House;

import processing.core.PApplet;
import processing.core.PFont;
import water.WaveScene;
import words.WordSetsManager;

public class WallTest extends PApplet {

	House house;
	
	PFont font;
	int font_size = 100;
	String[] words = new String[20];
	
	public void settings(){
		//size(1200,800, P2D);
		fullScreen(P2D);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		colorMode(HSB, 360, 100, 100);
		initWords();
		Ani.init(this);
				
		house = new House(100, 600, Settings.getStoryWidth(3), 3, Settings.LAYER_THICKNESS);
		//house.addDoorwayStory();
		//house.addPlainStory();
		//house.addBalconyStory();
		//house.addBalconyStory();
		//house.addRoof(true);
		house.addRoof(true, true);
		//house.fillAll(this);
		
		background(0);
		house.draw(false, true, true, this);
		
//		WaveScene.init(this, words);
	}
	
	public void draw() {
//		background(0);
//		house.draw(false, true, true, this);
//		WaveScene.run(this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		words = WordSetsManager.getCurrentWordSet().getTexts();
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}
	
	public void keyPressed() {
		if (key == 32) {
			
		}
	}
	
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
