package test;

import houses.vehicles.Wheel;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class VehiclesTest extends PApplet{

	PFont font;	
	int font_size = 10;
	
	Wheel w;
	
	public void settings(){
		size(1200	, 600);
		//fullScreen();
	}	
	
	public void setup() {
		textAlign(LEFT, TOP);
		initWords();
		background(0);
		
		w = new Wheel(width/2, height/2, 45, 6, 6, this);
		w.setup(this);
		w.draw(this);
	}
	
	public void draw() {
		
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", font_size);
		textFont(font, font_size);  
	}
	
	public void keyPressed() {
		if (keyCode == 39) {
			w.translateX(2);
		} else if (keyCode == 37) {
			w.translateX(-2);
		}
		else if (key == 32) {
			w.reset(this);
		}
		background(0);
		w.draw(this);
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.VehiclesTest"); 
	}
}
