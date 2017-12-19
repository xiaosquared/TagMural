package test;

import houses.elements.ArchWindow;
import houses.elements.ColorPalette;
import houses.elements.PointedWindow;
import houses.elements.Railing;
import houses.elements.RectWindow;
import houses.elements.Window;
import houses.stories.PlainStory;
import houses.elements.Wall;
import houses.elements.WindowFactory;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	Wall wall;
	Window win;
	Railing rail;
	PlainStory story;
	
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
		
		PlainStory story = new PlainStory(100, 100, width-200, 400, 6, ColorPalette.GREEN);
		story.addWindow(WindowFactory.Type.POINTED, 200, 150, 150, 200, ColorPalette.CYAN);
		story.fillAll(this);
//		
//		wall.fillAll(this);
//		win.fillAll(this);
		
		background(0);
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
			wall.reset();
			wall.fillAll(this);
			background(0);
			wall.draw(false, true, true, this);
		}
	}
	
	
	public static void main(String[] args) { 
		PApplet.main("test.WallTest"); 
	}
}
