package test;

import music.staff.SineStaff;
import music.staff.SineWave;
import processing.core.PApplet;
import processing.core.PVector;
import words.WordSetsManager;

public class StaffTest extends PApplet {
	
	SineWave sw;
	SineStaff ss;
	
	public void settings() {
		size(1200, 600, P2D);
		//fullScreen();
	}	

	public void setup() {
		textAlign(LEFT, TOP);
		background(0);
		WordSetsManager.init(this);
		
		//sw = new SineWave(new PVector(0, height/2), width, 6, this);
		ss = new SineStaff(new PVector(0, height/2), width, 100, -PI/15, 6, this);
	}
	
	public void draw() {
		background(0);
		//sw.update();
		//sw.draw(200, this);
		ss.update();
		ss.draw(this);
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.StaffTest"); 
	}	
}
