package music.staff;

import java.util.LinkedList;

import processing.core.PApplet;
import processing.core.PVector;
import words.WordSetsManager;

public class MusicScene {
	PApplet parent;
	SineStaff staff;
	
	float staff_height = 100;
	
	LinkedList<String> featured_word_queue;
	float WORD_FONT_SIZE = 60;
	float WORD_FADE_TIME = 7000;
	float fade_start_time = 0;
	boolean fading = false;
	
	float ADD_WORD_INTERVAL = 2000;
	float last_add_time = 0;
	
	public MusicScene(PApplet parent, float staff_font_size) {
		this.parent = parent;
		staff = new SineStaff(new PVector(0, parent.height*0.35f), parent.width,
								staff_height, -PApplet.PI/20, staff_font_size, parent);
		
		featured_word_queue = new LinkedList<String>();
	}
	
	public void addFeaturedWord(String word) {
		if (!staff.isFull()) {
			boolean added = staff.addWordNote(word, WORD_FONT_SIZE, parent);
			if (added) {
				last_add_time = parent.millis();
				return;
			}
		}
		featured_word_queue.add(word);
	}
	
	private String popWordFromQueue() {
		return featured_word_queue.pop();
	}
	
	private void addFromQueue() {
		if (!featured_word_queue.isEmpty())
			addFeaturedWord(popWordFromQueue());
	}
	
	public void run() {
		float current_time = parent.millis();
		
		if (staff.isFull() && fading == false) {
			staff.fadeWordNotes(WORD_FADE_TIME/1000);
			fade_start_time = parent.millis();
			fading = true;
		}
		
		else if (fading && current_time - fade_start_time > WORD_FADE_TIME * 0.6) {
			staff.clearWordNotes();
			fading = false;
			addFromQueue();
		}
		
		else if (current_time - last_add_time > ADD_WORD_INTERVAL) {
			addFeaturedWord(WordSetsManager.getRandomWord().getText());
			last_add_time = current_time;
		}
		
		parent.background(0);
		staff.update();
		staff.draw(parent);
	}
	
}
