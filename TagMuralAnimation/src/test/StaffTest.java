package test;

import de.looksgood.ani.Ani;
import music.notes.Note;
import music.notes.NoteName;
import music.staff.MusicScene;
import music.staff.SineStaff;
import music.staff.SineWave;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import words.WordSetsManager;

public class StaffTest extends PApplet {
	
	MusicScene music;
	PFont font;
	
	Note note;
	
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
		music.addFeaturedWord (WordSetsManager.getRandomWord().getText());
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.StaffTest"); 
	}	
}
