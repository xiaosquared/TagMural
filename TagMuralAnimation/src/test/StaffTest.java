package test;

import de.looksgood.ani.Ani;
import global.ColorPalette;
import music.musicians.WordImage;
import music.staff.MusicScene;
import processing.core.PApplet;
import processing.core.PFont;
import words.WordSetsManager;

public class StaffTest extends PApplet {
	
	MusicScene music;
	PFont font;
	
	WordImage trumpeter, trumpeter2;
	WordImage pianist;
	WordImage piano;
	WordImage bench;
	
	float next_frame_interval = 500;
	float last_change = 0;
	
	public void settings() {
		fullScreen(P2D);
		//size(1200, 600, P2D);
		smooth(4);
	}	

	public void setup() {
		textAlign(LEFT, TOP);
		background(0);
		initWords();
		Ani.init(this);
		
		music = new MusicScene(this, 6);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", 60);
		textFont(font);
	}
	
	public void draw() {
		background(0);
		music.run();
	}
	
	public void mousePressed() {
		println(mouseX + ", " + mouseY);
	}
	
	public void keyPressed() {
		pianist.nextFrame();
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.StaffTest"); 
	}	
}
