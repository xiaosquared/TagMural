package test;

import global.ColorPalette;

import houses.stories.PlainStory;
import houses.stories.RoofStory;
import houses.elements.WindowFactory;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	PlainStory story;
	RoofStory roof;
	
	PFont font;
	int font_size = 100;
	
	public void settings(){
		size(1200,800, P2D);
		//fullScreen(P2D);
	}
	
	public void setup() {
		textAlign(LEFT, TOP);
		colorMode(HSB, 360, 100, 100);
		initWords();
		
//		wall = new Wall(100, 100, width-200, 400, 6, ColorPalette.RED);
//		win = new PointedWindow(200, 200, 250, 300, 6, ColorPalette.YELLOW);
//		win.makeHole(wall);
		//rail = new Railing(100, 100, 200, 300, 12, 12, 10, 6, ColorPalette.CYAN);
		//rail.fillAll(this);
		
//		story = new PlainStory(100, 100, width-200, 400, 6, ColorPalette.GREEN);
//		story.addRailing(120, 12, 12, 10, ColorPalette.YELLOW);
//		story.addWindow(WindowFactory.Type.POINTED, 200, 150, 150, 200, ColorPalette.CYAN);
		//story.addDoor(WindowFactory.Type.RECT, 500, 100, 150, ColorPalette.YELLOW);
//		story.fillAll(this);
		
		
		roof = new RoofStory(100, 100, 500, 300, 16, 6, ColorPalette.MAGENTA);
		roof.addWindow(WindowFactory.Type.POINTED , 100, 50, 50, 50, ColorPalette.YELLOW);
		roof.fillAll(this);
		
//		wall.fillAll(this);
//		win.fillAll(this);
		
		background(0);
		roof.draw(false, true, true, this);
//		story.draw(false, true, true, this);
		//rail.draw(false, true, true, this);
//		wall.draw(false, true, true, this);
//		win.draw(false, true, true, this);
	}
	
	public void draw() {
		
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}
	
	public void keyPressed() {
		if (key == 32) {
			story.reset();
			background(0);
			story.draw(false, true, true, this);
		}
	}
	
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
