package test;

import music.notes.Note;
import music.notes.NoteName;
import music.staff.SineStaff;
import music.staff.SineWave;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import words.WordSetsManager;

public class StaffTest extends PApplet {
	
	SineWave sw;
	SineStaff ss;
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
		
		ss = new SineStaff(new PVector(0, height/2), width, 100, -PI/15, 10, this);
		ss.addWordNote(WordSetsManager.getRandomWord().getText(), 60, this);
	}
	
	private void initWords() {
		WordSetsManager.init(this);
		
		font = createFont("American Typewriter", 60);
		textFont(font);
	}
	
	public void draw() {
		background(0);
		//sw.update();
		//sw.draw(200, this);
		ss.update();
		ss.draw(this);
	}
	
	public void mousePressed() {
		println(mouseX + ", " + mouseY);
	}
	
	public void keyPressed() {
		ss.clearWordNotes();
		ss.addWordNote(WordSetsManager.getRandomWord().getText(), 60, this);
	}
	
	public static void main(String[] args) { 
		PApplet.main("test.StaffTest"); 
	}	
}
