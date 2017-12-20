package test;

import global.ColorPalette;
import global.Settings;
import houses.stories.PlainStory;
import houses.stories.PorticoStory;
import houses.stories.RoofStory;
import houses.elements.WindowFactory;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	PlainStory story;
	RoofStory roof;
	PorticoStory portico;
	
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
				
		story = new PlainStory(100, 100, Settings.getStoryWidth(4), Settings.getStoryHeight(), Settings.LAYER_THICKNESS, ColorPalette.GREEN);
		story.addRailing(Settings.getRailingHeight(), ColorPalette.YELLOW);
		story.addWindows(WindowFactory.Type.RECT, 4, 
						Settings.getTopMargin(), 
						Settings.getBottomMargin(), 
						Settings.getSideMargin(), 
						Settings.getInBetween(), ColorPalette.BLUE);
//		story.addRailing(120, 12, 12, 10, ColorPalette.YELLOW);
//		story.addWindow(WindowFactory.Type.POINTED, 200, 150, 150, 200, ColorPalette.CYAN);
		//story.addDoor(WindowFactory.Type.RECT, 500, 100, 150, ColorPalette.YELLOW);
		story.fillAll(this);
		
//		roof = new RoofStory(100, 100, Settings.getStoryWidth(3), Settings.DEFAULT_STORY_HEIGHT, 16, Settings.LAYER_THICKNESS, ColorPalette.MAGENTA);
//		roof.addWindow(WindowFactory.Type.POINTED, 100, 50, 50, 50, ColorPalette.YELLOW);
//		roof.addChimney(0.5f, 40, 50);
//		roof.fillAll(this);

//		portico = new PorticoStory(100, 100, 500, 200, 3, 12, 6, ColorPalette.BLUE, ColorPalette.GREEN);
//		portico.fillAll(this);
		
		background(0);
		
//		portico.draw(false, true, true, this);
		
//		roof.draw(false, true, true, this);
		story.draw(false, true, true, this);
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
