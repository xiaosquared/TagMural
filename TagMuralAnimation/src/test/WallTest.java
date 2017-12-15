package test;

import houses.elements.ArchWindow;
import houses.elements.ColorPalette;
import houses.elements.PointedWindow;
import houses.elements.RectWindow;
import houses.elements.Window;
import houses.elements.Wall;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class WallTest extends PApplet {

	Wall wall;
	Window win;
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
		
		wall = new Wall(100, 100, width-200, 400, 6, ColorPalette.RED);
		win = new PointedWindow(200, 200, 250, 300, 6, ColorPalette.YELLOW);
		win.makeHole(wall);
		
		wall.fillAll(this);
		win.fillAll(this);
		
		background(0);
		wall.draw(false, true, true, this);
		win.draw(false, true, true, this);
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
