package test;

import houses.vehicles.RollingWord;
import houses.vehicles.Wheel;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class VehiclesTest extends PApplet{

	PFont font;	
	int font_size = 60;
	
	Wheel w;
	
	RollingWord rw;
	
	public void settings(){
		size(1200, 600);
		//fullScreen();
	}	
	
	public void setup() {
		textAlign(LEFT, TOP);
		initWords();
		background(0);
		
		rw = new RollingWord(WordSetsManager.getRandomWord(), 50, 200, 600, 6, this);
		rw.draw(this);
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
			rw.translateX(2);
		} else if (keyCode == 37) {
			rw.translateX(-2);
		}
		else if (key == 32) {
			
		}
		background(0);
		rw.draw(this);
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.VehiclesTest"); 
	}
}
